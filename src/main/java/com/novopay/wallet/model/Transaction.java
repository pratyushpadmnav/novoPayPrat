package com.novopay.wallet.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;



@Entity
public class Transaction {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "TRANSACTIONID", nullable = false, unique = true, updatable = false)
	private UUID transactionid;
	
	@Column(name = "TRANSACTIONAMOUNT", precision = 15, scale = 4, nullable = false, updatable = false)
	private BigDecimal transactionAmount;
	
	@Column(name = "TRANSACTIONTYPE", nullable = false, updatable = false)
	private String transactionType;
	
	@Column(name = "TRANSACTIONSTATUS", nullable = false, updatable = false)
	private String transactionStatus;
	
	@Column(name = "TIMEOFTRANSACTION", columnDefinition =  "TIMESTAMP", nullable = false, updatable = false)
	private LocalDateTime timeOfTransaction;
	
	@Column(name = "TRANSACTIONCHARGE", precision = 15, scale = 4, nullable = false, updatable = false)
	private BigDecimal transactionCharge;
	
	@Column(name = "TRANSACTIONCOMMISION", precision = 15, scale = 4, nullable = false, updatable = false)
	private BigDecimal transactionCommision;
	
	
	
	@ManyToOne
	@JoinColumn(name = "WALLETID", nullable = false)
	private Wallet wallet;



	public UUID getTransactionid() {
		return transactionid;
	}



	public void setTransactionid(UUID transactionid) {
		this.transactionid = transactionid;
	}



	public BigDecimal getTransactionAmount() {
		return transactionAmount;
	}



	public void setTransactionAmount(BigDecimal transactionAmount) {
		this.transactionAmount = transactionAmount;
	}



	public String getTransactionType() {
		return transactionType;
	}



	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}



	public String getTransactionStatus() {
		return transactionStatus;
	}



	public void setTransactionStatus(String transactionStatus) {
		this.transactionStatus = transactionStatus;
	}



	public LocalDateTime getTimeOfTransaction() {
		return timeOfTransaction;
	}



	public void setTimeOfTransaction(LocalDateTime timeOfTransaction) {
		this.timeOfTransaction = timeOfTransaction;
	}



	public BigDecimal getTransactionCharge() {
		return transactionCharge;
	}



	public void setTransactionCharge(BigDecimal transactionCharge) {
		this.transactionCharge = transactionCharge;
	}



	public BigDecimal getTransactionCommision() {
		return transactionCommision;
	}



	public void setTransactionCommision(BigDecimal transactionCommision) {
		this.transactionCommision = transactionCommision;
	}



	public Wallet getWallet() {
		return wallet;
	}



	public void setWallet(Wallet wallet) {
		this.wallet = wallet;
	}
	
	

}
