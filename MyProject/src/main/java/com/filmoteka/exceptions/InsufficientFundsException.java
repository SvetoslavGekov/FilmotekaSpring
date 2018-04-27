package com.filmoteka.exceptions;

public class InsufficientFundsException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1302494252182163624L;

	public InsufficientFundsException() {
		super("Sorry, but you have insufficient funds to process this order.");
	}

	public InsufficientFundsException(Throwable cause) {
		super("Sorry, but you have insufficient funds to process this order.", cause);
	}


}
