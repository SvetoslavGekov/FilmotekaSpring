package com.filmoteka.model.dao;

import java.sql.SQLException;
import java.util.Collection;

import com.filmoteka.exceptions.InvalidProductDataException;
import com.filmoteka.model.TVSeries;

public interface ITVSeriesDao {
	void saveTVSeries(TVSeries tvs) throws SQLException, InvalidProductDataException;
	
	void updateTVSeries(TVSeries tvs) throws SQLException;
	
	Collection<TVSeries> getAllTVSeries() throws SQLException, InvalidProductDataException;
	
	Collection<TVSeries> getTVSeriesBySubstring(String substring) throws SQLException, InvalidProductDataException;
}
