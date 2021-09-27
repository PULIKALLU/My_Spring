package com.sg.prj.dao;

import java.util.List;

import com.sg.prj.entity.Product;

public interface ProductDao {
	public List<Product> getProducts();
	
	public void addProduct(Product p);
	
	public void updateProduct(int id, double price);
	
	public Product getProduct(int id);
}
