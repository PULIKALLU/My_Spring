package com.sg.prj.dao;

import java.util.List;

import com.sg.prj.entity.Customer;

public interface CustomerDao {
	void addCustomer(Customer c);
	List<Customer> getByLastName(String lastName);
}
