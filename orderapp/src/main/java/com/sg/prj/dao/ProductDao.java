package com.sg.prj.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sg.prj.entity.Product;

public interface ProductDao extends JpaRepository<Product, Integer> {
	// select * from products where qty = ?
	List<Product> findByQuantity(int quantity);
	// select * from products where name = ?
	List<Product> findByName(String name);
	// select * from products where price = ? and quantity = ?
	List<Product> findByPriceAndQuantity(double price, int quantity);
	// select * from products where price = ? or quantity = ?
	List<Product> findByPriceOrQuantity(double price, int quantity);

//	@Query("from Product where price >= :low and price <= :high")
	@Query(value = "select * from products where price >= :low and price <= :high", nativeQuery = true)
	List<Product> getByRange(@Param("low") double lower, @Param("high") double higher);
	
	@Modifying
	@Query("update Product set price = :pr where id= :id")
	void updateProduct(@Param("pr") double price, @Param("id") int id);
}
