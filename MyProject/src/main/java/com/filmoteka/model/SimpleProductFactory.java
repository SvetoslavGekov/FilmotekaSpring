package com.filmoteka.model;

import java.time.LocalDate;
import java.util.Map;
import java.util.Set;

import com.filmoteka.exceptions.InvalidProductDataException;
import com.filmoteka.model.nomenclatures.Genre;
import com.filmoteka.model.nomenclatures.ProductCategory;

public class SimpleProductFactory {

	// Constructor
	private SimpleProductFactory() {

	}

	// Methods
	//Create new stub instances for Spring MVC forms
	public static final Product createProductStub(int category) throws InvalidProductDataException {
		switch (category) {
		case 1: return new Movie();
		case 2: return new TVSeries();
		default: throw new InvalidProductDataException("You've attempted to create a product from a category that does not exist."
						+ " Please try again");
		}
	}
	
	
	// Create new products for registering in the databases
	public static final Product createProduct(String name, ProductCategory productCategory, LocalDate releaseDate,
			String pgRating, int duration, double rentCost,	double buyCost, String description, String poster, String trailer,
			String writers, String actors,Set<Genre> genres, Map<Integer, Double> raters, double salePercent, LocalDate saleValidity,
			String director, int season, LocalDate finishedAiring) throws InvalidProductDataException {
		
		Product product = null;
		//Check which instance of the ProductCategory is given
		if(productCategory.getId() == 1) {
			//Return a movie
			product = new Movie(name, productCategory, releaseDate, pgRating, duration, rentCost,
					buyCost, description, poster, trailer, writers, actors, genres, salePercent, saleValidity, director);
		}
		else {
			//Return a tv series
			product = new TVSeries(name, productCategory, releaseDate, pgRating, duration, rentCost,
					buyCost, description, poster, trailer, writers, actors, genres, salePercent, saleValidity, season, finishedAiring);
		}
		return product;
	}
	
	public static final Product createProduct(int id, String name, ProductCategory productCategory, LocalDate releaseDate,
			String pgRating, int duration, double rentCost,	double buyCost, String description, String poster, String trailer,
			String writers, String actors,Set<Genre> genres, Map<Integer, Double> raters, double salePercent, LocalDate saleValidity,
			String director, int season, LocalDate finishedAiring) throws InvalidProductDataException {
		
		Product product = null;
		//Check which instance of the ProductCategory is given
		if(productCategory.getId() == 1) {
			//Return a movie
			product = new Movie(id, name, productCategory, releaseDate, pgRating, duration, rentCost,
					buyCost, description, poster, trailer, writers, actors, genres, raters, salePercent, saleValidity, director);
		}
		else {
			//Return a tv series
			product = new TVSeries(id, name, productCategory, releaseDate, pgRating, duration, rentCost,
					buyCost, description, poster, trailer, writers, actors, genres, raters, salePercent, saleValidity, season, finishedAiring);
		}
		return product;
	}
}
