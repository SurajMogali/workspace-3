package com.demo.spring.dto;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties
public class ServerConfiguration {

	String accountServer="http://localhost:8081";
	String usersServer="http://localhost:8084";
	public String getAccountServer() {
		return accountServer;
	}
	public void setAccountServer(String accountServer) {
		this.accountServer = accountServer;
	}
	public String getUsersServer() {
		return usersServer;
	}
	public void setUsersServer(String usersServer) {
		this.usersServer = usersServer;
	}
	
}
