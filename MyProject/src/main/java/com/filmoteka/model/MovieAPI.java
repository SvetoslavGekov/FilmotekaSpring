package com.filmoteka.model;

import com.filmoteka.exceptions.InvalidMovieAPIDataException;
import com.filmoteka.util.Supp;

public class MovieAPI {
	//Fields
	private String title;
	private String year;
	private String imdbID;
	private String poster;
	private String type;
	
	//Constructors
	public MovieAPI(String title, String year, String imdbID, String poster, String type) throws InvalidMovieAPIDataException {
		setTitle(title);
		setYear(year);
		setImdbID(imdbID);
		setPoster(poster);
		setType(type);
	}

	//Setters
	private void setTitle(String title) throws InvalidMovieAPIDataException {
		if(Supp.isNotNullOrEmpty(title)) {
			this.title = title;
		}
		else {
			throw new InvalidMovieAPIDataException("Invalid MovieAPI title");
		}
	}

	private void setYear(String year) throws InvalidMovieAPIDataException {
		if(Supp.isNotNullOrEmpty(year)) {
			this.year = year;
		}
		else {
			throw new InvalidMovieAPIDataException("Invalid MovieAPI date");
		}
	}

	private void setImdbID(String imdbID) throws InvalidMovieAPIDataException {
		if(Supp.isNotNullOrEmpty(imdbID)) {
			this.imdbID = imdbID;
		}
		else {
			throw new InvalidMovieAPIDataException("Invalid MovieAPI imdbID");
		}
	}

	private void setPoster(String poster) throws InvalidMovieAPIDataException {
		if(Supp.isNotNullOrEmpty(poster)) {
			this.poster = poster;
		}
		else {
			throw new InvalidMovieAPIDataException("Invalid MovieAPI poster");
		}
	}

	private void setType(String type) throws InvalidMovieAPIDataException {
		if(Supp.isNotNullOrEmpty(type)) {
			this.type = type;
		}		
		else {
			throw new InvalidMovieAPIDataException("Invalid MovieAPI type");
		}
	}

	//Getters
	public String getTitle() {
		return this.title;
	}

	public String getYear() {
		return this.year;
	}

	public String getImdbID() {
		return this.imdbID;
	}

	public String getPoster() {
		return this.poster;
	}

	public String getType() {
		return this.type;
	}
	
	
}
