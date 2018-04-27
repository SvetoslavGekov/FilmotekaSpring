package com.filmoteka.model.dao;

import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.filmoteka.exceptions.InvalidProductDataException;
import com.filmoteka.model.Product;
import com.filmoteka.model.nomenclatures.Genre;
import com.filmoteka.util.productFilters.ProductQueryInfo;


public interface IProductDao {
	void saveProduct(Product p) throws SQLException, InvalidProductDataException;
	
	void updateProduct(Product p) throws SQLException;
	
	Collection<Genre> getProductGenresById(int id) throws SQLException;
	
	Map<Integer,Double> getProductRatersById(int movieId) throws SQLException;
	
	Collection<Product> getProductsByIdentifiers(List<Integer> identifiers) throws SQLException, InvalidProductDataException;
	
	Product getProductById(int productId) throws SQLException, InvalidProductDataException;
	
	Collection<Product> getAllProducts() throws SQLException, InvalidProductDataException;

	void deleteExpiredProducts() throws SQLException;

	List<Integer> getFilteredProducts(ProductQueryInfo filter) throws SQLException;


	
}
