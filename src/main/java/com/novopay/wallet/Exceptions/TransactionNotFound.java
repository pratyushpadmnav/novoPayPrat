package com.novopay.wallet.Exceptions;

public class TransactionNotFound extends Exception {
	
private static final long serialVersionUID = 1L;

	
	public TransactionNotFound(String message, Throwable cause) {
		super(message, cause);
	}


	public TransactionNotFound(String message) {
		// TODO Auto-generated constructor stub
		super(message);
	}

	public TransactionNotFound() {
		super();
	}

}
