package com.sg.prj.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sg.prj.entity.Order;

public interface OrderDao extends JpaRepository<Order, Integer>{

}
