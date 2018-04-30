package com.filmoteka.model.dao;

import java.util.List;
 
import com.filmoteka.model.Review;

public interface IReviewDao {
	
	List<Review> getReviewsByProductId(int productID) throws Exception;
	
	List<Review> getReviewsByUserId(int userID) throws Exception;
}
