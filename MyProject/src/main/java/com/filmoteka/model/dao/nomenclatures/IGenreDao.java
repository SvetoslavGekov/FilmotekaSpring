package com.filmoteka.model.dao.nomenclatures;

import java.sql.SQLException;
import java.util.Map;

import com.filmoteka.exceptions.InvalidGenreDataException;
import com.filmoteka.model.nomenclatures.Genre;

public interface IGenreDao {
	
	void saveGenre(Genre g) throws SQLException, InvalidGenreDataException;
	
	void updateGenre(Genre g) throws SQLException;
	
	Map<Integer,Genre> getAllGenres() throws SQLException, InvalidGenreDataException;
}
