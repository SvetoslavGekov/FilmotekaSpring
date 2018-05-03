package com.filmoteka.model;

import java.time.LocalDate;
import java.util.Map;
import java.util.Set;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

import com.filmoteka.exceptions.InvalidProductDataException;
import com.filmoteka.model.nomenclatures.Genre;
import com.filmoteka.model.nomenclatures.ProductCategory;

@Component
public class TVSeries extends Product {
	private static final byte MAX_SEASON = 127;
	
	//Optional fields
	private int season;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate finishedAiring;
	
	//Constructors
	public TVSeries() {
		
	};
	
	//Constructor for creating a new TV Series
	public TVSeries(String name, ProductCategory productCategory, LocalDate releaseDate, String pgRating,
			int duration, double rentCost, double buyCost)
			throws InvalidProductDataException {
		super(name, productCategory, releaseDate, pgRating, duration, rentCost, buyCost);
	}
	
	//Constructor for creating a new TV Series with all available information
	public TVSeries( String name, ProductCategory productCategory, LocalDate releaseDate, String pgRating, int duration, double rentCost,
			double buyCost, String description,String poster, String trailer, String writers, String actors,
			Set<Genre> genres, double salePercent, LocalDate saleValidity, int season, LocalDate finishedAiring) 
					throws InvalidProductDataException {
		super(name, productCategory, releaseDate, pgRating, duration, rentCost, buyCost, description,poster, trailer, writers, actors,
				genres, salePercent, saleValidity);
		setSeason(season);
		setFinishedAiring(finishedAiring);
	}
	
	//Constructor for loading a TV Series from the DB
	public TVSeries(int id, String name, ProductCategory productCategory, LocalDate releaseDate, String pgRating, int duration, double rentCost,
			double buyCost, String description,String poster, String trailer, String writers, String actors,
			Set<Genre> genres, Map<Integer,Double> raters, double salePercent, LocalDate saleValidity, int season, LocalDate finishedAiring)
					throws InvalidProductDataException {
		super(id, name, productCategory,releaseDate, pgRating, duration, rentCost, buyCost, description,poster, trailer, writers,
				actors, genres,raters, salePercent, saleValidity);
		setSeason(season);
		setFinishedAiring(finishedAiring);
	}
	//Methods
	@Override
	public String toString() {
		return String.format("Type:%s	Id:%d	Name:%s	Year:%s	PG:%s	Duration:%d	Rent: %.2f	Price: %.2f	%nDescr: %s%n	Post: %s	Trailer:%s"
				+ "	Writers:%s	Actors:%s	%nViewer_rating:%.2f	Season:%d	FinishedAiring:%s %nRaters: %s%n"
				+ "Sale Percent: %f	Sale Validity: %s", getProductCategory().getValue(),
				this.getId(), getName(), getReleaseDate().getYear(), getPgRating(), getDuration(), getRentCost(),getBuyCost(),
				getDescription(), getPoster(), getTrailer(), getWriters(), getActors(), getViewerRating(), getSeason(),
				getFinishedAiring(), getRaters(),getSalePercent(), getSaleValidity());
	}
	
	//Setters
	public void setSeason(int season) {
		if(season >= 0 && season <= MAX_SEASON) {
			this.season = season;
		}
		else {
			this.season = 0;
		}
	}
	
	public void setFinishedAiring(LocalDate finishedAiring) {
		this.finishedAiring = finishedAiring;
	}
	
	//Getters
	public Integer getSeason() {
		return this.season;
	}
	
	public LocalDate getFinishedAiring() {
		return this.finishedAiring;
	}
	
}
