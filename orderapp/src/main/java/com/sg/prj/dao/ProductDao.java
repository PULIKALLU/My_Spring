package com.sg.prj.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sg.prj.entity.Product;

public interface ProductDao extends JpaRepository<Product, Integer>{

}
