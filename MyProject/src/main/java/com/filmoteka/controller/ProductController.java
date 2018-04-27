package com.filmoteka.controller;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.Map.Entry;

import javax.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.filmoteka.exceptions.InvalidProductDataException;
import com.filmoteka.manager.UserManager;
import com.filmoteka.model.Product;
import com.filmoteka.model.User;
import com.filmoteka.model.dao.MovieDao;
import com.filmoteka.model.dao.ProductDao;
import com.filmoteka.model.dao.TVSeriesDao;

@Controller
public class ProductController {
	private static final String dbError = "An error occured while accessing the database. Please try again later!";
	
	
		@RequestMapping(value = "/product/{id}", method = RequestMethod.GET)
		public String loadProductPage(Model m, @PathVariable("id") Integer productId ) throws Exception {
			//Grab the product from the database
			try {
				Product product = ProductDao.getInstance().getProductById(productId);
				
				//Add the product to the model
				m.addAttribute("product", product);
			}
			catch (SQLException | InvalidProductDataException e) {
				//Error while reading the product from the database
				throw new Exception(dbError,e);
			}
			
			//Return the product view
			return "product";
		}
		
		@RequestMapping(value = "/main", method = RequestMethod.GET)
		public String loadMainPage(Model m) throws Exception {
			//Load the product categories in the main page
			try {
				//Create a map of: category --> list of products
				Map<String,List<Product>> groupedProducts = new TreeMap<>();
				//Get top 5 of
				groupedProducts.put("On Sale", (List<Product>) ProductDao.getInstance().getProductsOnSale(new Integer(5))); //On sale
				groupedProducts.put("Most Popular", (List<Product>) ProductDao.getInstance().getMostPopularProducts(new Integer(5))); //Most Popular
				groupedProducts.put("Highest Rated", (List<Product>) ProductDao.getInstance().getHighestRatedProducts(new Integer(5))); //Highest rated
				groupedProducts.put("Cheapest", (List<Product>) ProductDao.getInstance().getCheapestProducts(new Integer(5))); //Cheapest

				//Set the products in the model
				m.addAttribute("mainPageProducts", groupedProducts);
			}
			catch (SQLException |InvalidProductDataException e) {
				throw new Exception("An error occured while loading the products from the database. Please try again!");
			}
			return "main";
		}
		
		@RequestMapping(value = "/search", method = RequestMethod.GET)
		public String loadProductsBySearching(Model m, @RequestParam("word") String substring) throws Exception {
			//Load the searched products in the main page
			try {
				//Create a map of: type --> list of products
				Map<String,List<Product>> productsBySearch = new TreeMap<>();

				productsBySearch.put("Movies", (List<Product>) MovieDao.getInstance().getMoviesBySubstring(substring)); //Movies
				productsBySearch.put("TV Series", (List<Product>) TVSeriesDao.getInstance().getTVSeriesBySubstring(substring)); //TV Series

				//Set the products in the model
				m.addAttribute("mainPageProducts", productsBySearch);
			}
			catch (SQLException |InvalidProductDataException e) {
				throw new Exception("An error occured while loading the movies from the database. Please try again!");
			}
			return "main";
		}
		
		@RequestMapping(value = "/tofavorites", method = RequestMethod.POST)
		public ResponseEntity<Boolean> addOrRemoveFavoriteProduct(HttpSession session, @RequestParam("productID") Integer productID){
			try {
				// Get user from session
				User user = (User) session.getAttribute("USER");

				// Get product from database
				Product product = ProductDao.getInstance().getProductById(productID);
				
				// Check if the productId is valid
				if (product == null) {
					// If not --> return an HTTP code for no such product (400);
					return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
				}
				//Check the result of adding or removing the product from the favorites
				boolean isAdded = UserManager.getInstance().addOrRemoveProductFromFavorites(user, product);
				
				//Return the result and an OK status
				return new ResponseEntity<Boolean>(isAdded, HttpStatus.OK);
			}
			catch (SQLException | InvalidProductDataException e) {
				//Return an entity with a status code for Internal Server Error (handling is done via JS)
				return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		
		@RequestMapping(value = "/towatchlist", method = RequestMethod.POST)
		public ResponseEntity<Boolean> addOrRemoveWatchlistProduct(HttpSession session, @RequestParam("productID") Integer productID){
			try {
				// Get user from session
				User user = (User) session.getAttribute("USER");

				// Get product from database
				Product product = ProductDao.getInstance().getProductById(productID);

				// Check if the productId is valid
				if (product == null) {
					// If not --> return an HTTP code for no such product (400);
					return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
				}
				//Check the result of adding or removing the product from the watchlist
				boolean isAdded = UserManager.getInstance().addOrRemoveProductFromWatchlist(user, product);
				
				//Return the result and an OK status
				return new ResponseEntity<Boolean>(isAdded, HttpStatus.OK);
			}
			catch (SQLException | InvalidProductDataException e1) {
				//Return an entity with a status code for Internal Server Error (handling is done via JS)
				return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		
		@RequestMapping(value = "/addtocart", method = RequestMethod.POST)
		public ResponseEntity<Boolean> addProductToCart(HttpSession session,
				@RequestParam("productID") Integer productId,
				@RequestParam("willBuy") Boolean willBuy){
			try {
				// Get user from session
				User user = (User) session.getAttribute("USER");

				// Get product from database
				Product product = ProductDao.getInstance().getProductById(productId);
				
				// Check if the productId is valid
				if (product == null) {
					// If not --> return an HTTP code for no such product (400);
					return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
				}
				
				// Add product to shopping cart
				boolean isAddedToCart = UserManager.getInstance().addProductToShoppingCart(user, product, willBuy);
				
				System.out.println("\nAdded product to cart:");
				for (Entry<Product,LocalDate> e: user.getShoppingCart().entrySet()) {
					System.out.println(String.format("%s	%s", e.getKey().getName(), e.getValue()));
				}
				
				//Return the result and an OK status
				return new ResponseEntity<Boolean>(isAddedToCart, HttpStatus.OK);
			}
			catch (SQLException | InvalidProductDataException e) {
				//Return an entity with a status code for Internal Server Error (handling is done via JS)
				return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
}
