package com.filmoteka.model.dao;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.filmoteka.dao.dbManager.DBManager;
import com.filmoteka.exceptions.InvalidGenreDataException;
import com.filmoteka.exceptions.InvalidProductCategoryDataException;
import com.filmoteka.exceptions.InvalidProductDataException;
import com.filmoteka.model.Movie;
import com.filmoteka.model.Product;
import com.filmoteka.model.dao.nomenclatures.ProductCategoryDao;
import com.filmoteka.model.nomenclatures.Genre;
import com.filmoteka.model.nomenclatures.ProductCategory;

@Component
public final class MovieDao implements IMovieDao {
	//Fields
	@Autowired
	private DBManager dbManager;
	@Autowired
	private ProductDao productDao;
	@Autowired
	private ProductCategoryDao productCategoryDao;
	
	@Override
	//Save a newly created movie with the basic mandatory fields
	public void saveMovie(Movie m) throws SQLException, InvalidProductDataException {
		synchronized (dbManager.getCon()) {
			dbManager.getCon().setAutoCommit(false);
			try {
				//Insert the movie in the products table
				productDao.saveProduct(m);
				
				//Insert the movie in the movies table
				try(PreparedStatement ps = dbManager.getCon().prepareStatement("INSERT INTO movies (product_id, director) VALUES (?,?);")){
					ps.setInt(1, m.getId());
					ps.setString(2, m.getDirector());
					ps.executeUpdate();
				}
				dbManager.getCon().commit();
			}
			catch(SQLException e) {
				//Rollback 
				dbManager.getCon().rollback();
				throw e;
			}
			finally {
				dbManager.getCon().setAutoCommit(true);
			}
		}
	}

	@Override
	public void updateMovie(Movie m) throws SQLException {
		synchronized (dbManager.getCon()) {
			dbManager.getCon().setAutoCommit(false);
			try {
				//Update the product basic information
				productDao.updateProduct(m);
				
				//Update the movie specific information
				try(PreparedStatement ps = dbManager.getCon().prepareStatement("UPDATE movies SET director = ? WHERE product_id = ?;")){
					ps.setString(1, m.getDirector()); //Movie director
					ps.setInt(2, m.getId()); //Movie Id
					ps.executeUpdate();
				}
				dbManager.getCon().commit();
			}
			catch (SQLException e) {
				//Rollback
				dbManager.getCon().rollback();
				throw e;
			}
			finally {
				dbManager.getCon().setAutoCommit(true);
			}
		}
		
	}

	@Override
	public Collection<Movie> getAllMovies() throws SQLException, InvalidProductDataException,
	InvalidGenreDataException, InvalidProductCategoryDataException {
		Collection<Movie> allMovies = new ArrayList<Movie>();
		try(PreparedStatement ps = dbManager.getCon().prepareStatement("SELECT m.director, p.product_id, p.name, p.category_id, p.release_year, p.pg_rating,"
				+ " p.duration, p.rent_cost, p.buy_cost, p.description, p.poster, p.trailer, p.writers, p.actors,"
				+ " p.sale_percent, p.sale_validity FROM movies AS m" + 
				"	INNER JOIN products AS p USING (product_id);")){

			try(ResultSet rs = ps.executeQuery();){
				while(rs.next()) {
					
					int movieId = rs.getInt("product_id");
					Date saleValidity = rs.getDate("sale_validity");
					ProductCategory productCategory = productCategoryDao.getProductCategoryById(rs.getInt("category_id"));
					
					//Collect the movie's genres
					Set<Genre> genres = new HashSet<>(productDao.getProductGenresById(movieId));
					
					//Collect the movie's raters
					Map<Integer, Double> raters = new TreeMap<>(productDao.getProductRatersById(movieId));
					
					//Construct the new movie
					Movie m = new Movie(movieId, //Movie ID
							rs.getString("name"), //Name
							productCategory, //Product category
							rs.getDate("release_year").toLocalDate(),//Release year
							rs.getString("pg_rating"),//PG Rating
							rs.getInt("duration"),//Duration
							rs.getDouble("rent_cost"),//Rent cost
							rs.getDouble("buy_cost"),//Buy Cost
							rs.getString("description"),//Description
							rs.getString("poster"),//Poster
							rs.getString("trailer"),//Trailer
							rs.getString("writers"),//Writers
							rs.getString("actors"),//Actors
							genres,//Genres
							raters,//Raters
							rs.getDouble("sale_percent"),//Sale percent
							(saleValidity != null ? saleValidity.toLocalDate() : null),//Sale validity
							rs.getString("director"));//Director
					
					allMovies.add(m);
				}
				
			}
		}
		return allMovies;
	}
	
	@Override
	public Collection<Product> getMoviesBySubstring(String substring) throws SQLException, InvalidProductDataException,
	InvalidGenreDataException, InvalidProductCategoryDataException {
		
		String sql = "SELECT m.director, p.product_id, p.name, p.category_id, p.release_year, "
							+ "p.pg_rating, p.duration, p.rent_cost, "
							+ "p.buy_cost, p.description, p.poster, "
							+ "p.trailer, p.writers, p.actors, "
							+ "p.sale_percent, p.sale_validity"
					+ " FROM movies AS m "
					+ "JOIN products AS p "
					+ "ON m.product_id = p.product_id "
					+ "WHERE p.name LIKE ?;";
		
		Collection<Product> allMoviesBySubStr = new ArrayList<Product>();
		try(PreparedStatement ps = dbManager.getCon().prepareStatement(sql)){
			ps.setString(1, '%'+substring+'%');
			try(ResultSet rs = ps.executeQuery();){
				while(rs.next()) {
					
					int movieId = rs.getInt("product_id");
					Date saleValidity = rs.getDate("sale_validity");
					ProductCategory productCategory = productCategoryDao.getProductCategoryById(rs.getInt("category_id"));
					
					//Collect the movie's genres
					Set<Genre> genres = new HashSet<>(productDao.getProductGenresById(movieId));
					
					//Collect the movie's raters
					Map<Integer, Double> raters = new TreeMap<>(productDao.getProductRatersById(movieId));
					
					//Construct the new movie
					//Construct the new movie
					Movie m = new Movie(movieId, //Movie ID
							rs.getString("name"), //Name
							productCategory, //Product category
							rs.getDate("release_year").toLocalDate(),//Release year
							rs.getString("pg_rating"),//PG Rating
							rs.getInt("duration"),//Duration
							rs.getDouble("rent_cost"),//Rent cost
							rs.getDouble("buy_cost"),//Buy Cost
							rs.getString("description"),//Description
							rs.getString("poster"),//Poster
							rs.getString("trailer"),//Trailer
							rs.getString("writers"),//Writers
							rs.getString("actors"),//Actors
							genres,//Genres
							raters,//Raters
							rs.getDouble("sale_percent"),//Sale percent
							(saleValidity != null ? saleValidity.toLocalDate() : null),//Sale validity
							rs.getString("director"));//Directors
					
					allMoviesBySubStr.add(m);
				}
				
			}
		}
		return allMoviesBySubStr;
	}
}
