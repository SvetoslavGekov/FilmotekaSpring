package com.filmoteka.exceptions;

public class InvalidMovieAPIDataException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3232144591295195140L;

	public InvalidMovieAPIDataException() {
		super();
	}

	public InvalidMovieAPIDataException(String message, Throwable cause) {
		super(message, cause);
	}

	public InvalidMovieAPIDataException(String message) {
		super(message);
	}

	public InvalidMovieAPIDataException(Throwable cause) {
		super(cause);
	}

}
