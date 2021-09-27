package com.sg.prj.dao;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import com.sg.prj.entity.Employee;

@Repository
@Profile("dev")
public class EmployeeDaoFileImpl implements EmployeeDao {

	@Override
	public void addEmployee(Employee e) {
		System.out.println("file store!!!");
	}

}
