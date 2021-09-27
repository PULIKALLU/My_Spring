package com.sg.prj.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sg.prj.dao.ProductDao;
import com.sg.prj.entity.Product;

@Service
public class OrderService {
	@Autowired
	private ProductDao productDao;
	
	public List<Product> getProducts() { 
		return productDao.getProducts();
	}
	
	@Transactional
	public void addProduct(Product p) {
		productDao.addProduct(p);
	}
	
	public Product getProduct(int id) {
		return productDao.getProduct(id);
	}
}
