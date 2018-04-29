package com.filmoteka.controller;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.filmoteka.exceptions.InvalidOrderDataException;
import com.filmoteka.exceptions.InvalidProductDataException;
import com.filmoteka.manager.UserManager;
import com.filmoteka.model.Order;
import com.filmoteka.model.Product;
import com.filmoteka.model.User;
import com.filmoteka.model.dao.ProductDao;


@Controller
@RequestMapping(value ="/auth")
public class CartController {
	private static final String dbError = "An error occured while accessing the database. Please try again later!";

	@RequestMapping(value = "/cart", method = RequestMethod.GET)
	public String loadCartPage(Model m, HttpSession session) {
		// Get user from session
		User user = (User) session.getAttribute("USER");

		// Get user's cart
		Map<Product, LocalDate> cart = new HashMap<>(user.getShoppingCart());
		
		// Add the cart into the view
		m.addAttribute("cart", cart);

		// Return the cart JSP
		return "cart";
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
	
	
	@RequestMapping(value = "/removefromcart", method = RequestMethod.GET)
	public String removeProductFromCart(HttpSession session, @RequestParam("productID") Integer productID) throws Exception {
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
			UserManager.getInstance().removeProductFromShoppingCart(user, product);

			System.out.println("\nRemoved product from cart:");
			for (Entry<Product, LocalDate> e : user.getShoppingCart().entrySet()) {
				System.out.println(String.format("%s	%s", e.getKey().getName(), e.getValue()));
			}

			// Redirect to the updated cart JSP
			return "redirect:cart";
		}
		catch (SQLException | InvalidProductDataException e) {
			//Throw a DB error
			throw new Exception(dbError, e);
		}
	}
	
	@RequestMapping(value = "/buycart", method = RequestMethod.GET)
	public String buyProductsFromCart(HttpSession session) throws Exception {
		try {
			// Get user from session
			User user = (User) session.getAttribute("USER");
			
			//Attempt to buy products in cart
			
			UserManager.getInstance().buyProductsInCart(user);
			
			for (Order order : user.getOrdersHistory()) {
				System.out.printf("Id:%d	User:%d	Date:%s	Price:%.2f%n",order.getId(), order.getUserId(),
						order.getDate(), order.getTotalCost());
			}
			
			//Redirect to the main page 
			return "redirect:/main";
		}
		catch (SQLException | InvalidOrderDataException e) {
			//Tell user that an error occured while creating his order
			throw new Exception(dbError,e);
		}
	}
}
