package com.sg.prj.client;

import java.util.List;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.sg.prj.entity.Item;
import com.sg.prj.entity.Order;
import com.sg.prj.service.OrderService;

public class OrderFetchClient {

	public static void main(String[] args) {
		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
		ctx.scan("com.sg"); // look out for @Component @Repository @Service ... and create beans , autowired
		ctx.refresh();
		OrderService service = ctx.getBean("orderService", OrderService.class);
		List<Order> orders = service.getOrders();
		for(Order order : orders) {
			System.out.println(order.getOrderDate() + " ==> " + order.getTotal() + " ==> " + order.getCustomer().getFirstName());
			for(Item item : order.getItems()) {
				System.out.println(item.getProduct().getName() + " : " + item.getQty() + " : " + item.getAmount());
			}
			System.out.println("********");
		}
		
	}

}
