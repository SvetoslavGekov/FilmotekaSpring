package com.filmoteka.model;

import java.time.LocalDate;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Component;

import com.filmoteka.exceptions.InvalidProductDataException;
import com.filmoteka.model.nomenclatures.Genre;
import com.filmoteka.model.nomenclatures.ProductCategory;
import com.filmoteka.validation.Supp;

@Component
public class Movie extends Product {
	//Optional fields
	private String director;
	
	//Constructors
	public Movie() {
		
	}
	
	//Constructor for creating a new movie
	public Movie(String name, ProductCategory productCategory, LocalDate releaseDate, String pgRating, int duration, double rentCost, double buyCost)
			throws InvalidProductDataException {
		super(name, productCategory, releaseDate, pgRating, duration, rentCost, buyCost);
	}
	
	//Constructor for creating a new move with all available information
	public Movie(String name, ProductCategory productCategory, LocalDate releaseDate, String pgRating, int duration, double rentCost,
			double buyCost, String description,String poster, String trailer, String writers, String actors,
			Set<Genre> genres, double salePercent, LocalDate saleValidity, String director) 
					throws InvalidProductDataException {
		super(name, productCategory, releaseDate, pgRating, duration, rentCost, buyCost, description,poster, trailer, writers, actors,
				genres, salePercent, saleValidity);
		setDirector(director);
	}
	
	//Constructor for loading a movie from the DB
	public Movie(int id, String name, ProductCategory productCategory, LocalDate releaseDate, String pgRating, int duration, double rentCost,
			double buyCost, String description,String poster, String trailer, String writers, String actors,
			Set<Genre> genres, Map<Integer,Double> raters, double salePercent, LocalDate saleValidity, String director) 
					throws InvalidProductDataException {
		super(id, name, productCategory, releaseDate, pgRating, duration, rentCost, buyCost, description,poster, trailer, writers, actors,
				genres, raters, salePercent, saleValidity);
		setDirector(director);
	}

	//Methods
	@Override
	public String toString() {
		return String.format("Type:%s	Id:%d	Name:%s	Year:%s	PG:%s	Duration:%d	Rent: %.2f	Price: %.2f	%nDescr: %s%n	Post: %s	Trailer:%s"
				+ "	Writers:%s	Actors:%s	%nViewer_rating:%.2f	Director:%s%nRaters: %s%n"
				+ "Sale Percent: %f	Sale Validity: %s", getProductCategory().getValue(),
				getId(), getName(), getReleaseDate().getYear(), getPgRating(), getDuration(), getRentCost(),getBuyCost(),
				getDescription(), getPoster(), getTrailer(), getWriters(), getActors(), getViewerRating(), getDirector(), getRaters(),
				getSalePercent(), getSaleValidity());
	}
	
	//Setters
	public void setDirector(String director) {
		if(Supp.isValidStr(director)) {
			this.director = director;
		}
	}
	
	//Getters
	public String getDirector() {
		return this.director;
	}
	
}
