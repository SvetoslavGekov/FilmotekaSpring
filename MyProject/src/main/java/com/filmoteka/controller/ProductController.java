package com.filmoteka.controller;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.filmoteka.exceptions.InvalidProductDataException;
import com.filmoteka.model.Product;
import com.filmoteka.model.dao.MovieDao;
import com.filmoteka.model.dao.ProductDao;
import com.filmoteka.model.dao.TVSeriesDao;

@Controller
public class ProductController {

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
			throw new Exception("An error occured while loading the movies from the database. Please try again!");
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
}
