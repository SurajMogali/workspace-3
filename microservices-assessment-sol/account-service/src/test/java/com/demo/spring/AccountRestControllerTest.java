package com.demo.spring;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.demo.spring.entity.Account;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class AccountRestControllerTest {
	@LocalServerPort
	int port;

	@Autowired
	MockMvc mvc;

	@Autowired
	TestRestTemplate testRestTemplate;

	@Test
	void testGetBalance() throws Exception {
		mvc.perform(get("/accounts/10002")).andExpect(status().isOk())
				.andExpect(content().contentType("application/json")).andExpect(jsonPath("$.balance").isNotEmpty());
	}

	@Test
	void testDepositAmmount() throws Exception {

		mvc.perform(patch("/accounts/deposit").param("accNo", "10003").param("amount", "6000")
				.contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)).andExpect(status().isOk())
				.andExpect(content().contentType("application/json")).andExpect(jsonPath("$.balance").isNotEmpty());
	}

	@Test
	void testFindAccount() throws Exception {
		mvc.perform(get("/accounts/10002")).andExpect(status().isOk())
				.andExpect(content().contentType("application/json")).andExpect(jsonPath("$.accountNumber").value("10002"));
	}

	@Test
	void testListAccounts() throws Exception {
		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", "application/json");
		HttpEntity<Void> request = new HttpEntity<>(headers);

		ResponseEntity<List<Account>> response = testRestTemplate.exchange(
				"http://localhost:" + port + "/accounts/list/102", HttpMethod.GET, request,
				new ParameterizedTypeReference<List<Account>>() {
				});
		
		Assertions.assertThat(response.getStatusCode().is2xxSuccessful());
		assertThat(response.getBody().size()>0);
		
	}

	@Test
	void testAddAccount() {
		Account account=new Account(10009, "savings", 70000.0, 102);
		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", "application/json");
		HttpEntity<Account> request = new HttpEntity<>(account,headers);

		ResponseEntity<Account> response = testRestTemplate.exchange(
				"http://localhost:" + port + "/accounts/create", HttpMethod.POST, request,Account.class);
		
		Assertions.assertThat(response.getStatusCode().is2xxSuccessful());
		assertThat(response.getBody().getAccountNumber()==10009);
	}

	@Test
	void testHandleAccountNotFound() throws Exception{
		mvc.perform(get("/accounts/10012")).andExpect(status().isNotFound())
		.andExpect(content().contentType("application/json")).andExpect(jsonPath("$.status").value("Account Not Found"));
	}

}
