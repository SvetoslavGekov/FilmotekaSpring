package com.filmoteka.util.taskExecutors;

import java.sql.SQLException;
import java.util.concurrent.Callable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.filmoteka.model.dao.ProductDao;

@Component
public class ExpiredProductsDeleter implements Callable<Boolean> {
	//Fields
	@Autowired
	private ProductDao productDao;
	
	@Override
	public Boolean call() throws SQLException, InterruptedException {
		//Deletes all expired products from the user_has_products table in the database
		productDao.deleteExpiredProducts();
		//Sleep for 0.5 seconds so we don't start the process a million times over and over again
		Thread.sleep(500);
		return true;
	}
	
	
}
