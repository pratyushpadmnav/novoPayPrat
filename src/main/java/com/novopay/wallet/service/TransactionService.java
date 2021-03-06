package com.novopay.wallet.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.novopay.wallet.Exceptions.InsuffecientFundsException;
import com.novopay.wallet.Exceptions.TransactionNotFound;
import com.novopay.wallet.Exceptions.UserNotFoundException;
import com.novopay.wallet.Exceptions.WalletInvalid;
import com.novopay.wallet.model.Transaction;
import com.novopay.wallet.model.UserAccount;
import com.novopay.wallet.model.UserLoginCredential;
import com.novopay.wallet.model.Wallet;
import com.novopay.wallet.payload.Passbook;
import com.novopay.wallet.payload.PassbookPayload;
import com.novopay.wallet.payload.TransactionPayLoad;
import com.novopay.wallet.payload.UserSignUpPayLoad;
import com.novopay.wallet.repository.LoginDetailRepository;
import com.novopay.wallet.repository.LoginRepository;
import com.novopay.wallet.repository.TransactionRepository;
import com.novopay.wallet.repository.WalletRepository;

@Service
public class TransactionService {
	
	
	public static final String CREDIT ="credit";
	public static final String DEBIT ="debit";
	public static final String SUCCESS ="Sucess";
	
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
		
		public String checkTransactionStatus(UUID transactionId) throws TransactionNotFound
		{
			Transaction transaction = transactionRepository.findById(transactionId).orElse(null);
			if( null == transaction)
			{
				throw new TransactionNotFound("Transaction not found.");
			}
			
			return transaction.getStatus();
		}
		
		public void RevertTransaction(UUID transactionId) throws TransactionNotFound, WalletInvalid, InsuffecientFundsException
		{
			
			Transaction transaction = transactionRepository.findById(transactionId).orElse(null);
			if( null == transaction)
			{
				throw new TransactionNotFound("Transaction not found.");
			}
			Wallet senderWallet  = transaction.getWallet();
			Wallet recipientWallet = walletRepository.findById(transaction.getSendorWalletID()).orElse(null);
			if(recipientWallet == null) {
				throw new WalletInvalid("Wallet missing");
			}
			deductFromWallet(recipientWallet,senderWallet, transaction.getAmount(),true);
			
			addToWallet(senderWallet, recipientWallet, transaction.getAmount());
			
			
			
		}
		
		public Passbook viewPassBook(String email) throws UserNotFoundException, WalletInvalid
		{
			UserAccount userAccount = loginRepository.findByEmail(email).orElse(null);
			if(userAccount == null)
			{
				throw new UserNotFoundException("User Not Found");
			}
			//List<Transaction> passBook = transactionRepository.findAllByWallet(userAccount.getWallet(),Sort.by(Sort.Direction.DESC, "timeOfTransaction"));
			 Wallet wallet =userAccount.getWallet();
			 if(null == wallet)
				 throw new WalletInvalid("wallet not found");
			 List<Transaction> transactions = wallet.getTransactions();
			 List<PassbookPayload> passBookList = new ArrayList<PassbookPayload>();
			 for(Transaction itr:transactions)
			 {
				 PassbookPayload tmp = new PassbookPayload();
				 tmp.setDateOftransaction(itr.getTimeOfTransaction());
				 tmp.setTransactionAmount(itr.getAmount());
				 tmp.setTransactionId(itr.getId());
				 tmp.setTransactionStatus(itr.getStatus());
				 tmp.setTransactionType(itr.getType());
				 passBookList.add(tmp);
				 
				 
			 }
			 Collections.sort(passBookList);
			 Passbook passbook = new Passbook();
			 passbook.setAmount(wallet.getBalance());
			 passbook.setTransactionList(passBookList);
			 
			return passbook;
		}
		
		private boolean transferToWallet(Wallet senderWallet, Wallet recipientWallet, BigDecimal amount) throws WalletInvalid ,InsuffecientFundsException {
			if(senderWallet == null || recipientWallet == null) {
				throw new WalletInvalid();
			}
			
			deductFromWallet(senderWallet, recipientWallet, amount,false);
			
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
		
		private boolean deductFromWallet(Wallet wallet, Wallet sendorWallet, BigDecimal amount,boolean isReversal) throws WalletInvalid, InsuffecientFundsException {
			if(wallet == null || sendorWallet == null) {
				throw new WalletInvalid();
			}
			BigDecimal finalAmountAfterCharges = amount;
			if(!isReversal)
			{
			BigDecimal charge = calculateCharge(amount);
			BigDecimal commission = calculateCommission(amount);
			
			 finalAmountAfterCharges = finalAmountAfterCharges.add(charge).add(commission);
			}
			
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
		
		public BigDecimal calculateCharge(BigDecimal amount) {
			return amount.multiply(charge).divide(new BigDecimal(100.00));
		}
		
		public BigDecimal calculateCommission(BigDecimal amount) {
			return amount.multiply(commission).divide(new BigDecimal(100.00));
		}

}
