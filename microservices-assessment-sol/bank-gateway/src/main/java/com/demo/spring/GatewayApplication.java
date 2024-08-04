package com.demo.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@SpringBootApplication
@RestController
@EnableConfigurationProperties(UriConfiguration.class)
public class GatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(GatewayApplication.class, args);

	}

	@Bean
	public RouteLocator myRoutes(RouteLocatorBuilder builder,UriConfiguration uriConfiguration) {
		
		String bankserver=uriConfiguration.getBankServer();
		String userserver=uriConfiguration.getUserServer();
		return builder.routes()
			.route(p -> p
				.path("/bank/**")
				.uri(bankserver))
			.route(p -> p
				.path("/users/**")
//				.filters(f -> f
//					.circuitBreaker(config -> config
//						.setName("mycmd")
//						.setFallbackUri("forward:/fallback")))
				.uri(userserver))
			.build();
	}
	
	@RequestMapping("/fallback")
	public ResponseEntity<String> fallback() {
		return ResponseEntity.ok("fallback");
	}
	
}
@ConfigurationProperties
class UriConfiguration {
	
	private String bankServer = "http://localhost:8084";
	private String userServer = "http://localhost:8084";

	public String getBankServer() {
		return bankServer;
	}

	public void setBankServer(String bankServer) {
		this.bankServer = bankServer;
	}

	public String getUserServer() {
		return userServer;
	}

	public void setUserServer(String userServer) {
		this.userServer = userServer;
	}

	
}
