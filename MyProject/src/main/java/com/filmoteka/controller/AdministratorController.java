package com.filmoteka.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FilenameUtils;
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

@Controller
public class AdministratorController {
	private static final String dbError = "An error occured while accessing the database. Please try again later!";

	
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
			@RequestParam("trailerFile") MultipartFile trailerFile) throws Exception {
		try {
			// Check for binding errors
			if (result.hasErrors()) {
				throw new InvalidProductDataException("Invalid form data was entered. Please follow the input hints.");
			}

			// Upload poster and trailer if any
			if (!posterFile.isEmpty()
					&& FilenameUtils.getExtension(posterFile.getOriginalFilename()).equalsIgnoreCase("jpg")) {
				String posterFilePath = FilesController.uploadPoster(posterFile, null);
				existingProduct.setPoster(posterFilePath);
			}
			if (!trailerFile.isEmpty()
					&& FilenameUtils.getExtension(posterFile.getOriginalFilename()).equalsIgnoreCase("avi")) {
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
			HttpServletRequest request) throws SQLException, InvalidProductDataException{

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

			// Upload poster and trailer if any
			if (!posterFile.isEmpty()
					&& FilenameUtils.getExtension(posterFile.getOriginalFilename()).equalsIgnoreCase("jpg")) {
				String posterFilePath = FilesController.uploadPoster(posterFile, null);
				newProduct.setPoster(posterFilePath);
			}
			if (!trailerFile.isEmpty()
					&& FilenameUtils.getExtension(posterFile.getOriginalFilename()).equalsIgnoreCase("avi")) {
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
		catch (NumberFormatException e) {
			throw new Exception("Invalid form data entered. Please follow the form hints.", e);
		}
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
