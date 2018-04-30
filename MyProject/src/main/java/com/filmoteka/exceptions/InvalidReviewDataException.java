package com.filmoteka.exceptions;

public class InvalidReviewDataException extends Exception {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InvalidReviewDataException() {
		super();
	}

	public InvalidReviewDataException(String message, Throwable cause) {
		super(message, cause);
	}

	public InvalidReviewDataException(String message) {
		super(message);
	}

	public InvalidReviewDataException(Throwable cause) {
		super(cause);
	}
}
