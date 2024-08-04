package com.demo.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class ThymeleafFrontApplication implements WebMvcConfigurer{

	public static void main(String[] args) {
		SpringApplication.run(ThymeleafFrontApplication.class, args);
	}

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/find").setViewName("findOne");
		registry.addViewController("/list").setViewName("list");
		registry.addViewController("/save").setViewName("save");
		registry.addViewController("/").setViewName("Home");
	}
	
	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplateBuilder().build();
	}
}
