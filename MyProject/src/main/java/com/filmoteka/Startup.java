package com.filmoteka;

import java.sql.SQLException;

import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.filmoteka.exceptions.InvalidGenreDataException;
import com.filmoteka.exceptions.InvalidOrderDataException;
import com.filmoteka.exceptions.InvalidProductCategoryDataException;
import com.filmoteka.exceptions.InvalidProductDataException;
import com.filmoteka.exceptions.InvalidUserDataException;
import com.filmoteka.util.WebSite;

@Component
public class Startup {
	
	@EventListener(ContextRefreshedEvent.class)
	void contextRefreshEvent() throws SQLException, InvalidGenreDataException, InvalidProductDataException, InvalidUserDataException, InvalidOrderDataException, InvalidProductCategoryDataException {
		//3. Initialize the website main method
		WebSite.main(null);
	}
}
