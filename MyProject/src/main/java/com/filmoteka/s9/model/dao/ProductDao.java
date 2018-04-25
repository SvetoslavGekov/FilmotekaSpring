package com.filmoteka.s9.model.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.filmoteka.s9.model.Product;

@Component
public class ProductDao {

	@Autowired
	private DBManager manager;
	
	public List<Product> getAll() {
		//select ala bala
		ArrayList<Product> products = new ArrayList<>();
		for(int i = 0; i < 10; i++) {
			Product p = new Product("Product " + (i+1), i*2, i*3.56);
			products.add(p);
		}
		return products;
	}
}
