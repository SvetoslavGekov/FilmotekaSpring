package com.filmoteka.model.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import com.filmoteka.dao.dbManager.DBManager;
import com.filmoteka.exceptions.InvalidGenreDataException;
import com.filmoteka.exceptions.InvalidProductCategoryDataException;
import com.filmoteka.exceptions.InvalidProductDataException;
import com.filmoteka.model.Product;
import com.filmoteka.model.TVSeries;
import com.filmoteka.model.dao.nomenclatures.ProductCategoryDao;
import com.filmoteka.model.nomenclatures.Genre;
import com.filmoteka.model.nomenclatures.ProductCategory;

public final class TVSeriesDao implements ITVSeriesDao {
	// Fields
	private static TVSeriesDao instance;
	private Connection con;

	// Constructors
	private TVSeriesDao() {
		// Create the connection object from the DBManager
		this.con = DBManager.getInstance().getCon();
	}

	// Methods
	public synchronized static TVSeriesDao getInstance() {
		if (instance == null) {
			instance = new TVSeriesDao();
		}
		return instance;
	}

	@Override
	public void saveTVSeries(TVSeries tvs) throws SQLException, InvalidProductDataException {
		synchronized (con) {
			con.setAutoCommit(false);
			try {
				// Insert the TV Series in the products table
				ProductDao.getInstance().saveProduct(tvs);
				
				LocalDate finishedAiring = tvs.getFinishedAiring();
				// Insert the TV Series in the TV Series table
				try (PreparedStatement ps = con.prepareStatement("INSERT INTO tvseries (product_id, season, finished_airing)"
						+ " VALUES (?,?,?);")) {
					ps.setInt(1, tvs.getId());
					ps.setInt(2, tvs.getSeason());
					ps.setDate(3, finishedAiring != null ? Date.valueOf(finishedAiring) : null);
					ps.executeUpdate();
				}
				con.commit();
			}
			catch (SQLException e) {
				// Rollback
				con.rollback();
				throw e;
			}
			finally {
				con.setAutoCommit(true);
			}
		}
	}

	@Override
	public void updateTVSeries(TVSeries tvs) throws SQLException {
		synchronized (con) {
			con.setAutoCommit(false);
			try {
				// Update the product basic information
				ProductDao.getInstance().updateProduct(tvs);
				LocalDate finishedAiring = tvs.getFinishedAiring();
				
				// Update the TV Series specific information
				try (PreparedStatement ps = con.prepareStatement(
						"UPDATE tvseries SET season = ?, finished_airing = ? WHERE product_id = ?;")) {
					ps.setInt(1, tvs.getSeason()); //Series season
					ps.setDate(2, finishedAiring != null ? Date.valueOf(finishedAiring) : null); //Series finished airing date
					ps.setInt(3, tvs.getId()); //Series id
					ps.executeUpdate();
				}
				con.commit();
			}
			catch (SQLException e) {
				// Rollback
				con.rollback();
				throw e;
			}
			finally {
				con.setAutoCommit(true);
			}
		}
	}

	@Override
	public Collection<TVSeries> getAllTVSeries() throws SQLException, InvalidProductDataException,
	InvalidGenreDataException, InvalidProductCategoryDataException {
		Collection<TVSeries> allTVSeries = new ArrayList<TVSeries>();
		try (PreparedStatement ps = con.prepareStatement("SELECT tv.season, tv.finished_airing, p.product_id, p.name, p.category_id, p.release_year,"
						+ " p.pg_rating, p.duration, p.rent_cost, p.buy_cost, p.description, p.poster, p.trailer, p.writers, p.actors,"
						+ " p.sale_percent, p.sale_validity FROM tvseries AS tv"
						+ "	INNER JOIN products AS p USING (product_id);")) {

			try (ResultSet rs = ps.executeQuery();) {
				while (rs.next()) {

					int tvsID = rs.getInt("product_id");
					Date saleValidity = rs.getDate("sale_validity");
					ProductCategory productCategory = ProductCategoryDao.getInstance().getProductCategoryById(rs.getInt("category_id"));
					
					// Collect the tv series's genres
					Set<Genre> genres = new HashSet<>(ProductDao.getInstance().getProductGenresById(tvsID));

					// Collect the tv series's raters
					Map<Integer, Double> raters = new TreeMap<>(ProductDao.getInstance().getProductRatersById(tvsID));

					// Construct the new tv series
					Date finishedAiring = rs.getDate("finished_airing");
					TVSeries tvs = new TVSeries(tvsID, // Id
							rs.getString("name"), // Name
							productCategory, //Product category
							rs.getDate("release_year").toLocalDate(), // Release year
							rs.getString("pg_rating"), // Pg Rating
							rs.getInt("duration"), // Duration
							rs.getDouble("rent_cost"),//Original Rent Cost 
							rs.getDouble("buy_cost"),//Original Buy Cost 
							rs.getString("description"),//Description
							rs.getString("poster"),//Poster 
							rs.getString("trailer"), //Trailer
							rs.getString("writers"), //Writers
							rs.getString("actors"), //Actors 
							genres,//Genres 
							raters,//Raters
							rs.getDouble("sale_percent"), // Sale percent
							(saleValidity != null ? saleValidity.toLocalDate() : null), // Sale validity
							rs.getInt("season"), //Season
							(finishedAiring != null) ? finishedAiring.toLocalDate() : null); //Finished Airing

					allTVSeries.add(tvs);
				}

			}
		}
		if (allTVSeries.isEmpty()) {
			return Collections.emptyList();
		}
		return allTVSeries;
	}

	@Override
	public Collection<Product> getTVSeriesBySubstring(String substring)
			throws SQLException, InvalidProductDataException, InvalidGenreDataException, InvalidProductCategoryDataException {

		String sql = "SELECT tv.season, tv.finished_airing, "
							+ "p.product_id, p.name, p.category_id, p.release_year, "
							+ "p.pg_rating, p.duration, p.rent_cost, "
							+ "p.buy_cost, p.description, p.poster, "
							+ "p.trailer, p.writers, p.actors, "
							+ "p.sale_percent, p.sale_validity "
					+ "FROM tvseries AS tv " + "JOIN products AS p "
					+ "ON tv.product_id = p.product_id " + "WHERE p.name LIKE ?;";

		Collection<Product> allTVSeriesBySubstring = new ArrayList<Product>();

		try (PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setString(1,'%'+substring+'%');
			try (ResultSet rs = ps.executeQuery();) {
				while (rs.next()) {
					
					int tvsID = rs.getInt("product_id");
					Date saleValidity = rs.getDate("sale_validity");
					ProductCategory productCategory = ProductCategoryDao.getInstance().getProductCategoryById(rs.getInt("category_id"));
					
					// Collect the tv series's genres
					Set<Genre> genres = new HashSet<>(ProductDao.getInstance().getProductGenresById(tvsID));

					// Collect the tv series's raters
					Map<Integer, Double> raters = new TreeMap<>(ProductDao.getInstance().getProductRatersById(tvsID));

					// Construct the tv series
					Date finishedAiring = rs.getDate("finished_airing");
					TVSeries tvs = new TVSeries(tvsID, // Id
							rs.getString("name"), // Name
							productCategory, //Product category
							rs.getDate("release_year").toLocalDate(), // Release year
							rs.getString("pg_rating"), // Pg Rating
							rs.getInt("duration"), // Duration
							rs.getDouble("rent_cost"),//Original Rent Cost 
							rs.getDouble("buy_cost"),//Original Buy Cost 
							rs.getString("description"),//Description
							rs.getString("poster"),//Poster 
							rs.getString("trailer"), //Trailer
							rs.getString("writers"), //Writers
							rs.getString("actors"), //Actors 
							genres,//Genres 
							raters,//Raters
							rs.getDouble("sale_percent"), // Sale percent
							(saleValidity != null ? saleValidity.toLocalDate() : null), // Sale validity
							rs.getInt("season"), //Season
							(finishedAiring != null) ? finishedAiring.toLocalDate() : null); //Finished Airing

					allTVSeriesBySubstring.add(tvs);
				}

			}
		}
		if (allTVSeriesBySubstring.isEmpty()) {
			return Collections.emptyList();
		}
		return allTVSeriesBySubstring;
	}

}
