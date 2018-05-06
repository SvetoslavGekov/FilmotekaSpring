package com.filmoteka.manager;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.filmoteka.exceptions.InvalidGenreDataException;
import com.filmoteka.model.dao.nomenclatures.GenreDao;
import com.filmoteka.model.nomenclatures.Genre;
@Component
public final class GenreManager {
	//Fields
	@Autowired
	private GenreDao dao;
	
	public void createNewGenre(String genreName) throws SQLException, InvalidGenreDataException {
		//Create new genre with the given data
		Genre genre = new Genre(genreName);
		//Add genre to DB
		dao.saveGenre(genre);
	}
	
	public void updateExistingGenre(Genre g, String newGenreName) throws InvalidGenreDataException, SQLException {
		//Set new genre characteristics
		g.setValue(newGenreName);
		//Update characteristics in DB
		dao.updateGenre(g);

	}
}
