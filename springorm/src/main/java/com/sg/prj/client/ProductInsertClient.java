package com.sg.prj.client;

import java.util.List;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.sg.prj.entity.Product;
import com.sg.prj.service.OrderService;

public class ProductInsertClient {

	public static void main(String[] args) {
		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
		ctx.scan("com.sg"); //look out for @Component @Repository @Service ... and create beans , autowired
		ctx.refresh();
		OrderService service = ctx.getBean("orderService", OrderService.class);
		
//		addProducts(service);
		
//		getProducts(service);
	}
	
	private static void addProducts(OrderService service) {
		Product p1 = new Product(0, "iPhone 12", 89000.00, 200);
		Product p2 = new Product(0, "Logitech Headphone", 1300.00, 200);
		Product p3 = new Product(0, "HP Printer", 24000.00, 200);
		Product p4 = new Product(0, "Sony Bravia", 130000.00, 200);
		
		service.addProduct(p1);
		service.addProduct(p2);
		service.addProduct(p3);
		service.addProduct(p4);
	}

	private static void getProducts(OrderService service) {
		List<Product> products = service.getProducts();
		for(Product p : products) {
			System.out.println(p.getName() + ", " + p.getPrice());
		}
	}

}
