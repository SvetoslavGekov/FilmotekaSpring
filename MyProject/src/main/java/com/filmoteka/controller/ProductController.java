package com.filmoteka.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FilenameUtils;
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
import org.springframework.web.multipart.MultipartFile;

import com.filmoteka.exceptions.InvalidGenreDataException;
import com.filmoteka.exceptions.InvalidProductDataException;
import com.filmoteka.exceptions.InvalidProductQueryInfoException;
import com.filmoteka.manager.UserManager;
import com.filmoteka.model.Movie;
import com.filmoteka.model.Product;
import com.filmoteka.model.TVSeries;
import com.filmoteka.model.User;
import com.filmoteka.model.dao.MovieDao;
import com.filmoteka.model.dao.ProductDao;
import com.filmoteka.model.dao.TVSeriesDao;
import com.filmoteka.model.dao.nomenclatures.GenreDao;
import com.filmoteka.model.nomenclatures.Genre;
import com.filmoteka.util.WebSite;
import com.filmoteka.util.productFilters.ProductQueryInfo;


@Controller
public class ProductController {
	private static final String dbError = "An error occured while accessing the database. Please try again later!";

	@RequestMapping(value = "/product/{id}", method = RequestMethod.GET)
	public String loadProductPage(Model m, @PathVariable("id") Integer productId) throws Exception {
		try {
			// Grab the product from the database
			Product product = ProductDao.getInstance().getProductById(productId);

			// Add the product to the model
			m.addAttribute("product", product);

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
			groupedProducts.put("On Sale", (List<Product>) ProductDao.getInstance().getProductsOnSale(new Integer(5))); // On
																														// sale
			groupedProducts.put("Most Popular",
					(List<Product>) ProductDao.getInstance().getMostPopularProducts(new Integer(5))); // Most Popular
			groupedProducts.put("Highest Rated",
					(List<Product>) ProductDao.getInstance().getHighestRatedProducts(new Integer(5))); // Highest rated
			groupedProducts.put("Cheapest",
					(List<Product>) ProductDao.getInstance().getCheapestProducts(new Integer(5))); // Cheapest

			// Set the products in the model
			m.addAttribute("mainPageProducts", groupedProducts);
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
			m.addAttribute("mainPageProducts", productsBySearch);
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
		catch (SQLException | InvalidProductDataException e) {
			// Return an entity with a status code for Internal Server Error (handling is
			// done via JS)
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
		catch (SQLException | InvalidProductDataException e1) {
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
				newFilterGenres.add(WebSite.getGenreById(Integer.valueOf(genre.getValue())));
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


}
