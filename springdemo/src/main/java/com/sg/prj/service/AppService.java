package com.sg.prj.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sg.prj.dao.EmployeeDao;
import com.sg.prj.entity.Employee;

@Service
public class AppService {
	@Autowired
	private EmployeeDao empDao;
	
	@Autowired
	private EmailService emailService;
	
	public void insertEmployee(Employee e) {
		empDao.addEmployee(e);
		emailService.sendEmail("Employee added");
	}
}
