package com.filmoteka.util.taskExecutors;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.Callable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.filmoteka.exceptions.InvalidGenreDataException;
import com.filmoteka.exceptions.InvalidProductCategoryDataException;
import com.filmoteka.exceptions.InvalidProductDataException;
import com.filmoteka.exceptions.InvalidUserDataException;
import com.filmoteka.model.Product;
import com.filmoteka.model.User;
import com.filmoteka.model.dao.UserDao;
import com.filmoteka.util.MailManager;
@Component
public final class ExpiringProductsNotifier implements Callable<Boolean> {
	//Fields
	private static final String MESSAGE_SUBJECT = "FilmotekaBG - rented products are expiring soon";
	@Autowired
	private UserDao userDao;
	
	@Override
	public Boolean call() {
			try {
				//Collect all users that need to be notified in a map
				 Map<User, List<Product>> expiringProducts = userDao.getExpiringProducts();
				 
				//Compose message for each one
				for (Entry<User,List<Product>> e: expiringProducts.entrySet()) {
					User user = e.getKey();
					List<Product> products = e.getValue();
					String message = buildEmailMessage(user, products);
					
					//Send each on of them an email (no attachments)
					MailManager.sendEmail(user.getEmail(), MESSAGE_SUBJECT, message, null);
				}
				
			return true;
			}
			catch (SQLException | InvalidUserDataException | InvalidProductDataException
					| InvalidGenreDataException | InvalidProductCategoryDataException e) {
				e.printStackTrace();
				return false;
			}	
	}
	
	private String buildEmailMessage(User user, List<Product> products) {
		StringBuilder message = new StringBuilder(String.format("Dear %s %s, %n"
				+ "we'd like to remind you that some of the products you've rented are going to expire tomorrow (%s).%n"
				+ "Those products are:%n", user.getFirstName(), user.getLastName(), LocalDate.now().plusDays(1)));
		//Continue composing message for each product
		for (Product product : products) {
			message.append(String.format("	- %s (%s);%n", product.getName(), product.getReleaseDate().getYear()));
		}
		//End the message with a positive vibe
		message.append("With best regards, the FilmotekaBG team.");
		return message.toString();
	}
}
