package com.sg.prj.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sg.prj.dao.CustomerDao;
import com.sg.prj.dao.OrderDao;
import com.sg.prj.dao.ProductDao;
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
	
	@Transactional
	public void placeOrder(Order order) {
		double total = 0.0;
		for(Item item : order.getItems()) {
			Product p = productDao.getProduct(item.getProduct().getId());
			item.setAmount(p.getPrice() * item.getQty()); // can add discount and tax...
			p.setQuantity(p.getQuantity() - item.getQty()); // dirty checking or ORM ==> update SQL
			if(p.getQuantity() < 0) {
				throw new IllegalArgumentException("Product not in stock!!!");
			}
			total += item.getAmount();
		}
		order.setTotal(total);
		orderDao.addOrder(order); // persist order and items
	}
	
	public List<Order> getOrders() {
		return orderDao.getOrders();
	}
	
	
	@Transactional
	public void addCustomer(Customer c) {
		customerDao.addCustomer(c);
	}
	
	public List<Customer> getCustomerByLastName(String lastName) {
		return customerDao.getByLastName(lastName);
	}
	
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
