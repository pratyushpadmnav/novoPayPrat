package com.novopay.wallet.payload;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class PassbookPayload  implements Comparable<PassbookPayload> {
	
	private BigDecimal transactionAmount;
	
	private UUID transactionId;
	
	private LocalDateTime dateOftransaction;
	
	private String transactionType;
	
	private String transactionStatus;
	
	

	

	public BigDecimal getTransactionAmount() {
		return transactionAmount;
	}

	public void setTransactionAmount(BigDecimal transactionAmount) {
		this.transactionAmount = transactionAmount;
	}

	public UUID getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(UUID transactionId) {
		this.transactionId = transactionId;
	}

	public LocalDateTime getDateOftransaction() {
		return dateOftransaction;
	}

	public void setDateOftransaction(LocalDateTime dateOftransaction) {
		this.dateOftransaction = dateOftransaction;
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
	
	@Override
	public int compareTo(PassbookPayload obj1) {
		// TODO Auto-generated method stub
		return (obj1.dateOftransaction.compareTo(this.dateOftransaction));
	}
	

}
