package com.filmoteka.controller;

import java.sql.SQLException;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.filmoteka.exceptions.InvalidOrderDataException;
import com.filmoteka.exceptions.InvalidProductDataException;
import com.filmoteka.model.Order;
import com.filmoteka.model.User;
import com.filmoteka.model.dao.OrderDao;

@Controller
@RequestMapping(value="/auth")
public class OrdersController {
	private static final String dbError = "An error occured while accessing the database. Please try again later!";
	
	@RequestMapping(value = "/orders", method = RequestMethod.GET)
	public String loadCartPage(Model m, HttpSession session) throws SQLException, InvalidOrderDataException, InvalidProductDataException {
		// Get user from session
		User user = (User) session.getAttribute("USER");

		// Get user's orders
		Set<Order> orders = user.getOrdersHistory();
		
		m.addAttribute("orders", orders);

		// Return the orders JSP
		return "orders";
	}
	
	@RequestMapping(value = "/order/{id}", method = RequestMethod.GET)
	public String loadProductPage(Model m, @PathVariable("id") Integer orderId, HttpSession session ) throws Exception {
		
		try {
			//Check if the requested order belongs to the user in the first place
			User user = (User) session.getAttribute("USER");
			if(!OrderDao.getInstance().isUserOwnerOfOrder(orderId, user.getUserId())) {
				throw new Exception("You've attempted to view an order that does not belong to you!");
			}
			
			//Grab the order from the database
			Order order = OrderDao.getInstance().getOrderById(orderId);
			
			//Add the order to the model
			m.addAttribute("order", order);
		}
		catch (SQLException | InvalidProductDataException e) {
			//Error while reading the order from the database
			throw new Exception(dbError,e);
		}
		
		//Return the product view
		return "order";
	}

}
