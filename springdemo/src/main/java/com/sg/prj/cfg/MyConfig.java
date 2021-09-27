package com.sg.prj.cfg;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.sg.prj.service.EmailService;

@Configuration
public class MyConfig {
	// factory method
	@Bean
	public EmailService emailService() {
		return new EmailService("some address", 120);
	}
}
