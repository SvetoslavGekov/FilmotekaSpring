package com.filmoteka.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
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
import org.springframework.web.multipart.MultipartFile;

import com.filmoteka.exceptions.InvalidProductDataException;
import com.filmoteka.manager.UserManager;
import com.filmoteka.model.Movie;
import com.filmoteka.model.Product;
import com.filmoteka.model.SimpleProductFactory;
import com.filmoteka.model.TVSeries;
import com.filmoteka.model.User;
import com.filmoteka.model.dao.MovieDao;
import com.filmoteka.model.dao.ProductDao;
import com.filmoteka.model.dao.TVSeriesDao;
import com.filmoteka.model.dao.nomenclatures.GenreDao;
import com.filmoteka.model.nomenclatures.Genre;
import com.filmoteka.model.nomenclatures.ProductCategory;
import com.filmoteka.util.WebSite;

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
		
		@RequestMapping(value = "/auth/tofavorites", method = RequestMethod.POST)
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
		
		@RequestMapping(value = "/auth/towatchlist", method = RequestMethod.POST)
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
		
		@RequestMapping(value = "/adm/newProduct/{category}", method = RequestMethod.GET)
		public String createNewProduct(Model m, @PathVariable("category") Integer category) throws Exception{
			//Grab the category of the product that's being created
			ProductCategory productCategory = WebSite.getProductCategoryById(category);
			
			//Create a product based on the given category
			Product product = null;
			switch(productCategory.getId()){
				case 1:	product = new Movie(); break;
				case 2: product = new TVSeries(); break;
				default:
					throw new Exception("You've attempted to create a product from a category that does not exist. Please try again");
			}
			//Set the product's category
			product.setProductCategory(productCategory);
			
			//Create the Collection of available product genres
			ArrayList<Genre> genres = new ArrayList<>(GenreDao.getInstance().getAllGenres().values());
			
			//Add the product and the genres to the model
			m.addAttribute("product", product);
			m.addAttribute("genres", genres);
			
			//Return the product view
			return "newProduct";
		}
		
		@RequestMapping(value = "/adm/newProduct/{category}", method = RequestMethod.POST)
		public void saveProduct(@ModelAttribute Product newProduct,
				BindingResult result,
				@RequestParam("posterFile") MultipartFile posterFile,
				@RequestParam("trailerFile") MultipartFile trailerFile,
				@PathVariable("category") int category) throws Exception{
			try {
				
				//Check for binding errors
				if(result.hasErrors()) {
					throw new InvalidProductDataException("Invalid form data was entered. Please follow the input hints.");
				}
				
				//Upload poster and trailer if any
				if(!posterFile.isEmpty()) {
					String posterFilePath = FilesController.uploadPoster(posterFile, null);
					newProduct.setPoster(posterFilePath);
				}
				if(!trailerFile.isEmpty()) {
					String trailerFilePath = FilesController.uploadTrailer(trailerFile, null);
					newProduct.setTrailer(trailerFilePath);
				}
				
				//Set product category
				newProduct.setProductCategory(WebSite.getProductCategoryById(category));
				
				//Set genres
				Set<Genre> newProductGenres = new HashSet<>();
				for (Genre genre : newProduct.getGenres()) {
					newProductGenres.add(WebSite.getGenreById(Integer.valueOf(genre.getValue())));
				}
				newProduct.setGenres(newProductGenres);
				
				//Save in DAO
				switch(newProduct.getProductCategory().getId()){
					case 1:	MovieDao.getInstance().saveMovie((Movie) newProduct); break;
					case 2: TVSeriesDao.getInstance().saveTVSeries((TVSeries) newProduct); break;
				default:
					throw new Exception("You've attempted to create a product from a category that does not exist. Please try again");
				}

			}
			catch (SQLException e) {
				throw new SQLException(dbError, e);
			}
			catch(NumberFormatException e) {
				throw new Exception("Invalid form data entered. Please follow the form hints.",e);
			}
		}
		
		//Pretty important code for instantiating abstract classes in MVC forms (acts like a factory class for the controller)
		@ModelAttribute("product")
		public Product getProduct(@PathVariable("category") int category) throws Exception {
			switch(category){
			case 1:	return new Movie();
			case 2: return new TVSeries();
			default:
				throw new Exception("You've attempted to create a product from a category that does not exist. Please try again");
			}
		}
}
