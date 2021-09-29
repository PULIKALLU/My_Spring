package com.sg.prj.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sg.prj.dao.CustomerDao;
import com.sg.prj.dao.OrderDao;
import com.sg.prj.dao.ProductDao;
import com.sg.prj.dto.ReportDTO;
import com.sg.prj.entity.Customer;
import com.sg.prj.entity.Item;
import com.sg.prj.entity.Order;
import com.sg.prj.entity.Product;

@Service
public class OrderService {
	@Autowired
	private ProductDao productDao;
	
	@Autowired
 	private CustomerDao customerDao;

	@Autowired
	private OrderDao orderDao;
	
	public List<ReportDTO> getReports() {
		return orderDao.getReport();
	}
	
	@Transactional
	public void placeOrder(Order order) {
		double total = 0.0;
		for(Item item : order.getItems()) {
			Product p = productDao.findById(item.getProduct().getId()).get();
			item.setAmount(p.getPrice() * item.getQty()); // can add discount and tax...
			p.setQuantity(p.getQuantity() - item.getQty()); // dirty checking or ORM ==> update SQL
			if(p.getQuantity() < 0) {
				throw new IllegalArgumentException("Product not in stock!!!");
			}
			total += item.getAmount();
		}
		order.setTotal(total);
		orderDao.save(order); // persist order and items
	}
	
	public List<Order> getOrders() {
		return orderDao.findAll();
	}
	
	
 	public Customer addCustomer(Customer c) {
 		return customerDao.save(c);
 	}

 	public List<Customer> getCustomers() {
 		return customerDao.findAll();
 	}
 	
 	public Customer getCustomerByEmail(String email) {
 		return customerDao.findById(email).orElseThrow();
 	}
 	
	public List<Product> getProducts() {
		return productDao.findAll();
	}
	
	public Product getProduct(int id) {
		return productDao.findById(id).orElseThrow();
	}
	
	public Product addProduct(Product p) {
		return productDao.save(p);
	}

	@Transactional
	public void testLazy() {
//		Product p = productDao.findById(1).orElse(null);
		Product p = productDao.getById(1);
		System.out.println("Has it hit DB!!!");
//		System.out.println(p.getName());
	}
	
	public List<Product> byRange(double low, double high) {
		return productDao.getByRange(low, high);
	}
	
	@Transactional
	public Product updateProduct(double price, int id) {
		productDao.updateProduct(price, id);
		return productDao.findById(id).orElseThrow();
	}
}
