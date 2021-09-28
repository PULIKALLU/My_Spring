package com.sg.prj.dao;

import java.util.List;

import com.sg.prj.entity.Order;

public interface OrderDao {
	void addOrder(Order order);
	List<Order> getOrders();
}
