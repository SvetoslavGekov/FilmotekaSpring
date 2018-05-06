package com.filmoteka.manager;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.filmoteka.exceptions.InvalidOrderDataException;
import com.filmoteka.model.Order;
import com.filmoteka.model.Product;
import com.filmoteka.model.User;
import com.filmoteka.model.dao.OrderDao;

@Component
public final class OrderManager {
	// Fields
	@Autowired
	private OrderDao dao;

	// Methods
	public Order createNewOrder(User user, LocalDate date, Map<Product, LocalDate> shoppingCart) throws InvalidOrderDataException, SQLException {
		Order order = null;

		// Create new order
		order = new Order(user.getUserId(), date, shoppingCart);
		// Save order in DB
		dao.saveOrder(order);

		return order;
	}
}
