package com.novopay.wallet.payload;

import com.sun.istack.NotNull;

import java.math.BigDecimal;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

public class TransactionPayLoad {
	
	

	@Digits(integer= 15,fraction=5)
	private BigDecimal amount;
	
	@NotNull
	@Length(min = 1, max = 254)
	private String recipientEmail;

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getRecipientEmail() {
		return recipientEmail;
	}

	public void setRecipientEmail(String recipientEmail) {
		this.recipientEmail = recipientEmail;
	}
	
	
	
	

}
