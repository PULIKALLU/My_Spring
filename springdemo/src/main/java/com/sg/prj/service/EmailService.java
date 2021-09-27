package com.sg.prj.service;

public class EmailService {
	private String address;
	private int port;
	
	public EmailService(String address, int port) {
		this.address = address;
		this.port = port;
	}
	
	public void sendEmail(String msg) {
		System.out.println("email sent to " + address + " : " + port + " Msg: " + msg);
 	}
	
}
