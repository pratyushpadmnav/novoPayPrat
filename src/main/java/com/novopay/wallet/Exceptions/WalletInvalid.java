package com.novopay.wallet.Exceptions;

public class WalletInvalid  extends Exception{
	
	
	private static final long serialVersionUID = 1L;

	
	public WalletInvalid(String message, Throwable cause) {
		super(message, cause);
	}
	

	public WalletInvalid(String message) {
		// TODO Auto-generated constructor stub
		super(message);
	}
	
	public WalletInvalid() {
		super();
	}

}
