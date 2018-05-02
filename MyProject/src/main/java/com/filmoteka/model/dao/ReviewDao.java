package com.filmoteka.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.filmoteka.dao.dbManager.DBManager;
import com.filmoteka.exceptions.InvalidOrderDataException;
import com.filmoteka.exceptions.InvalidProductDataException;
import com.filmoteka.exceptions.InvalidReviewDataException;
import com.filmoteka.exceptions.InvalidUserDataException;
import com.filmoteka.model.Review;

public class ReviewDao implements IReviewDao {
	
	// Fields
	private static ReviewDao instance;
	private Connection con;

	// Constructors
	private ReviewDao() {
		// Create the connection object from the DBManager
		this.con = DBManager.getInstance().getCon();
	}

	// Methods
	public synchronized static ReviewDao getInstance() {
		if (instance == null) {
			instance = new ReviewDao();
		}
		return instance;
	}
	
	@Override
	public List<Review> getReviewsByUserId(int userID) throws SQLException, InvalidUserDataException, 
		InvalidOrderDataException, InvalidProductDataException, InvalidReviewDataException{
		List<Review> reviews = new ArrayList<>();
		
		String sql = "SELECT r.review_id, r.product_id, u.username, r.content, r.date_time FROM reviews AS r\r\n" + 
						"JOIN users AS u ON r.user_id = u.user_id\r\n" + 
						"WHERE u.user_id = ? ORDER BY r.date_time DESC;";
		
		try(PreparedStatement ps = con.prepareStatement(sql);){
			ps.setInt(1, userID);
		
			try(ResultSet rs = ps.executeQuery()){
				while(rs.next()) {
					
					Review review = new Review(
											  rs.getLong("review_id"), 
											  rs.getLong("product_id"), 
											  rs.getString("username"), 
											  rs.getString("content"), 
											  rs.getTimestamp("date_time").toLocalDateTime()
											  );
					
					reviews.add(review);
				}
			}
		}
		
		return reviews;
	}
	
	@Override
	public List<Review> getReviewsByProductId(int productID) throws SQLException, InvalidUserDataException,
		InvalidOrderDataException, InvalidProductDataException, InvalidReviewDataException{
		List<Review> reviews = new ArrayList<>();
		
		String sql = "SELECT r.review_id, r.product_id, u.username, r.content, r.date_time FROM reviews AS r\r\n" + 
						"JOIN users AS u ON r.user_id = u.user_id\r\n" + 
						"WHERE r.product_id = ? ORDER BY r.date_time DESC;";
		
		try(PreparedStatement ps = con.prepareStatement(sql);){
			ps.setInt(1, productID);
		
			try(ResultSet rs = ps.executeQuery()){
				while(rs.next()) {
					
					Review review = new Review(
											  rs.getLong("review_id"), 
											  rs.getLong("product_id"), 
											  rs.getString("username"), 
											  rs.getString("content"), 
											  rs.getTimestamp("date_time").toLocalDateTime()
											  );
					
					reviews.add(review);
				}
			}
		}
		
		return reviews;
	}

}
