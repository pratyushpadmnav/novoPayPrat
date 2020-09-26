package com.novopay.wallet.Exceptions;

public class InsuffecientFundsException extends Exception{
	
	private static final long serialVersionUID = 1L;

	
	public InsuffecientFundsException(String message, Throwable cause) {
		super(message, cause);
	}


	public InsuffecientFundsException(String message) {
		// TODO Auto-generated constructor stub
		super(message);
	}

	public InsuffecientFundsException() {
		super();
	}

}
