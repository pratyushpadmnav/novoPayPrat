package com.novopay.wallet.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
	public static final String SUCCES ="credit";
	
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
			
			
			
			return addToWallet(userAccnt.getWallet(),  amount);
		}
		
		
		
		private boolean addToWallet(Wallet wallet,  BigDecimal amount) throws WalletInvalid {
			if(wallet == null) {
				throw new WalletInvalid();
			}
			
			BigDecimal finalBalance = wallet.getBalance().add(amount);
			
			Transaction transaction = new Transaction();
			transaction.setAmount(amount);
			transaction.setType(CREDIT);
			transaction.setStatus(SUCCES);
			transaction.setTimeOfTransaction(LocalDateTime.now());
			transaction.setCharge(new BigDecimal(0.0));
			transaction.setCommision(new BigDecimal(0.0));
			transaction.setWallet(wallet);
			
			
			transactionRepository.save(transaction);
			
			wallet.setBalance(finalBalance);
			
			walletRepository.save(wallet);
			
			return true;								
		}

}
