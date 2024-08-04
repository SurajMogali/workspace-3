package com.demo.spring.controllers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

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
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;


import com.demo.spring.entity.User;
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class UserRestControllerTest {

	@LocalServerPort
	int port;

	@Autowired
	MockMvc mvc;

	@Autowired
	TestRestTemplate testRestTemplate;
	
	@Test
	void testGetUserById() throws Exception{
		mvc.perform(get("/users/102")).andExpect(status().isOk())
		.andExpect(content().contentType("application/json")).andExpect(jsonPath("$.userId").value("102"));
	}

	@Test
	void testListAllUsers() {
		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", "application/json");
		HttpEntity<Void> request = new HttpEntity<>(headers);

		ResponseEntity<List<User>> response = testRestTemplate.exchange(
				"http://localhost:" + port + "/users", HttpMethod.GET, request,
				new ParameterizedTypeReference<List<User>>() {
				});
		
		Assertions.assertThat(response.getStatusCode().is2xxSuccessful());
		assertThat(response.getBody().size()>0);
	}

	@Test
	void testCreateUser() {
		User user=new User(108, "James", "james@gmail.com");
		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", "application/json");
		HttpEntity<User> request = new HttpEntity<>(user,headers);

		ResponseEntity<User> response = testRestTemplate.exchange(
				"http://localhost:" + port + "/users/create", HttpMethod.POST, request,User.class);
		
		Assertions.assertThat(response.getStatusCode().is2xxSuccessful());
		assertThat(response.getBody().getUserId()==108);
	}

}
