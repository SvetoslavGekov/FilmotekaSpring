package com.filmoteka.controller;

import java.sql.SQLException;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.filmoteka.exceptions.InvalidProductDataException;
import com.filmoteka.model.Product;
import com.filmoteka.model.dao.ProductDao;

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
}
