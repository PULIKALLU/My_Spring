package com.sg.prj.dao;

import org.springframework.stereotype.Repository;

import com.sg.prj.entity.Employee;

@Repository
public class EmployeeDaoJdbcImpl implements EmployeeDao {

	@Override
	public void addEmployee(Employee e) {
		System.out.println("stored in database !!!");
	}

}
