package com.demo.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableDiscoveryClient
@EnableConfigurationProperties(MyServerConfiguration.class)
public class UserManagementGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserManagementGatewayApplication.class, args);
	}

	@Bean
	public RouteLocator appRoutes(RouteLocatorBuilder routeBuilder, MyServerConfiguration config) {
		String userServer = config.getUserServer();
		String contactServer = config.getContactServer();
		String manageServer = config.getManageServer();

		return routeBuilder.routes().route(p -> p.path("/manage/**").uri(manageServer))
				// .uri(" http://localhost:8085"))
				.route(p -> p.path("/user/**").uri(userServer))
				// .uri("http://localhost:8181"))
				.route(p -> p.path("/contact/**").uri(contactServer))
				// .uri("http://localhost:8183"))
				.build();
	}
}

@ConfigurationProperties
class MyServerConfiguration {
	private String userServer = "http://localhost:8181";
	private String contactServer = "http://localhost:8182";
	private String manageServer = "http://localhost:8085";

	public String getUserServer() {
		return userServer;
	}

	public void setUserServer(String userServer) {
		this.userServer = userServer;
	}

	public String getContactServer() {
		return contactServer;
	}

	public void setContactServer(String contactServer) {
		this.contactServer = contactServer;
	}

	public String getManageServer() {
		return manageServer;
	}

	public void setManageServer(String manageServer) {
		this.manageServer = manageServer;
	}

}
