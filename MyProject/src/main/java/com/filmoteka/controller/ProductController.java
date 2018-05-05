package com.filmoteka.controller;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.filmoteka.exceptions.InvalidGenreDataException;
import com.filmoteka.exceptions.InvalidProductCategoryDataException;
import com.filmoteka.exceptions.InvalidProductDataException;
import com.filmoteka.exceptions.InvalidProductQueryInfoException;
import com.filmoteka.exceptions.InvalidReviewDataException;
import com.filmoteka.manager.UserManager;
import com.filmoteka.model.Product;
import com.filmoteka.model.Review;
import com.filmoteka.model.User;
import com.filmoteka.model.dao.MovieDao;
import com.filmoteka.model.dao.ProductDao;
import com.filmoteka.model.dao.ReviewDao;
import com.filmoteka.model.dao.TVSeriesDao;
import com.filmoteka.model.dao.nomenclatures.GenreDao;
import com.filmoteka.model.nomenclatures.Genre;
import com.filmoteka.util.ProductQueryInfo;


@Controller
public class ProductController {
	private static final String dbError = "An error occured while accessing the database. Please try again later!";
	
	private static final double MAX_RATING = 10d;
	private static final double MIN_RATING = 1d;
	
	private static final int MAX_REVIEW_CHARS = 480;
	private static final int MIN_REVIEW_CHARS = 3;
	
	private static final Integer TOP_ITEMS_COUNT = new Integer(5);
	
	
	@RequestMapping(value = "/product/{id}", method = RequestMethod.GET)
	public String loadProductPage(Model m, @PathVariable("id") Integer productId,
			HttpSession session) throws Exception {
		try {
			// Grab the product from the database
			Product product = ProductDao.getInstance().getProductById(productId);

			//Check if the product exists
			if(product == null) {
				throw new Exception("You've attempted to view a product that does not exist!");
			}
			
			// Add the product to the model
			m.addAttribute("product", product);
			
			//Get reviews of the product
			List<Review> reviews = ReviewDao.getInstance().getReviewsByProductId(product.getId());
			
			
			boolean isInFavorites = false;
			boolean isInWatchlist = false;
			double userRating = 0d;
			
			//Check if there is a user logged in the session
			User user = (User) session.getAttribute("USER");
			if(user != null) {
				//Look if the product is favored, added to the watchlist or rated by the user
				isInFavorites = user.getFavourites().contains(productId);
				isInWatchlist = user.getWatchList().contains(productId);
				userRating = product.getRaters().containsKey(user.getUserId()) ? product.getRaters().get(user.getUserId()) : 0d;;
			}
			
			//Add product reviews to the model
			m.addAttribute("reviews", reviews);
			m.addAttribute("isInFavorites", isInFavorites);
			m.addAttribute("isInWatchlist", isInWatchlist);
			m.addAttribute("userRating", userRating);

			// Return the product view
			return "product";
		}
		catch (SQLException | InvalidProductDataException e) {
			// Error while reading the product from the database
			throw new Exception(dbError, e);
		}
	}

	@RequestMapping(value = "/main", method = RequestMethod.GET)
	public String loadMainPage(Model m) throws Exception {
		// Load the product categories in the main page
		try {
			// Create a map of: category --> list of products
			Map<String, List<Product>> groupedProducts = new TreeMap<>();
			// Get top 5 of
			groupedProducts.put("On Sale", (List<Product>) ProductDao.getInstance().getProductsOnSale(new Integer(TOP_ITEMS_COUNT))); // On sale
			groupedProducts.put("Most Popular",
					(List<Product>) ProductDao.getInstance().getMostPopularProducts(new Integer(TOP_ITEMS_COUNT))); // Most Popular
			groupedProducts.put("Highest Rated",
					(List<Product>) ProductDao.getInstance().getHighestRatedProducts(new Integer(TOP_ITEMS_COUNT))); // Highest rated
			groupedProducts.put("Cheapest",
					(List<Product>) ProductDao.getInstance().getCheapestProducts(new Integer(TOP_ITEMS_COUNT))); // Cheapest

			// Set the products in the model
			m.addAttribute("products", groupedProducts);
		}
		catch (SQLException | InvalidProductDataException e) {
			throw new Exception("An error occured while loading the products from the database. Please try again!");
		}
		return "main";
	}

	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public String loadProductsBySearching(Model m, @RequestParam("word") String substring) throws Exception {
		// Load the searched products in the main page
		try {
			// Create a map of: type --> list of products
			Map<String, List<Product>> productsBySearch = new TreeMap<>();

			productsBySearch.put("Movies", (List<Product>) MovieDao.getInstance().getMoviesBySubstring(substring)); // Movies
			productsBySearch.put("TV Series",
					(List<Product>) TVSeriesDao.getInstance().getTVSeriesBySubstring(substring)); // TV Series

			// Set the products in the model
			m.addAttribute("products", productsBySearch);
		}
		catch (SQLException | InvalidProductDataException e) {
			throw new Exception("An error occured while loading the movies from the database. Please try again!");
		}
		return "main";
	}

	@RequestMapping(value = "/auth/tofavorites", method = RequestMethod.POST)
	public ResponseEntity<Boolean> addOrRemoveFavoriteProduct(HttpSession session,
			@RequestParam("productID") Integer productID) {
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
			// Check the result of adding or removing the product from the favorites
			boolean isAdded = UserManager.getInstance().addOrRemoveProductFromFavorites(user, product);

			// Return the result and an OK status
			return new ResponseEntity<Boolean>(isAdded, HttpStatus.OK);
		}
		catch (SQLException | InvalidProductDataException | InvalidGenreDataException | InvalidProductCategoryDataException e) {
			// Return an entity with a status code for Internal Server Error (handling is
			// done via JS)
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
		
		@RequestMapping(value = "/auth/rateproduct", method = RequestMethod.POST)
		public ResponseEntity<Boolean> addProductToCart(HttpSession session,
				@RequestParam("productID") Integer productId,
				@RequestParam("rating") Double rating){
			try {
				// Get user from session
				User user = (User) session.getAttribute("USER");

				// Get product from database
				Product product = ProductDao.getInstance().getProductById(productId);
				
				// Check if the productId and rating are valid
				if (product == null || rating > MAX_RATING || rating < MIN_RATING) {
					// If not --> return an HTTP code for no such product (400);
					return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
				}
				
				// Rate product
				ProductDao.getInstance().rateProduct(user, product, rating);
				
				System.out.println("\nRated product from "+user.getFirstName()+" with rate = "+rating);
				
				
				//Return an OK status
				return new ResponseEntity<Boolean>(HttpStatus.OK);
			}
			catch (SQLException | InvalidProductDataException | InvalidGenreDataException | InvalidProductCategoryDataException e) {
				//Return an entity with a status code for Internal Server Error (handling is done via JS)
				return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		
		@RequestMapping(value = "/auth/towatchlist", method = RequestMethod.POST)
		public ResponseEntity<Boolean> addOrRemoveWatchlistProduct(HttpSession session,
				@RequestParam("productID") Integer productID) {
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
				// Check the result of adding or removing the product from the watchlist
				boolean isAdded = UserManager.getInstance().addOrRemoveProductFromWatchlist(user, product);
	
				// Return the result and an OK status
				return new ResponseEntity<Boolean>(isAdded, HttpStatus.OK);
			}
			catch (SQLException | InvalidProductDataException | InvalidGenreDataException | InvalidProductCategoryDataException e1) {
				// Return an entity with a status code for Internal Server Error (handling is
				// done via JS)
				return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	 		}
		}

	@RequestMapping(value = "/browse", method = RequestMethod.GET)
	public String loadProductBrowsingPage(Model m) throws Exception {
		try {
			// Grab the default products filter from the database (1 for the default stats and 1 for the input)
			ProductQueryInfo filter = ProductDao.getInstance().getFilterInfo();
			ProductQueryInfo newFilter = ProductDao.getInstance().getFilterInfo();

			// Grab the products from the database
			List<Product> products = new ArrayList<>(ProductDao.getInstance().getAllProducts());

			// Create the Collection of available product genres
			ArrayList<Genre> genres = new ArrayList<>(GenreDao.getInstance().getAllGenres().values());

			// Add the filter and products to the model
			m.addAttribute("genres", genres);
			m.addAttribute("filter", filter);
			m.addAttribute("newFilter", newFilter);
			m.addAttribute("products", products);

			// Return the browsing view
			return "browseProducts";
		}
		catch (SQLException | InvalidProductQueryInfoException e) {
			// Error while accessing the database
			throw new Exception(dbError, e);
		}		
		catch (NumberFormatException e) {
			throw new Exception("Invalid form data entered. Please follow the form hints.", e);
		}
	}

	@RequestMapping(value = "/browse", method = RequestMethod.POST)
	public String filterBrowsingPage(@ModelAttribute("newFilter") ProductQueryInfo newFilter,
			BindingResult result,
			Model m) throws Exception {
		try {
			// Check for any binding errors
			if (result.hasErrors()) {
				throw new InvalidProductQueryInfoException(
						"Invalid filter data was entered. Please follow the input hints.");
			}

			// Set genres
			List<Genre> newFilterGenres = new ArrayList<>();
			for (Genre genre : newFilter.getGenres()) {
				newFilterGenres.add(GenreDao.getInstance().getGenreById(Integer.valueOf(genre.getValue())));
			}
			newFilter.setGenres(newFilterGenres);

			System.out.println(newFilter);

			//Get the static information again (genres and default filter)
			ArrayList<Genre> genres = new ArrayList<>(GenreDao.getInstance().getAllGenres().values());
			ProductQueryInfo filter = ProductDao.getInstance().getFilterInfo();
			List<Product> products = ProductDao.getInstance().getFilteredProducts(newFilter);
			
			// Add the filter and products to the model
			m.addAttribute("genres", genres);
			m.addAttribute("filter", filter);
			m.addAttribute("newFilter", newFilter);
			m.addAttribute("products", products);

			// Return the browsing view
			return "browseProducts";
		}
		catch (SQLException | InvalidGenreDataException | InvalidProductDataException e) {
			// Error while accessing the database
			throw new Exception(dbError, e);
		}
	}

	@RequestMapping(value = "/auth/addreview", method = RequestMethod.POST)
	public ResponseEntity<Boolean> addReviewToProduct(HttpSession session,
			@RequestParam("productID") Integer productId,
			@RequestParam("reviewContent") String reviewContent){
		try {
			// Get user from session
			User user = (User) session.getAttribute("USER");

			// Get product from database
			Product product = ProductDao.getInstance().getProductById(productId);
			
			// Check if the productId and reviewContent length are valid
			if (product == null || reviewContent.length() > MAX_REVIEW_CHARS || reviewContent.length() < MIN_REVIEW_CHARS) {
				// If not --> return an HTTP code for no such product (400);
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}
			
			Review review = new Review(productId, user.getUsername(), reviewContent, LocalDateTime.now());
			// Add review to product
			ProductDao.getInstance().addReview(review, user.getUserId());
			
			System.out.println("\n"+"Added review content:\n"+reviewContent+"\nFor product with id = "+productId+"\nFROM: "+user.getFirstName()+"\n\n");
			
			
			//Return an OK status
			return new ResponseEntity<Boolean>(HttpStatus.OK);
		}
		catch (SQLException | InvalidProductDataException | InvalidGenreDataException | InvalidProductCategoryDataException | InvalidReviewDataException e) {
			//Return an entity with a status code for Internal Server Error (handling is done via JS)
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
