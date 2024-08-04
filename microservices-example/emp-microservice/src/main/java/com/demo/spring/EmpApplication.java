package com.demo.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient//@EnableDiscoveryClient (or @EnableEurekaClient ) 
// you can use it to discover service instances from the Eureka Server
public class EmpApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(EmpApplication.class, args);
	}

}
