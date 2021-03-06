package com.filmoteka.model.dao.nomenclatures;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.filmoteka.dao.dbManager.DBManager;
import com.filmoteka.exceptions.InvalidGenreDataException;
import com.filmoteka.model.nomenclatures.Genre;

@Component
public final class GenreDao implements IGenreDao {
	//Fields
	@Autowired
	private DBManager dbManager;

	public GenreDao() {
	}
	
	@Override
	public void saveGenre(Genre g) throws SQLException, InvalidGenreDataException {
		try(PreparedStatement ps = dbManager.getCon().prepareStatement("INSERT INTO genres (value) VALUES(?);", PreparedStatement.RETURN_GENERATED_KEYS)){
			ps.setString(1, g.getValue());
			//If the insertion is successful
			if(ps.executeUpdate() > 0) {
				//Update the genre's Id
				try(ResultSet rs = ps.getGeneratedKeys()){
					rs.next();
					g.setId(rs.getInt("GENERATED_KEY"));
				}
			}
		}
	}

	@Override
	public void updateGenre(Genre g) throws SQLException {
		try(PreparedStatement ps = dbManager.getCon().prepareStatement("UPDATE genres SET value = ? WHERE genre_id = ?;")){
			ps.setString(1, g.getValue());
			ps.setInt(2, g.getId());
			ps.executeUpdate();
		}
	}

	@Override
	public Map<Integer,Genre> getAllGenres() throws SQLException, InvalidGenreDataException {
		TreeMap<Integer, Genre> allGenres = new TreeMap<Integer,Genre>();
		try(PreparedStatement ps = dbManager.getCon().prepareStatement("SELECT genre_id, value FROM genres ORDER BY genre_id;");){
			try(ResultSet rs = ps.executeQuery();){
				//While there are genres to be created
				while(rs.next()) {
					//Create next genre with full data
					Genre g = new Genre(rs.getInt("genre_id"), rs.getString("value"));
					allGenres.put(g.getId(),g);
				}
			}
		}
		return allGenres;
	}

	@Override
	public Genre getGenreById(int id) throws SQLException, InvalidGenreDataException {
		Genre genre = null;
		try(PreparedStatement ps = dbManager.getCon().prepareStatement("SELECT genre_id, value FROM genres WHERE genre_id = ?;")){
			ps.setInt(1, id);
			try(ResultSet rs = ps.executeQuery()){
				//Check if there is anything returned from the query
				if(rs.next()) {
					//Create the genre
					genre = new Genre(rs.getInt("genre_id"), rs.getString("value"));
				}
			}
		}
		return genre;
	}
}
