package com.demo.spring.test;

import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.demo.spring.dto.AccountDTO;
import com.demo.spring.dto.UserDTO;
import com.fasterxml.jackson.databind.ObjectMapper;

import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;

import java.util.ArrayList;
import java.util.List;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT,properties = {
		"accountServer=http://localhost:${wiremock.server.port}","usersServer=http://localhost:${wiremock.server.port}" })
@AutoConfigureWireMock(port = 0)
public class BankControllerTest {

	String accountServer="http://localhost:${wiremock.server.port}";
	String usersServer="http://localhost:${wiremock.server.port}";
	
	@Autowired
	TestRestTemplate testRestTemplate;
	
	@LocalServerPort
	int port;
	
	@Test
	public void testgetAccountsForUserptSuccess() throws Exception{
		AccountDTO acc=new AccountDTO(10001, "savings", 5000.0, 101);
		UserDTO user=new UserDTO(101, "John", "john@gmail.com");
		List<AccountDTO> accountsList=new ArrayList<>();
		accountsList.add(acc);
		
		
		String userJson=new ObjectMapper().writeValueAsString(user);
		String accountsJson=new ObjectMapper().writeValueAsString(accountsList);
		
		stubFor(get(urlEqualTo("/accounts/list/101")).willReturn(aResponse()
				.withHeader("Content-Type", "application/json").withBody(accountsJson)));
		
		stubFor(get(urlEqualTo("/users/101")).willReturn(aResponse()
				.withHeader("Content-Type", "application/json").withBody(userJson)));
		
		String response=testRestTemplate.getForObject("http://localhost:"+port+"/bank/accounts/101", String.class);
		System.out.println(response);
		Assertions.assertTrue(response.contains("John"));
		
	}
	
	@Test
	public void testgetAccountsForUserFailure() throws Exception{
		AccountDTO acc=new AccountDTO(10001, "savings", 5000.0, 105);
		UserDTO user=new UserDTO(105, "John", "john@gmail.com");
		List<AccountDTO> accountsList=new ArrayList<>();
		accountsList.add(acc);
		
		
		String userJson=new ObjectMapper().writeValueAsString(user);
		String accountsJson=new ObjectMapper().writeValueAsString(accountsList);
		
		stubFor(get(urlEqualTo("/accounts/list/105")).willReturn(aResponse()
				.withHeader("Content-Type", "application/json").withBody(accountsJson)));
		
		stubFor(get(urlEqualTo("/users/105")).willReturn(aResponse()
				.withStatus(404).withBody("{\"status\":\"User Not Found\"}")));
		
		ResponseEntity<String> response=testRestTemplate.getForEntity("http://localhost:"+port+"/bank/accounts/105", String.class);
		
		Assertions.assertTrue(response.getStatusCode()==HttpStatus.NOT_FOUND);
		
	}
}
