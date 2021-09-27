package com.sg.prj.client;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.sg.prj.entity.Employee;
import com.sg.prj.service.AppService;

public class FirstExample {

	public static void main(String[] args) {
		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
		ctx.scan("com.sg"); //look out for @Component @Repository @Service ... and create beans , autowired
		ctx.refresh();
		
		AppService service = ctx.getBean("appService", AppService.class);
		
		Employee e = new Employee();
		
		service.insertEmployee(e);
		
//		System.out.println("********");
//		
//		String[] names = ctx.getBeanDefinitionNames();
//		for(String name : names) {
//			System.out.println(name);
//		}
	}

}
