package com.filmoteka.s9.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.filmoteka.s9.model.Product;
import com.filmoteka.s9.model.dao.ProductDao;

@RestController
public class MagicRestController {


	@Autowired
	private ProductDao productDao;
	
	@RequestMapping(value = "/s9/products/json", method = RequestMethod.GET)
	@ResponseBody
	public List<Product> getProduct(){
		return productDao.getAll();
	}
	
}
