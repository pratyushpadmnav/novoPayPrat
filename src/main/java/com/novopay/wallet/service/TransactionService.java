package com.novopay.wallet.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.novopay.wallet.Exceptions.InsuffecientFundsException;
import com.novopay.wallet.Exceptions.UserNotFoundException;
import com.novopay.wallet.Exceptions.WalletInvalid;
import com.novopay.wallet.model.Transaction;
import com.novopay.wallet.model.UserAccount;
import com.novopay.wallet.model.UserLoginCredential;
import com.novopay.wallet.model.Wallet;
import com.novopay.wallet.payload.TransactionPayLoad;
import com.novopay.wallet.payload.UserSignUpPayLoad;
import com.novopay.wallet.repository.LoginDetailRepository;
import com.novopay.wallet.repository.LoginRepository;
import com.novopay.wallet.repository.TransactionRepository;
import com.novopay.wallet.repository.WalletRepository;

@Service
public class TransactionService {
	
	
	public static final String CREDIT ="credit";
	public static final String DEBIT ="credit";
	public static final String SUCCESS ="credit";
	
	private static final BigDecimal charge = new BigDecimal(0.2);
	private static final BigDecimal commission = new BigDecimal(0.05);
	
	private LoginRepository loginRepository;
	private LoginDetailRepository loginDetailRepository;
	private TransactionRepository transactionRepository;
	private WalletRepository walletRepository;
	
	@Autowired
	TransactionService(LoginRepository loginRepository, LoginDetailRepository loginDetailRepository,TransactionRepository transactionRepository,WalletRepository walletRepository) {
		this.loginRepository = loginRepository;
		this.loginDetailRepository = loginDetailRepository;
		this.transactionRepository=transactionRepository;
		this.walletRepository = walletRepository;
	}
	
	
		
		public boolean addMoney(String email, BigDecimal amount) throws WalletInvalid {
			UserAccount userAccnt = loginRepository.findByEmail(email).orElse(null);
			
			
			
			return addToWallet(userAccnt.getWallet(),null,  amount);
		}
		
		
		public boolean transferMoneyWallet(String senderEmail, String recipientEmail, BigDecimal amount) throws UserNotFoundException, WalletInvalid, InsuffecientFundsException {
			UserAccount sender = loginRepository.findByEmail(senderEmail).orElse(null);
			UserAccount recipient = loginRepository.findByEmail(recipientEmail).orElse(null);
			
			if(sender == null || recipient == null) {
				throw new UserNotFoundException("User  not found.");
			}
			
			return transferToWallet(sender.getWallet(), recipient.getWallet(), amount);
		}
		
		private boolean transferToWallet(Wallet senderWallet, Wallet recipientWallet, BigDecimal amount) throws WalletInvalid ,InsuffecientFundsException {
			if(senderWallet == null || recipientWallet == null) {
				throw new WalletInvalid();
			}
			
			deductFromWallet(senderWallet, recipientWallet, amount);
			
			addToWallet(recipientWallet, senderWallet, amount);
			
			return true;
		}
		
		
		
		private boolean addToWallet(Wallet wallet,Wallet sendorWallet , BigDecimal amount) throws WalletInvalid {
			if(wallet == null) {
				throw new WalletInvalid();
			}
			
			BigDecimal finalBalance = wallet.getBalance().add(amount);
			
			Transaction transaction = new Transaction();
			transaction.setAmount(amount);
			transaction.setType(CREDIT);
			transaction.setStatus(SUCCESS);
			transaction.setTimeOfTransaction(LocalDateTime.now());
			transaction.setCharge(new BigDecimal(0.0));
			transaction.setCommision(new BigDecimal(0.0));
			transaction.setWallet(wallet);
			
			if(sendorWallet != null) {
				transaction.setSendorWalletID(sendorWallet.getWalletId());
			}
			transactionRepository.save(transaction);
			
			wallet.setBalance(finalBalance);
			
			walletRepository.save(wallet);
			
			return true;								
		}
		
		private boolean deductFromWallet(Wallet wallet, Wallet sendorWallet, BigDecimal amount) throws WalletInvalid, InsuffecientFundsException {
			if(wallet == null || sendorWallet == null) {
				throw new WalletInvalid();
			}
			
			BigDecimal charge = calculateCharge(amount);
			BigDecimal commission = calculateCommission(amount);
			
			BigDecimal finalAmountAfterCharges = amount.add(charge).add(commission);
			
			BigDecimal finalBalance = wallet.getBalance().subtract(finalAmountAfterCharges);
			
			if(finalBalance.compareTo(BigDecimal.ZERO) < 0) {
				throw new InsuffecientFundsException("Transaction declined due to insufficient funds");
			}
			
			Transaction transaction = new Transaction();
			
			transaction.setAmount(amount);
			transaction.setType(DEBIT);
			transaction.setStatus(SUCCESS);
			transaction.setTimeOfTransaction(LocalDateTime.now());
			transaction.setCharge(charge);
			transaction.setCommision(commission);
			
			transaction.setWallet(wallet);
			transaction.setSendorWalletID(sendorWallet.getWalletId());
			
			transactionRepository.save(transaction);
			
			wallet.setBalance(finalBalance);
			
			walletRepository.save(wallet);
			
			return true;								
		}
		
		private BigDecimal calculateCharge(BigDecimal amount) {
			return amount.multiply(charge).divide(new BigDecimal(100.00));
		}
		
		private BigDecimal calculateCommission(BigDecimal amount) {
			return amount.multiply(commission).divide(new BigDecimal(100.00));
		}

}
