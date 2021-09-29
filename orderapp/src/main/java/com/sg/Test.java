package com.sg;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.sg.prj.entity.Product;

public class Test {

	public static void main(String[] args) {
		List<Product> products = 
				Arrays.asList(new Product(1,"a",4545.33, 22), new Product(2,"b",2333.55, 222));
		
		List<String> names = products.stream().map(p -> p.getName()).collect(Collectors.toList());
		
		System.out.println(names);
	}

}
