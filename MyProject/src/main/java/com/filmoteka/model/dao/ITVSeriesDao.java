package com.filmoteka.model.dao;

import java.sql.SQLException;
import java.util.Collection;

import com.filmoteka.exceptions.InvalidGenreDataException;
import com.filmoteka.exceptions.InvalidProductCategoryDataException;
import com.filmoteka.exceptions.InvalidProductDataException;
import com.filmoteka.model.Product;
import com.filmoteka.model.TVSeries;

public interface ITVSeriesDao {
	void saveTVSeries(TVSeries tvs) throws SQLException, InvalidProductDataException;
	
	void updateTVSeries(TVSeries tvs) throws SQLException;
	
	Collection<TVSeries> getAllTVSeries() throws SQLException, InvalidProductDataException,
	InvalidGenreDataException, InvalidProductCategoryDataException;
	
	Collection<Product> getTVSeriesBySubstring(String substring) throws SQLException, InvalidProductDataException,
	InvalidGenreDataException, InvalidProductCategoryDataException;
}
