package com.filmoteka.model.dao;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

import com.filmoteka.exceptions.InvalidOrderDataException;
import com.filmoteka.exceptions.InvalidProductDataException;
import com.filmoteka.exceptions.InvalidUserDataException;
import com.filmoteka.model.Order;
import com.filmoteka.model.Product;
import com.filmoteka.model.User;

public interface IUserDao {

	User getUserByID(int id) throws Exception;
	
	void saveUser(User user) throws Exception;
	
	void deleteUser(User user) throws Exception;
	
	void updateUser(User user) throws Exception;
	
	Collection<User> getAllUsers() throws Exception;
	
	Map<Product,LocalDate> getUserProductsById(int userId) throws SQLException, InvalidProductDataException;
	
	Set<Integer> getUserFavoritesById(int userId) throws SQLException;
	
	Set<Integer> getUserWatchlistById(int userId) throws SQLException;
	
	User getUserByLoginCredentials(String username, String password) throws SQLException, InvalidUserDataException, InvalidOrderDataException, InvalidProductDataException;
	
	void addProductToFavorites(User user, Product product) throws SQLException;
	
	void removeProductFromFavorites(User user, Product product) throws SQLException;
	
	void addProductToWatchlist(User user, Product product) throws SQLException;
	
	void removeProductFromWatchlist(User user, Product product) throws SQLException;
	
	Set <Order> getUserOrdersById(int userId) throws SQLException, InvalidOrderDataException, InvalidProductDataException;
	
	void saveUserProductsInCartById(int userId, Map<Product, LocalDate> products) throws SQLException;
	
	boolean databaseHasUserWithCredentials(String username, String email) throws SQLException;
}
