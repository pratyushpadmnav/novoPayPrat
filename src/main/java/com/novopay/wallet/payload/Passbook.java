package com.novopay.wallet.payload;

import java.math.BigDecimal;
import java.util.List;

public class Passbook {
	
	private BigDecimal amount;
	
	private List<PassbookPayload> transactionList ;

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public List<PassbookPayload> getTransactionList() {
		return transactionList;
	}

	public void setTransactionList(List<PassbookPayload> transactionList) {
		this.transactionList = transactionList;
	}
	
	

}
