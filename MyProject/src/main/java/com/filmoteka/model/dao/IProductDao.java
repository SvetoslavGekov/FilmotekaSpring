package com.filmoteka.model.dao;

import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.filmoteka.exceptions.InvalidGenreDataException;
import com.filmoteka.exceptions.InvalidProductCategoryDataException;
import com.filmoteka.exceptions.InvalidProductDataException;
import com.filmoteka.exceptions.InvalidProductQueryInfoException;
import com.filmoteka.model.Product;
import com.filmoteka.model.User;
import com.filmoteka.model.nomenclatures.Genre;
import com.filmoteka.util.productFilters.ProductQueryInfo;


public interface IProductDao {
	void saveProduct(Product p) throws SQLException, InvalidProductDataException;
	
	void updateProduct(Product p) throws SQLException;
	
	Collection<Genre> getProductGenresById(int id) throws SQLException, InvalidGenreDataException;
	
	Map<Integer,Double> getProductRatersById(int movieId) throws SQLException;
	
	Collection<Product> getProductsByIdentifiers(List<Integer> identifiers) throws SQLException, InvalidProductDataException,
	InvalidGenreDataException, InvalidProductCategoryDataException;
	
	Product getProductById(int productId) throws SQLException, InvalidProductDataException, InvalidGenreDataException,
	InvalidProductCategoryDataException;
	
	Collection<Product> getAllProducts() throws SQLException, InvalidProductDataException, InvalidGenreDataException,
	InvalidProductCategoryDataException;

	void deleteExpiredProducts() throws SQLException;

	List<Product> getFilteredProducts(ProductQueryInfo filter) throws SQLException, InvalidProductDataException,
	InvalidGenreDataException, InvalidProductCategoryDataException;

	ProductQueryInfo getFilterInfo() throws SQLException, InvalidProductQueryInfoException, InvalidGenreDataException;
	
	void rateProduct(User user, Product product, double rating) throws SQLException;
	
	Collection<Product> getCheapestProducts(Integer limit) throws SQLException, InvalidProductDataException,
	InvalidGenreDataException, InvalidProductCategoryDataException;
	
	Collection<Product> getHighestRatedProducts(Integer limit) throws SQLException, InvalidProductDataException,
	InvalidGenreDataException, InvalidProductCategoryDataException;
	
	Collection<Product> getMostPopularProducts(Integer limit) throws SQLException, InvalidProductDataException,
	InvalidGenreDataException, InvalidProductCategoryDataException;
	
	Collection<Product> getProductsOnSale(Integer limit) throws SQLException, InvalidProductDataException,
	InvalidGenreDataException, InvalidProductCategoryDataException;
	
	Map<Integer, Map<Integer, Double>> getProductRatersById(List<Integer> productIdentifiers) throws SQLException;
	
	Map<Integer, Collection<Genre>> getProductGenresById(List<Integer> productIdentifiers) throws SQLException, InvalidGenreDataException;
	
}
