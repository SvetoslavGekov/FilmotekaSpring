package com.filmoteka.model.dao;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Map;

import com.filmoteka.exceptions.InvalidGenreDataException;
import com.filmoteka.exceptions.InvalidOrderDataException;
import com.filmoteka.exceptions.InvalidProductCategoryDataException;
import com.filmoteka.exceptions.InvalidProductDataException;
import com.filmoteka.model.Order;
import com.filmoteka.model.Product;

public interface IOrderDao {
	Order getOrderById(int orderId) throws SQLException, InvalidOrderDataException,
	InvalidProductDataException, InvalidGenreDataException, InvalidProductCategoryDataException;
	
	Map<Product, LocalDate> getOrderProductsById(int orderId) throws SQLException, InvalidProductDataException,
	InvalidGenreDataException, InvalidProductCategoryDataException;
	
	void saveOrder(Order order) throws SQLException, InvalidOrderDataException;
	
}
