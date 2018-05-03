package com.filmoteka.controller;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.filmoteka.exceptions.InvalidProductDataException;
import com.filmoteka.manager.UserManager;
import com.filmoteka.model.Product;
import com.filmoteka.model.User;
import com.filmoteka.model.dao.ProductDao;
import com.filmoteka.model.dao.UserDao;

@Controller
public class CollectionsController {
	
	private static final String dbError = "An error occured while accessing the database. Please try again later!";
	
	@RequestMapping(value = "/auth/favourites", method = RequestMethod.GET)
	public String loadFavoriteProducts(Model m, HttpSession session) throws Exception {
		try {
			// Get the user from the session
			User user = (User) session.getAttribute("USER");
			// Get user's favorites
			List<Integer> identifiers = new ArrayList<Integer>(user.getFavourites());
			// Create a map of: type --> collection of products
			Collection<Product> myFavourites = new ArrayList<>();
			
			myFavourites.addAll(ProductDao.getInstance().getProductsByIdentifiers(identifiers));
			
			m.addAttribute("collection","Favorites");
			m.addAttribute("products", myFavourites);
		}
		catch (SQLException | InvalidProductDataException e) {
			throw new Exception("An error occured while loading the movies from the database. Please try again!");
		}
		return "productsList";
	}
	
	@RequestMapping(value = "/auth/removefromfavs", method = RequestMethod.GET)
	public String removeProductFromFavorites(HttpSession session, @RequestParam("productID") Integer productID) throws Exception {
		try {
			// Get user from session and product from DB
			User user = (User) session.getAttribute("USER");
			Product product = ProductDao.getInstance().getProductById(productID);

			// Check if the productId is valid
			if (product == null) {
				// If not --> return an error page saying there is no such product
				throw new Exception("Sorry, but you've made a request for a product that does not exist.");
			}
			
			// Remove product from shopping cart
			UserManager.getInstance().addOrRemoveProductFromFavorites(user, product);

			System.out.println("\nRemoved product from favorites:");

			// Redirect to the updated productList JSP
			return "redirect:favourites";
		}
		catch (SQLException | InvalidProductDataException e) {
			//Throw a DB error
			throw new Exception(dbError, e);
		}
	}
	
	@RequestMapping(value = "/auth/myproducts", method = RequestMethod.GET)
	public String loadBoughtProducts(Model m, HttpSession session) throws Exception {
		try {
			// Get the user from the session
			User user = (User) session.getAttribute("USER");

			// Get user's products
			Map<Product, LocalDate> myProducts = new TreeMap<>(UserDao.getInstance().getUserProductsById(user.getUserId()));
		
			m.addAttribute("collection", "Products");
			m.addAttribute("cart", myProducts);
		}
		catch (SQLException | InvalidProductDataException e) {
			throw new Exception("An error occured while loading the movies from the database. Please try again!");
		}

		return "cart";
	}
	
	@RequestMapping(value = "/auth/watchlist", method = RequestMethod.GET)
	public String loadWatchListProducts(Model m, HttpSession session) throws Exception {
		try {
			// Get the user from the session
			User user = (User) session.getAttribute("USER");
			// Get user's watchList
			List<Integer> identifiers = new ArrayList<Integer>(user.getWatchList());
			// Create a map of: type --> collection of products
			Collection<Product> myWatchList = new HashSet<>();
			
			myWatchList.addAll(ProductDao.getInstance().getProductsByIdentifiers(identifiers));
			
			m.addAttribute("collection","WatchList");
			m.addAttribute("products", myWatchList);
		}
		catch (SQLException | InvalidProductDataException e) {
			throw new Exception("An error occured while loading the movies from the database. Please try again!");
		}
		return "productsList";
	}
	
	@RequestMapping(value = "/auth/removefromWatchList", method = RequestMethod.GET)
	public String removeProductFromWatchList(HttpSession session, @RequestParam("productID") Integer productID) throws Exception {
		try {
			// Get user from session and product from DB
			User user = (User) session.getAttribute("USER");
			Product product = ProductDao.getInstance().getProductById(productID);

			// Check if the productId is valid
			if (product == null) {
				// If not --> return an error page saying there is no such product
				throw new Exception("Sorry, but you've made a request for a product that does not exist.");
			}
			
			// Remove product from watchList
			UserManager.getInstance().addOrRemoveProductFromWatchlist(user, product);

			System.out.println("\nRemoved product from watchlist:");

			// Redirect to the updated productList JSP
			return "redirect:watchlist";
		}
		catch (SQLException | InvalidProductDataException e) {
			//Throw a DB error
			throw new Exception(dbError, e);
		}
	}
}
