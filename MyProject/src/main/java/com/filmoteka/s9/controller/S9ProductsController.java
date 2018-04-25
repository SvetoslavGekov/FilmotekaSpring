package com.filmoteka.s9.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.filmoteka.s9.model.Product;
import com.filmoteka.s9.model.dao.ProductDao;

@Controller
public class S9ProductsController {
	
	@Autowired
	private ProductDao productDao;

	@RequestMapping(value = "/s9/products", method = RequestMethod.GET)
	public String getProducts(Model m) {
		m.addAttribute("produkti", productDao.getAll());
		return "products";
	}

	@RequestMapping(value = "/s9/product/new", method = RequestMethod.GET)
	public String createProduct(Model m) {
		Product p = new Product();
		p.setPrice(30);
		m.addAttribute("nov_product", p);
		return "newProduct";
	}

	@RequestMapping(value = "/s9/product/new", method = RequestMethod.POST)
	public String saveProduct(@ModelAttribute Product newProduct) {
		//dao.save(p);
		System.out.println("new product = " + newProduct.toString());
		return "bravo";
	}
	
}
