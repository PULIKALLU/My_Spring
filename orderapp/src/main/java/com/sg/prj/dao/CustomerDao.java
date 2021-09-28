package com.sg.prj.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sg.prj.entity.Customer;

public interface CustomerDao extends JpaRepository<Customer, String> {

}
