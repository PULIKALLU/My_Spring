package com.sg.prj.dto;

import java.util.Date;

public class ReportDTO {
	// from orders
	private Date orderDate;
	private double total;
	private String email;
	// from customers
	private String firstName;
	private String lastName;
	
	public ReportDTO() {
	}
	public ReportDTO(Date orderDate, double total, String email, String firstName, String lastName) {
		this.orderDate = orderDate;
		this.total = total;
		this.email = email;
		this.firstName = firstName;
		this.lastName = lastName;
	}
	public Date getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}
	public double getTotal() {
		return total;
	}
	public void setTotal(double total) {
		this.total = total;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
}
