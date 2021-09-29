package com.sg.prj.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.sg.prj.entity.Product;
import com.sg.prj.service.OrderService;

@RestController
@RequestMapping("api/products")
public class ProductController {
	@Autowired
	private OrderService service;

	// GET
	// depending on "Accept:application/json" @ResponseBody uses
	// HttpMessageHandler to convert List<Product> to JSON / XML
	// http://localhost:8080/api/products
	//http://localhost:8080/api/products?low=1000&high=50000
	@GetMapping()
	public @ResponseBody List<Product> getProducts(@RequestParam(name="low", defaultValue = "0.0") double low,
			@RequestParam(name="high", defaultValue = "0.0") double high) {
		if(low != 0.0 && high != 0.0) {
			return service.byRange(low, high);
		}
		return service.getProducts();
		
	}

	// GET
	// http://localhost:8080/api/products/3
	// "3" is taken as PathVariable
	@GetMapping("/{pid}")
	@ResponseStatus(value = HttpStatus.OK)
	public @ResponseBody Product getProduct(@PathVariable("pid") int id) {
		return service.getProduct(id);
	}

	// POST
	// http://localhost:8080/api/products
	// payload [json/xml] is sent from client ==> converted to Product @RequestBody
	@PostMapping()
	public ResponseEntity<Product> addProduct(@RequestBody Product p) {
		service.addProduct(p);
		return new ResponseEntity<Product>(p, HttpStatus.CREATED);
	}

	// PUT
	// http://localhost:8080/api/products/3
	// payload [json/xml] is sent from client ==> converted to Product @RequestBody
	@PutMapping("/{pid}")
	public @ResponseBody Product updateProduct(@PathVariable("pid") int id, @RequestBody Product p) {
		service.updateProduct(p.getPrice(), id);
		return service.getProduct(id);
	}
}
