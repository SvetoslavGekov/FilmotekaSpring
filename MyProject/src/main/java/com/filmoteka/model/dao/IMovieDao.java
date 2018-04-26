package com.filmoteka.model.dao;

import java.sql.SQLException;
import java.util.Collection;

import com.filmoteka.exceptions.InvalidProductDataException;
import com.filmoteka.model.Movie;
import com.filmoteka.model.Product;

public interface IMovieDao {
	void saveMovie(Movie m) throws SQLException, InvalidProductDataException;
	
	void updateMovie(Movie m) throws SQLException;
	
	Collection<Movie> getAllMovies() throws SQLException, InvalidProductDataException;
	
	Collection<Product> getMoviesBySubstring(String substring) throws SQLException, InvalidProductDataException;
}
