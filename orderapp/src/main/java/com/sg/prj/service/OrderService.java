package com.sg.prj.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sg.prj.dao.ProductDao;
import com.sg.prj.entity.Product;

@Service
public class OrderService {
	@Autowired
	private ProductDao productDao;
	
	public List<Product> getProducts() {
		return productDao.findAll();
	}
	
	public Product getProduct(int id) {
		return productDao.findById(id).orElseThrow();
	}
	
	public Product addProduct(Product p) {
		return productDao.save(p);
	}
}
