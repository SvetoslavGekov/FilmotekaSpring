package com.filmoteka.controller;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

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
import com.filmoteka.exceptions.InvalidProductCategoryDataException;
import com.filmoteka.exceptions.InvalidProductDataException;
import com.filmoteka.model.Movie;
import com.filmoteka.model.MovieAPI;
import com.filmoteka.model.Product;
import com.filmoteka.model.SimpleProductFactory;
import com.filmoteka.model.TVSeries;
import com.filmoteka.model.dao.MovieDao;
import com.filmoteka.model.dao.ProductDao;
import com.filmoteka.model.dao.TVSeriesDao;
import com.filmoteka.model.dao.nomenclatures.GenreDao;
import com.filmoteka.model.dao.nomenclatures.ProductCategoryDao;
import com.filmoteka.model.nomenclatures.Genre;
import com.filmoteka.model.nomenclatures.ProductCategory;
import com.filmoteka.util.Supp;
import com.google.gson.JsonParser;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

@Controller
public class AdministratorController {
	private static final String OMDB_API_KEY = "8d55e2c0";
	private static final String YOUTUBE_TRAILER_URL = "https://www.youtube.com/embed/";
	private static final String dbError = "An error occured while accessing the database. Please try again later!";

	@RequestMapping(value = "adm/omdbAPI", method = RequestMethod.GET)
	public String showProductAPIPage() {
		return "newProductApi";
	}
	
	@RequestMapping(value = "/adm/editProduct/{productID}", method = RequestMethod.GET)
	public String showFormForExistingProduct(Model m, @PathVariable("productID") Integer productID) throws Exception {
		// Grab the product that's being edited
		try {
			Product product = ProductDao.getInstance().getProductById(productID);
			//Check if product exists
			if(product == null) {
				throw new Exception("You've attempted to edit a product that does not exists!");
			}
			
			// Create the Collection of available product genres
			ArrayList<Genre> genres = new ArrayList<>(GenreDao.getInstance().getAllGenres().values());

			// Add the product and it's genres to the model
			m.addAttribute("product", product);
			m.addAttribute("genres", genres);
			m.addAttribute("mode",false); //Mode for editing items
			
			// Return the product view
			return "newProduct";
		}
		catch (SQLException | InvalidProductDataException e) {
			throw new Exception(dbError, e);
		}
	}

	@RequestMapping(value = "/adm/editProduct/{productID}", method = RequestMethod.POST)
	public synchronized String editProduct(@ModelAttribute Product existingProduct, BindingResult result,
			@RequestParam("posterFile") MultipartFile posterFile,
			@RequestParam("trailerFile") MultipartFile trailerFile,
			@RequestParam("trailerURL") String trailerURL) throws Exception {
		try {
			// Check for binding errors
			if (result.hasErrors()) {
				throw new InvalidProductDataException("Invalid form data was entered. Please follow the input hints.");
			}

			// Upload poster and trailer if any
			if (!posterFile.isEmpty()) {
				String posterFilePath = FilesController.uploadPoster(posterFile, null);
				existingProduct.setPoster(posterFilePath);
			}
			// Set trailerURL if is valid 
			if(Supp.isNotNullOrEmpty(trailerURL) && trailerURL.startsWith(YOUTUBE_TRAILER_URL, 0)) {
				existingProduct.setTrailer(trailerURL);
			}
			else if (!trailerFile.isEmpty()) {
				String trailerFilePath = FilesController.uploadTrailer(trailerFile, null);
				existingProduct.setTrailer(trailerFilePath);
			}

			// Set genres
			Set<Genre> newProductGenres = new HashSet<>();
			for (Genre genre : existingProduct.getGenres()) {
				newProductGenres.add(GenreDao.getInstance().getGenreById(Integer.valueOf(genre.getValue())));
			}
			existingProduct.setGenres(newProductGenres);

			// Update in DAO
			switch (existingProduct.getProductCategory().getId()) {
			case 1:
				MovieDao.getInstance().updateMovie((Movie) existingProduct);
				break;
			case 2:
				TVSeriesDao.getInstance().updateTVSeries((TVSeries) existingProduct);
				break;
			default:
				throw new Exception(
						"You've attempted to update a product from a category that does not exist. Please try again");
			}
			
			//Go back to the page
			return "redirect:/adm/editProduct/"+existingProduct.getId();
		}
		catch (SQLException | InvalidProductDataException | InvalidGenreDataException e) {
			throw new Exception(dbError, e);
		}
	}
	
	@RequestMapping(value = "/adm/newProduct/{category}", method = RequestMethod.GET)
	public String showFormForNewProduct(Model m,
			@PathVariable("category") Integer category,
			HttpServletRequest request) throws Exception{

		try {		
			//Set the category in the request
			request.setAttribute("category", category);
			
			// Grab the category of the product that's being created
			ProductCategory productCategory = ProductCategoryDao.getInstance().getProductCategoryById(category);
			
			// Create a product based on the given category
			Product product = SimpleProductFactory.createProductStub(category);
			
			// Set the product's category
			product.setProductCategory(productCategory);

			// Create the Collection of available product genres
			ArrayList<Genre> genres = new ArrayList<>(GenreDao.getInstance().getAllGenres().values());

			// Add the product and the genres to the model
			m.addAttribute("product", product);
			m.addAttribute("genres", genres);
			m.addAttribute("mode",true); //Mode for creating new items

			// Return the product view
			return "newProduct";
		}
		catch (SQLException | InvalidProductCategoryDataException | InvalidGenreDataException e) {
			//Error when readin data from the DB
			throw new SQLException(dbError,e);
		}		
	}
	
	@RequestMapping(value = "/adm/newProduct/{category}", method = RequestMethod.POST)
	public synchronized String saveProduct(@ModelAttribute Product newProduct, BindingResult result,
			@RequestParam("posterFile") MultipartFile posterFile,
			@RequestParam("trailerFile") MultipartFile trailerFile,
			@RequestParam("category") Integer category)
			throws Exception {
		try {
			// Check for binding errors
			if (result.hasErrors()) {
				throw new InvalidProductDataException("Invalid form data was entered. Please follow the input hints.");
			}
			
			System.out.println(newProduct.getPoster());
			// Upload poster and trailer if any
			if (!posterFile.isEmpty()) {
				String posterFilePath = FilesController.uploadPoster(posterFile, null);
				newProduct.setPoster(posterFilePath);
			}
			else if(newProduct.getPoster() != null && newProduct.getPoster().contains("https:")){
				String posterFilePath = FilesController.uploadPosterFromURL(newProduct.getPoster(), newProduct.getName());
				newProduct.setPoster(posterFilePath);
			}
			
			if (!trailerFile.isEmpty()) {
				String trailerFilePath = FilesController.uploadTrailer(trailerFile, null);
				newProduct.setTrailer(trailerFilePath);
			}

			// Set product category
			ProductCategory productcategory = ProductCategoryDao.getInstance().getProductCategoryById(category);
			newProduct.setProductCategory(productcategory);

			// Set genres
			Set<Genre> newProductGenres = new HashSet<>();
			for (Genre genre : newProduct.getGenres()) {
				newProductGenres.add(GenreDao.getInstance().getGenreById(Integer.valueOf(genre.getValue())));
			}
			newProduct.setGenres(newProductGenres);

			// Save in DAO
			switch (newProduct.getProductCategory().getId()) {
			case 1:
				MovieDao.getInstance().saveMovie((Movie) newProduct);
				break;
			case 2:
				TVSeriesDao.getInstance().saveTVSeries((TVSeries) newProduct);
				break;
			default:
				throw new Exception(
						"You've attempted to create a product from a category that does not exist. Please try again");
			}
			
			//Go back to the page
			return "redirect:/adm/editProduct/"+newProduct.getId();
		}
		catch (SQLException | InvalidGenreDataException | InvalidProductCategoryDataException e ) {
			throw new SQLException(dbError, e);
		}
	}
	
	@RequestMapping(value = "adm/omdbAPI", method = RequestMethod.POST)
	public String loadAPIResults(@RequestParam("query") String query, Model model) throws Exception {
		//Check if query is not empty or null
		if(Supp.isNotNullOrEmpty(query)) {
			query = query.trim().replace(" ", "%20");
			//Create a new search URL to the OMDb API and a HTTPConnection
			URL url = new URL("http://www.omdbapi.com/?s="+query+"&type=movie&apikey="+OMDB_API_KEY);
			
			//Create and open the connection to the API
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			
			//Get the input stream from the response 
			InputStream responseBodyStream;
			if(connection.getResponseCode() == 200) {
				responseBodyStream = connection.getInputStream();
			}
			else {
				throw new Exception("An error occured while accessing the OMDB API.");
			}
			
			//Read the data in a String buffer
			StringBuffer sb = new StringBuffer();
			int b = responseBodyStream.read();
			while(b != -1) {
				sb.append((char) b);
				b = responseBodyStream.read();
			}
			
			//Put the data from the buffer into a string variable
			String jsonString = sb.toString();
			
			//Create a JSON object from the string
			JsonParser parser = new JsonParser();
			JsonObject jsonObj = parser.parse(jsonString).getAsJsonObject();
			boolean statusIsTrue = jsonObj.get("Response").getAsBoolean();
			
			//Check if the status is true
			if(statusIsTrue) {
				//Create a list of API Movies
				List<MovieAPI> apiMovies = new ArrayList<>();
				
				JsonArray jsonMovies = jsonObj.get("Search").getAsJsonArray();
				for(int i = 0; i < jsonMovies.size(); i++) {
					JsonObject movie = jsonMovies.get(i).getAsJsonObject();
					String title = movie.get("Title").getAsString();
					String year = movie.get("Year").getAsString();
					String imdbID = movie.get("imdbID").getAsString();
					String poster = movie.get("Poster").getAsString();
					String type = movie.get("Type").getAsString();
					
					apiMovies.add(new MovieAPI(title, year, imdbID, poster, type));
				}
				
				model.addAttribute("apiMovies", apiMovies);
			}
			else {
				throw new Exception("An error occured while accessing the API. Please try again later.");
			}
		}
		else {
			throw new Exception("You've entered invalid search parameters. Please follow the input hints.");
		}
		
		return "newProductApi";
	}
	
	@RequestMapping(value = "/adm/newMovie/{imdbID}", method = RequestMethod.GET)
	public String loadNewAPIMovieForm(Model model,
			@PathVariable("imdbID") String imdbID,
			HttpServletRequest request) throws Exception {
		try {
			//Set the category in the request (for movie)
			request.setAttribute("category", 1);
			
			//Create a new search URL to the OMDb API and a HTTPConnection
			URL url = new URL("http://www.omdbapi.com/?i="+imdbID+"&apikey="+ OMDB_API_KEY);
			
			//Create and open the connection to the API
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			
			//Get the input stream from the response 
			InputStream responseBodyStream;
			if(connection.getResponseCode() == 200) {
				responseBodyStream = connection.getInputStream();
			}
			else {
				throw new Exception("An error occured while accessing the OMDB API.");
			}
			
			//Read the data in a String buffer
			StringBuffer sb = new StringBuffer();
			int b = responseBodyStream.read();
			while(b != -1) {
				sb.append((char) b);
				b = responseBodyStream.read();
			}
			
			//Put the data from the buffer into a string variable
			String jsonString = sb.toString();
			
			//Create a JSON object from the string
			JsonParser parser = new JsonParser();
			JsonObject jsonObj = parser.parse(jsonString).getAsJsonObject();
			boolean statusIsTrue = jsonObj.get("Response").getAsBoolean();
			
			
			if(statusIsTrue) {
				//Grab the data from the JSON
				String name = jsonObj.get("Title").getAsString();
				LocalDate releaseDate = LocalDate.now().withYear(Integer.parseInt(jsonObj.get("Year").getAsString()));
				String pgRating = jsonObj.get("Rated").getAsString();
				String director = jsonObj.get("Director").getAsString();
				String actors = jsonObj.get("Actors").getAsString();
				String writers = jsonObj.get("Writer").getAsString();
				String description = jsonObj.get("Plot").getAsString();
				String poster = jsonObj.get("Poster").getAsString();
				String durationString = jsonObj.get("Runtime").getAsString().replace(" min", "").trim();
				int duration = Integer.parseInt(durationString);
				
				//Create the movie instance and set it's attributes
				Movie movie = new Movie();
				movie.setName(name);
				movie.setProductCategory(ProductCategoryDao.getInstance().getProductCategoryById(1));//For movie
				movie.setReleaseDate(releaseDate);
				movie.setDuration(duration);
				movie.setPgRating(pgRating);
				movie.setDirector(director);
				movie.setActors(actors);
				movie.setWriters(writers);
				movie.setDescription(description);
				movie.setPoster(poster);
				
				// Create the Collection of available product genres
				ArrayList<Genre> genres = new ArrayList<>(GenreDao.getInstance().getAllGenres().values());

				// Add the product and the genres to the model
				model.addAttribute("product", movie);
				model.addAttribute("genres", genres);
				model.addAttribute("mode",true); //Mode for creating new items
			}
			else {
				throw new Exception();
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new Exception("An error occured while creating the movie from the API.",e);
		}
		
		return "newProduct";
	}
	
	@RequestMapping(value = "/adm/newMovie/{imdbID}", method = RequestMethod.POST)
	public String createMovieWithApi(@ModelAttribute Product newProduct, BindingResult result,
			@RequestParam("posterFile") MultipartFile posterFile,
			@RequestParam("trailerFile") MultipartFile trailerFile,
			@RequestParam("category") Integer category) throws Exception{
		return saveProduct(newProduct, result, posterFile, trailerFile, category);
	}
	
	// Pretty important code for instantiating abstract classes in MVC forms (acts
	// like a factory class for the controller)
	@ModelAttribute("product")
	public Product getProduct(@RequestParam(value = "category", required = false) Integer category,
			@RequestParam(value = "productID", required = false) Integer productID,
			HttpServletRequest req) throws Exception {
		Product product = null;
		if (productID != null && productID != 0) {
			product = ProductDao.getInstance().getProductById(productID);
			if(product == null) {
				throw new Exception("You've attempted to edit a product that does not exist!");
			}
			return product;
		}
		if (category == null) {
			return null;
		}
		product = SimpleProductFactory.createProductStub(category);
		return product;
	}
	

}
