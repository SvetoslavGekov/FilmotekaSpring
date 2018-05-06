package com.filmoteka.model.dao.nomenclatures;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.filmoteka.dao.dbManager.DBManager;
import com.filmoteka.exceptions.InvalidProductCategoryDataException;
import com.filmoteka.model.nomenclatures.ProductCategory;

@Component
public class ProductCategoryDao implements IProductCategoryDao {
	// Fields
	@Autowired
	private DBManager dbManager;

	@Override
	public void saveProductCategory(ProductCategory pc) throws SQLException, InvalidProductCategoryDataException {
		try (PreparedStatement ps = dbManager.getCon().prepareStatement("INSERT INTO product_categories (value) VALUES(?);",
				PreparedStatement.RETURN_GENERATED_KEYS)) {
			ps.setString(1, pc.getValue());
			// If the insertion is successful
			if (ps.executeUpdate() > 0) {
				// Update the genre's Id
				try (ResultSet rs = ps.getGeneratedKeys()) {
					rs.next();
					pc.setId(rs.getInt("GENERATED_KEY"));
				}
			}
		}
	}

	@Override
	public void updateProductCategory(ProductCategory pc) throws SQLException {
		try (PreparedStatement ps = dbManager.getCon().prepareStatement("UPDATE product_categories SET value = ? WHERE category_id = ?;")) {
			ps.setString(1, pc.getValue());
			ps.setInt(2, pc.getId());
			ps.executeUpdate();
		}
	}

	@Override
	public Map<Integer, ProductCategory> getAllProductCategories() throws SQLException, InvalidProductCategoryDataException{
		TreeMap<Integer, ProductCategory> allCategories = new TreeMap<Integer, ProductCategory>();
		try (PreparedStatement ps = dbManager.getCon().prepareStatement("SELECT category_id, value FROM product_categories ORDER BY category_id;");) {
			try (ResultSet rs = ps.executeQuery();) {
				while (rs.next()) {
					// Create product category
					ProductCategory pc = new ProductCategory(rs.getInt("category_id"), rs.getString("value"));
					allCategories.put(pc.getId(), pc);
				}
			}
		}
		return allCategories;
	}

	@Override
	public ProductCategory getProductCategoryById(int id) throws SQLException, InvalidProductCategoryDataException {
		ProductCategory productCategory = null;
		try (PreparedStatement ps = dbManager.getCon().prepareStatement("SELECT category_id, value FROM product_categories WHERE category_id = ?");) {
			ps.setInt(1, id);
			try (ResultSet rs = ps.executeQuery();) {
				if (rs.next()) {
					// Create product category
					productCategory = new ProductCategory(rs.getInt("category_id"), rs.getString("value"));
				}
			}
		}
		return productCategory;
	}

}
