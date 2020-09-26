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
	private UUID id;
	
	@Column(name = "TRANSACTIONAMOUNT", precision = 15, scale = 4, nullable = false, updatable = false)
	private BigDecimal amount;
	
	@Column(name = "TRANSACTIONTYPE", nullable = false, updatable = false)
	private String type;
	
	@Column(name = "TRANSACTIONSTATUS", nullable = false, updatable = false)
	private String status;
	
	@Column(name = "TIMEOFTRANSACTION", columnDefinition =  "TIMESTAMP", nullable = false, updatable = false)
	private LocalDateTime timeOfTransaction;
	
	@Column(name = "SENDERWALLETID", nullable = true, updatable = false)
	private UUID sendorWalletID;
	
	@Column(name = "TRANSACTIONCHARGE", precision = 15, scale = 4, nullable = false, updatable = false)
	private BigDecimal charge;
	
	@Column(name = "TRANSACTIONCOMMISION", precision = 15, scale = 4, nullable = false, updatable = false)
	private BigDecimal commision;
	
	
	
	@ManyToOne
	@JoinColumn(name = "WALLETID", nullable = false)
	private Wallet wallet;




	public UUID getId() {
		return id;
	}



	public void setId(UUID id) {
		this.id = id;
	}



	public BigDecimal getAmount() {
		return amount;
	}



	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}



	public String getType() {
		return type;
	}



	public void setType(String type) {
		this.type = type;
	}



	public String getStatus() {
		return status;
	}



	public void setStatus(String status) {
		this.status = status;
	}



	public LocalDateTime getTimeOfTransaction() {
		return timeOfTransaction;
	}



	public void setTimeOfTransaction(LocalDateTime timeOfTransaction) {
		this.timeOfTransaction = timeOfTransaction;
	}



	public BigDecimal getCharge() {
		return charge;
	}



	public void setCharge(BigDecimal charge) {
		this.charge = charge;
	}



	public BigDecimal getCommision() {
		return commision;
	}



	public void setCommision(BigDecimal commision) {
		this.commision = commision;
	}
	
	


	public UUID getSendorWalletID() {
		return sendorWalletID;
	}



	public void setSendorWalletID(UUID sendorWalletID) {
		this.sendorWalletID = sendorWalletID;
	}



	public Wallet getWallet() {
		return wallet;
	}



	public void setWallet(Wallet wallet) {
		this.wallet = wallet;
	}
	
	

}
