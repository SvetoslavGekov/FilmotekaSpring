package com.filmoteka.model.dao;

import java.sql.Connection;
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

import com.filmoteka.dao.dbManager.DBManager;
import com.filmoteka.exceptions.InvalidProductDataException;
import com.filmoteka.model.Movie;
import com.filmoteka.model.Product;
import com.filmoteka.model.nomenclatures.Genre;
import com.filmoteka.model.nomenclatures.ProductCategory;
import com.filmoteka.util.WebSite;

public final class MovieDao implements IMovieDao {
	//Fields
	private static MovieDao instance;
	private Connection con;
	
	//Constructors
	private MovieDao() {
		//Create the connection object from the DBManager
		this.con = DBManager.getInstance().getCon();
	}
	
	//Methods
	public synchronized static MovieDao getInstance() {
		if(instance == null) {
			instance = new MovieDao();
		}
		return instance;
	}

	@Override
	//Save a newly created movie with the basic mandatory fields
	public void saveMovie(Movie m) throws SQLException, InvalidProductDataException {
		synchronized (con) {
			con.setAutoCommit(false);
			try {
				//Insert the movie in the products table
				ProductDao.getInstance().saveProduct(m);
				
				//Insert the movie in the movies table
				try(PreparedStatement ps = con.prepareStatement("INSERT INTO movies (product_id, director) VALUES (?,?);")){
					ps.setInt(1, m.getId());
					ps.setString(2, m.getDirector());
					ps.executeUpdate();
				}
				con.commit();
			}
			catch(SQLException e) {
				//Rollback 
				con.rollback();
				throw e;
			}
			finally {
				con.setAutoCommit(true);
			}
		}
	}

	@Override
	public void updateMovie(Movie m) throws SQLException {
		synchronized (con) {
			con.setAutoCommit(false);
			try {
				//Update the product basic information
				ProductDao.getInstance().updateProduct(m);
				
				//Update the movie specific information
				try(PreparedStatement ps = con.prepareStatement("UPDATE movies SET director = ? WHERE product_id = ?;")){
					ps.setString(1, m.getDirector()); //Movie director
					ps.setInt(2, m.getId()); //Movie Id
					ps.executeUpdate();
				}
				con.commit();
			}
			catch (SQLException e) {
				//Rollback
				con.rollback();
				throw e;
			}
			finally {
				con.setAutoCommit(true);
			}
		}
		
	}

	@Override
	public Collection<Movie> getAllMovies() throws SQLException, InvalidProductDataException {
		Collection<Movie> allMovies = new ArrayList<Movie>();
		try(PreparedStatement ps = con.prepareStatement("SELECT m.director, p.product_id, p.name, p.category_id, p.release_year, p.pg_rating,"
				+ " p.duration, p.rent_cost, p.buy_cost, p.description, p.poster, p.trailer, p.writers, p.actors,"
				+ " p.sale_percent, p.sale_validity FROM movies AS m" + 
				"	INNER JOIN products AS p USING (product_id);")){

			try(ResultSet rs = ps.executeQuery();){
				while(rs.next()) {
					
					int movieId = rs.getInt("product_id");
					Date saleValidity = rs.getDate("sale_validity");
					ProductCategory productCategory = WebSite.getProductCategoryById(rs.getInt("category_id"));
					
					//Collect the movie's genres
					Set<Genre> genres = new HashSet<>(ProductDao.getInstance().getProductGenresById(movieId));
					
					//Collect the movie's raters
					Map<Integer, Double> raters = new TreeMap<>(ProductDao.getInstance().getProductRatersById(movieId));
					
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
	public Collection<Product> getMoviesBySubstring(String substring) throws SQLException, InvalidProductDataException {
		
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
		try(PreparedStatement ps = con.prepareStatement(sql)){
			ps.setString(1, '%'+substring+'%');
			try(ResultSet rs = ps.executeQuery();){
				while(rs.next()) {
					
					int movieId = rs.getInt("product_id");
					Date saleValidity = rs.getDate("sale_validity");
					ProductCategory productCategory = WebSite.getProductCategoryById(rs.getInt("category_id"));
					
					//Collect the movie's genres
					Set<Genre> genres = new HashSet<>(ProductDao.getInstance().getProductGenresById(movieId));
					
					//Collect the movie's raters
					Map<Integer, Double> raters = new TreeMap<>(ProductDao.getInstance().getProductRatersById(movieId));
					
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
