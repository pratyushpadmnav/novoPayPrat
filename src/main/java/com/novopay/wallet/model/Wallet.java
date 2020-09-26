package com.novopay.wallet.model;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class Wallet {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="WALLETID", nullable = false, unique = true, updatable = false)
	private UUID walletId;
	
	@Column(name="BALANCE", nullable=false,unique=false, updatable=true)
	private BigDecimal balance; 
	
	@OneToOne(mappedBy="wallet")
	private UserAccount userAccount;
	
	@OneToMany(mappedBy = "wallet", fetch = FetchType.LAZY)
	private List<Transaction> transactions;
	
	public Wallet() {
		super();
	}

	public Wallet(BigDecimal balance) {
		this.balance = balance;
	}

	public UUID getWalletId() {
		return walletId;
	}

	public void setWalletId(UUID walletId) {
		this.walletId = walletId;
	}



	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public UserAccount getUserAccount() {
		return userAccount;
	}

	public void setUserAccount(UserAccount userAccount) {
		this.userAccount = userAccount;
	}

	public List<Transaction> getTransactions() {
		return transactions;
	}

	public void setTransactions(List<Transaction> transactions) {
		this.transactions = transactions;
	}
	
	
	

}
