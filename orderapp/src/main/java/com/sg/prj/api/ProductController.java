package com.sg.prj.api;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.CacheControl;
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

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("api/products")
//@Api()
public class ProductController {
	@Autowired
	private OrderService service;

	// GET
	// depending on "Accept:application/json" @ResponseBody uses
	// HttpMessageHandler to convert List<Product> to JSON / XML
	// http://localhost:8080/api/products
	//http://localhost:8080/api/products?low=1000&high=50000
	@SwaggerEnabled()
	@ApiOperation(value = "endpoint returns all the products",produces = "json")
	@GetMapping()
	public @ResponseBody List<Product> getProducts(@RequestParam(name="low", defaultValue = "0.0") double low,
			@RequestParam(name="high", defaultValue = "0.0") double high) {
		if(low != 0.0 && high != 0.0) {
			return service.byRange(low, high);
		}
		return service.getProducts();
		
	}
	
	@GetMapping("/{pid}/cachecontrol")
	@ResponseStatus(value = HttpStatus.OK)
	public ResponseEntity<Product> getProductCache(@PathVariable("pid") int id) throws NotFoundException {
		 return ResponseEntity.ok().cacheControl(CacheControl.maxAge(60, TimeUnit.MINUTES)).body(service.getProduct(id));
	}
	
	@GetMapping("/{pid}/etag")
	@ResponseStatus(value = HttpStatus.OK)
	public ResponseEntity<Product> getProductEtag(@PathVariable("pid") int id) throws NotFoundException {
			Product p = service.getProduct(id);
			return ResponseEntity.ok().eTag(String.valueOf(p.hashCode())).body(p);
	}
	
	// GET
	// http://localhost:8080/api/products/3
	// "3" is taken as PathVariable
	@Cacheable(value = "productCache", key="#id")
	@GetMapping("/{pid}")
	@ResponseStatus(value = HttpStatus.OK)
	public @ResponseBody Product getProduct(@PathVariable("pid") int id) throws NotFoundException {
		System.out.println("Cache Miss!!!");
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		try {
			return service.getProduct(id);
		} catch (NoSuchElementException e) {
			throw new NotFoundException("Product with " + id + " doesn't exist!!!");
		}
	}

	// POST
	// http://localhost:8080/api/products
	// payload [json/xml] is sent from client ==> converted to Product @RequestBody
	@PostMapping()
	public ResponseEntity<Product> addProduct(@RequestBody @Valid Product p) {
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
