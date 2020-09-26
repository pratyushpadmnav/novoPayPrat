package com.novopay.wallet.Exceptions;

public class UserNotFoundException extends Exception {
	
	
	private static final long serialVersionUID = 1L;

	
	public UserNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}


	public UserNotFoundException(String message) {
		// TODO Auto-generated constructor stub
		super(message);
	}

	public UserNotFoundException() {
		super();
	}
}
