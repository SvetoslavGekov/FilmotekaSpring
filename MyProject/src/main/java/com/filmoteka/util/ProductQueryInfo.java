package com.filmoteka.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.filmoteka.exceptions.InvalidProductQueryInfoException;
import com.filmoteka.model.nomenclatures.Genre;

public class ProductQueryInfo {
	private static final String[] ORDERED_OPTIONS = {"name","duration","buy_cost","rent_cost"};
	
	// Fields
	private String name;
	private int minReleaseYear;
	private int maxReleaseYear;
	private int minDuration;
	private int maxDuration;
	private double minBuyCost;
	private double maxBuyCost;
	private double minRentCost;
	private double maxRentCost;
	private List<Genre> genres = new ArrayList<>();
	private String orderedBy;
	private boolean isAscending;

	public ProductQueryInfo() {
		
	}
	
	public ProductQueryInfo(String name, int minReleaseYear, int maxReleaseYear, int minDuration, int maxDuration,
			double minBuyCost, double maxBuyCost, double minRentCost, double maxRentCost, List<Genre> genres,
			String orderedBy, boolean isAscending) throws InvalidProductQueryInfoException {
		setName(name);
		setMinReleaseYear(minReleaseYear);
		setMaxReleaseYear(maxReleaseYear);
		setMinDuration(minDuration);
		setMaxDuration(maxDuration);
		setMinBuyCost(minBuyCost);
		setMaxBuyCost(maxBuyCost);
		setMinRentCost(minRentCost);
		setMaxRentCost(maxRentCost);
		setGenres(genres);
		setOrderedBy(orderedBy);
		setIsAscending(isAscending);
	}

	// Setters
	public void setName(String name) {
		this.name = name;
	}

	public void setMinReleaseYear(int minReleaseYear) throws InvalidProductQueryInfoException {
		if (minReleaseYear >= 0) {
			this.minReleaseYear = minReleaseYear;
		}
		else {
			throw new InvalidProductQueryInfoException("Invalid query minimum release year.");
		}
	}

	public void setMaxReleaseYear(int maxReleaseYear) throws InvalidProductQueryInfoException {
		if (maxReleaseYear >= 0) {
			this.maxReleaseYear = maxReleaseYear;
		}
		else {
			throw new InvalidProductQueryInfoException("Invalid query maximum release year.");
		}
	}

	public void setMinDuration(int minDuration) throws InvalidProductQueryInfoException {
		if (minDuration >= 0) {
			this.minDuration = minDuration;
		}
		else {
			throw new InvalidProductQueryInfoException("Invalid query minimum duration.");
		}
	}

	public void setMaxDuration(int maxDuration) throws InvalidProductQueryInfoException {
		if (maxDuration >= 0) {
			this.maxDuration = maxDuration;
		}
		else {
			throw new InvalidProductQueryInfoException("Invalid query maximum duration.");
		}
	}

	public void setMinBuyCost(double minBuyCost) throws InvalidProductQueryInfoException {
		if (minBuyCost >= 0d) {
			this.minBuyCost = minBuyCost;
		}
		else {
			throw new InvalidProductQueryInfoException("Invalid query minimum buy cost.");
		}
	}

	public void setMaxBuyCost(double maxBuyCost) throws InvalidProductQueryInfoException {
		if (maxBuyCost >= 0d) {
			this.maxBuyCost = maxBuyCost;
		}
		else {
			throw new InvalidProductQueryInfoException("Invalid query maximum buy cost.");
		}
	}

	public void setMinRentCost(double minRentCost) throws InvalidProductQueryInfoException {
		if (minRentCost >= 0d) {
			this.minRentCost = minRentCost;
		}
		else {
			throw new InvalidProductQueryInfoException("Invalid query minimum rent cost.");
		}
	}

	public void setMaxRentCost(double maxRentCost) throws InvalidProductQueryInfoException {
		if (maxRentCost >= 0d) {
			this.maxRentCost = maxRentCost;
		}
		else {
			throw new InvalidProductQueryInfoException("Invalid query maximum rent cost.");
		}
	}

	public void setGenres(List<Genre> genres) {
		if (genres != null) {
			this.genres = genres;
		}
	}

	public void setOrderedBy(String orderedBy) throws InvalidProductQueryInfoException {
		ArrayList<String> validOptions = new ArrayList<String>(Arrays.asList(ORDERED_OPTIONS));
		if (Supp.isNotNullOrEmpty(orderedBy) && validOptions.contains(orderedBy)) {
			this.orderedBy = orderedBy;
		}
		else {
			throw new InvalidProductQueryInfoException("Invalid query ordering parameter.");
		}
	}
	
	public void setIsAscending(boolean isAscending) {
		this.isAscending = isAscending;
	}
	
	//Getters


	public String getName() {
		return this.name;
	}

	public int getMinReleaseYear() {
		return this.minReleaseYear;
	}

	public int getMaxReleaseYear() {
		return this.maxReleaseYear;
	}

	public int getMinDuration() {
		return this.minDuration;
	}

	public int getMaxDuration() {
		return this.maxDuration;
	}

	public double getMinBuyCost() {
		return this.minBuyCost;
	}

	public double getMaxBuyCost() {
		return this.maxBuyCost;
	}

	public double getMinRentCost() {
		return this.minRentCost;
	}

	public double getMaxRentCost() {
		return this.maxRentCost;
	}

	public List<Genre> getGenres() {
		return Collections.unmodifiableList(this.genres);
	}

	public String getOrderedBy() {
		return this.orderedBy;
	}

	public boolean getIsAscending() {
		return this.isAscending;
	}

	@Override
	public String toString() {
		return String.format("Name: %s%n"
				+ "MaxYear: %s%n"
				+ "MinYear: %s%n"
				+ "MaxDuration: %d%n"
				+ "MinDuration: %d%n"
				+ "MaxBuyCost: %.2f%n"
				+ "MinBuyCost: %.2f%n"
				+ "MaxRentCost: %.2f%n"
				+ "MinRentCost: %.2f%n"
				+ "Genres: %s%n"
				+ "OrderedBy: %s%n"
				+ "isAscending: %s%n", name, maxReleaseYear, minReleaseYear, maxDuration, minDuration, maxBuyCost, minBuyCost,
				maxRentCost, minRentCost, genres, orderedBy, isAscending);
	}
}
