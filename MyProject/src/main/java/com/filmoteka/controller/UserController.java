package com.filmoteka.controller;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.filmoteka.exceptions.InvalidFormDataException;
import com.filmoteka.exceptions.InvalidOrderDataException;
import com.filmoteka.exceptions.InvalidProductDataException;
import com.filmoteka.exceptions.InvalidUserDataException;
import com.filmoteka.manager.UserManager;
import com.filmoteka.model.Product;
import com.filmoteka.model.User;
import com.filmoteka.model.dao.ProductDao;


@Controller
public class UserController {
	private static final String dbError = "An error occured while accessing the database. Please try again later!";

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String showHomePage() {
		return "index";
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
			throw new Exception("An error occured while loading the movies from the database. Please try again!");
		}
		return "main";
	}

	@RequestMapping(value = "/logout", method = RequestMethod.POST)
	public String userLogout() {
		
		return "index";
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String userLogin(@RequestParam("username") String username, @RequestParam("password") String password,
			HttpServletRequest request) throws InvalidProductDataException, SQLException, InvalidUserDataException, InvalidOrderDataException {

		// Check if the credentials are valid
		User user = UserManager.getInstance().logIn(username, password);

		if (user != null) {
			// Get a new session
			HttpSession session = request.getSession();

			// Set the user in the session
			session.setAttribute("USER", user);

			// Set the IP of the request which called the server
			session.setAttribute("ip", request.getRemoteAddr());

			// Redirect to the main page service
			return "redirect:/main";
		}
		else {
			//Show an error page for invalid username or password
			String message = "Invalid Username or Password";
			throw new SQLException(message);
		}
	}
	
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String registerUser(@RequestParam("username") String username,
			@RequestParam("password") String password,
			@RequestParam("email") String email,
			@RequestParam("firstName") String firstName,
			@RequestParam("lastName") String lastName) throws Exception {
		try {
			//Check if the given user parameters are valid
			if(!UserManager.getInstance().isValidUserRegistrationData(username, password, email)) {
				throw new InvalidFormDataException("You've entered incorrect registration data in your form. Please follow the input hints.");
			}
			
			//Check if there are no users with the same username or email
			if(UserManager.getInstance().hasUserWithSameCredentials(username, email)) {
				String message= "The selected username or email is already taken by another user.";
				throw new Exception(message);
			}
			//Register user
			UserManager.getInstance().register(firstName, lastName, username, password, email);
			//Redirect to the login form again
			return "index";
		}
		catch (SQLException e) {
			throw new Exception(dbError);
		}
	}
}
