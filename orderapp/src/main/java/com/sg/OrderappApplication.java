package com.sg;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.filter.ShallowEtagHeaderFilter;

import com.sg.prj.entity.Customer;
import com.sg.prj.entity.Product;
import com.sg.prj.service.OrderService;

@SpringBootApplication
@EnableScheduling
@EnableCaching
public class OrderappApplication implements CommandLineRunner {
	
	@Scheduled(fixedRate = 100000)
	public void doTask() {
		System.out.println("task done!!!");
	}
	
	@Bean
	public ShallowEtagHeaderFilter etag() {
		return new  ShallowEtagHeaderFilter();
	}
	
	@Autowired
	private OrderService service;
	
	/*
	@Autowired
	private RestTemplate template;
	
	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}
	
	private  void getUsers() {
		String uri = "https://jsonplaceholder.typicode.com/users";
		String result = template.getForObject(uri, String.class);
		System.out.println(result);
	}

	private void getProductsTemplate() {
		ResponseEntity<List<Product>> response = template.exchange("http://localhost:8080/api/products",
				HttpMethod.GET, null , new ParameterizedTypeReference<List<Product>>() {
				});
		List<Product> products = response.getBody();
		for(Product p : products) {
			System.out.println(p.getName());
		}
	}
	
	private void addUsingRestTemplate() {
		Product p = new Product(0, "Konfo", 80000.00, 100);
		ResponseEntity<Product> resp = 
				template.postForEntity("http://localhost:8080/api/products", p, Product.class);
		System.out.println(resp.getBody().getId());
		System.out.println(resp.getStatusCodeValue());
	}
	*/
	public static void main(String[] args) {
		SpringApplication.run(OrderappApplication.class, args);
	}

	// this method gets called once Spring container is created and initialized
	@Override
	public void run(String... args) throws Exception {
//		getUsers();
//		getProductsTemplate();
//		addUsingRestTemplate();
//		System.out.println("******  Add Product **********");
//		addProduct();
//		System.out.println("******  Get Products **********");
//		getProducts();
//		System.out.println("******  Get Product by Id **********");
//		getById();
//		System.out.println("Get Customer by email");
//		customerByEmail();
//		service.testLazy();
//		customQuery();
//		modify();
	}


	private void modify() {
		Product p = service.updateProduct(999, 5);
		System.out.println(p.getName() + ", " + p.getPrice());
	}

	private void customQuery() {
		List<Product> products = service.byRange(500, 50_000);
		for(Product p : products) {
			System.out.println(p.getName() + ", " +p.getPrice());
		}
	}

	private void customerByEmail() {
		Customer c = service.getCustomerByEmail("harry@sg.com");
		System.out.println(c.getFirstName() + ", " + c.getLastName());
	}

	private void getById() {
		Product p = service.getProduct(1);
		System.out.println(p.getName() + ", " +p.getPrice());
	}

	private void getProducts() {
		List<Product> products = service.getProducts();
		for(Product p : products) {
			System.out.println(p.getName() + ", " +p.getPrice());
		}
	}

	private void addProduct() {
		service.addProduct(new Product(0, "Hp Laptop", 120000.00, 100));
	}

}
