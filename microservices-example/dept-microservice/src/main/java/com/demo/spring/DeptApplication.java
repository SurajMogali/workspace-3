package com.demo.spring;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class DeptApplication {
	
	public static void main(String[] args) {
	 ConfigurableApplicationContext ctx = SpringApplication.run(DeptApplication.class, args);
	 //ConfigurableApplicationContext ctx = SpringApplication.run(DeptApplication.class, args);
	}

}
