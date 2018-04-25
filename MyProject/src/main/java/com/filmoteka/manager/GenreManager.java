package com.filmoteka.manager;

import java.sql.SQLException;

import com.filmoteka.exceptions.InvalidGenreDataException;
import com.filmoteka.model.dao.nomenclatures.GenreDao;
import com.filmoteka.model.nomenclatures.Genre;
import com.filmoteka.util.WebSite;

public final class GenreManager {
	//Fields
	private static GenreManager instance;
	private GenreDao dao;
	
	//Constructor
	private GenreManager() {
		//Instantiate the dao object
		dao = GenreDao.getInstance();
	}
	
	//Methods
	public synchronized static GenreManager getInstance() {
		if(instance == null) {
			instance = new GenreManager();
		}
		return instance;
	}
	
	public void createNewGenre(String genreName) throws SQLException, InvalidGenreDataException {
		//Create new genre with the given data
		Genre genre = new Genre(genreName);
		//Add genre to DB
		dao.saveGenre(genre);
		//Add genre to the GENRES collection
		WebSite.addGenre(genre);
	}
	
	public void updateExistingGenre(Genre g, String newGenreName) throws InvalidGenreDataException, SQLException {
		//Set new genre characteristics
		g.setValue(newGenreName);
		//Update characteristics in DB
		dao.updateGenre(g);

	}
}
