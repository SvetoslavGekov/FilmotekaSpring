package com.filmoteka.manager;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Map;

import com.filmoteka.exceptions.InvalidOrderDataException;
import com.filmoteka.model.Order;
import com.filmoteka.model.Product;
import com.filmoteka.model.User;
import com.filmoteka.model.dao.OrderDao;

public final class OrderManager {
	// Fields
	private static OrderManager instance;
	private OrderDao dao;

	private OrderManager() {
		// Instantiate the dao object
		this.dao = OrderDao.getInstance();
	}

	public static synchronized OrderManager getInstance() {
		if (instance == null) {
			instance = new OrderManager();
		}
		return instance;
	}

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
