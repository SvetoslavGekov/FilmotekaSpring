package com.filmoteka.s9.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Product {

	@NotNull
	private String name;
	@JsonIgnore
	private int quantity;
	private double price;
	private LocalDate expDate;
	private List<String> characteristics = new ArrayList<>();
	
	public Product() {
		
	}
	
	public Product(String name, int quantity, double price) {
		this.name = name;
		this.quantity = quantity;
		this.price = price;
		this.expDate = LocalDate.now();
		characteristics.add("Calories");
		characteristics.add("Obem");
		characteristics.add("Color");
	}
	
	public List<String> getCharacteristics() {
		return Collections.unmodifiableList(characteristics);
	}
	
	public String getExpDate() {
		return expDate.toString();
	}

	public String getName() {
		return name;
	}

	public int getQuantity() {
		return quantity;
	}

	public double getPrice() {
		return price;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	@Override
	public String toString() {
		return "Product [name=" + name + ", quantity=" + quantity + ", price=" + price + "]";
	}

	
	
	
}
