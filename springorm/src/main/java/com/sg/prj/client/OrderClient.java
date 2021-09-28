package com.sg.prj.client;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.sg.prj.entity.Customer;
import com.sg.prj.entity.Item;
import com.sg.prj.entity.Order;
import com.sg.prj.entity.Product;
import com.sg.prj.service.OrderService;

public class OrderClient {

	public static void main(String[] args) {
		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
		ctx.scan("com.sg"); // look out for @Component @Repository @Service ... and create beans , autowired
		ctx.refresh();
		OrderService service = ctx.getBean("orderService", OrderService.class);
		Order order = new Order();
		Customer customer = new Customer();
		customer.setEmail("harry@sg.com"); // session
		order.setCustomer(customer);
		
		Product p1 = new Product();
		p1.setId(3);
		
		
		Product p2 = new Product();
		p2.setId(1);
		
		Item i1 = new Item();
		i1.setProduct(p1);
		i1.setQty(2);
		
		Item i2 = new Item();
		i2.setProduct(p2);
		i2.setQty(1);
		
		order.getItems().add(i1);
		order.getItems().add(i2);
		
		service.placeOrder(order);
		
	}

}
