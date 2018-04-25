package com.filmoteka.util;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import com.filmoteka.exceptions.InvalidGenreDataException;
import com.filmoteka.exceptions.InvalidOrderDataException;
import com.filmoteka.exceptions.InvalidProductCategoryDataException;
import com.filmoteka.exceptions.InvalidProductDataException;
import com.filmoteka.exceptions.InvalidUserDataException;
import com.filmoteka.manager.MovieManager;
import com.filmoteka.manager.TVSeriesManager;
import com.filmoteka.model.Product;
import com.filmoteka.model.TVSeries;
import com.filmoteka.model.dao.ProductDao;
import com.filmoteka.model.dao.nomenclatures.GenreDao;
import com.filmoteka.model.dao.nomenclatures.ProductCategoryDao;
import com.filmoteka.model.nomenclatures.Genre;
import com.filmoteka.model.nomenclatures.ProductCategory;
import com.filmoteka.util.taskExecutors.CustomTaskExecutor;
import com.filmoteka.util.taskExecutors.ExpiredProductsDeleter;
import com.filmoteka.util.taskExecutors.ExpiringProductsNotifier;

public final class WebSite {
	// Fields
	private static final LocalTime TASKS_STARTING_TIME = LocalTime.now().withHour(9).withMinute(23).withSecond(30);
	private static final Map<Integer,Genre> GENRES = new TreeMap<>();
	private static final Map<Integer, ProductCategory> PRODUCT_CATEGORIES = new TreeMap<>();
	private static final List<CustomTaskExecutor> TASKS = new ArrayList<>();
	
	// Constructors --> never instantiated
	private WebSite() {

	}

	// Methods
	public static Genre getGenreById(int id) {
		return GENRES.get(id);
	}
	
	public static void addGenre(Genre g) {
		if (g != null) {
			GENRES.put(g.getId(),g);
		}
	}
	
	public static Set<Genre> getGenresFromString(String genresString) throws InvalidGenreDataException {
		Set<Genre> genres = new HashSet<>();
		
		//Trim the string and check if it's empty
		genresString = genresString.trim();
		if(genresString.isEmpty()) {
			return genres;
		}
		
		//Split the resulting string
		String[] split = genresString.split(",");
		
		//Iterate over the resulting array
		try {
			for (String string : split) {
				if(!string.isEmpty()) {
					//Attempt to get the genre's ID
					int genreId = Integer.parseInt(string);
					//Collect a genre from the genres collection
					genres.add(getGenreById(genreId));
				}
			}
		}
		catch (NumberFormatException e) {
			//If something else is specified (harmful strings for example) --> throw an exception
			throw new InvalidGenreDataException("Invalid genres have been specified!", e);
		}
		
		return genres;
	}
	
	public static Map<Integer,Genre> getAllGenres() {
		return Collections.unmodifiableMap(GENRES);
	}
	
	public static ProductCategory getProductCategoryById(int id) {
		return PRODUCT_CATEGORIES.get(id);
	}
	
	public static void addProductCategory(ProductCategory pc) {
		if (pc != null) {
			PRODUCT_CATEGORIES.put(pc.getId(),pc);
		}
	}

	public static Collection<CustomTaskExecutor> getAllTasks(){
		return Collections.unmodifiableCollection(TASKS);
	}

	public static void main(String[] args) throws SQLException, InvalidGenreDataException, InvalidProductDataException,
	InvalidUserDataException, InvalidOrderDataException, InvalidProductCategoryDataException {
		//Load all genres
		GENRES.putAll(GenreDao.getInstance().getAllGenres());
		
		//Load all product categories
		PRODUCT_CATEGORIES.putAll(ProductCategoryDao.getInstance().getAllProductCategories());
		
		//Create all utility tasks
		TASKS.add(new CustomTaskExecutor(ExpiringProductsNotifier.getInstance())); //Expiring products notifier
		TASKS.add(new CustomTaskExecutor(ExpiredProductsDeleter.getInstance()));// Expired products deleter
		
		//Start all task at the same time
		for (CustomTaskExecutor taskExecutor : TASKS) {
			taskExecutor.startExecutionAt(TASKS_STARTING_TIME.getHour(), TASKS_STARTING_TIME.getMinute(), TASKS_STARTING_TIME.getSecond());
		}
		
	}
}
