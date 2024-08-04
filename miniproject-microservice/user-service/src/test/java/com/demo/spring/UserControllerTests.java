package com.demo.spring;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import com.demo.spring.entity.User;
import com.demo.spring.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class UserControllerTests {

	@Autowired
	MockMvc mvc;

	@MockBean
	UserRepository userRepository;

	@Autowired
	TestRestTemplate template;

	@Test
	public void testFindSuccess() throws Exception {
		User user = new User(20, "bharat");
		when(userRepository.findById(20)).thenReturn(Optional.of(user));
		mvc.perform(get("/user/find/20")).andDo(print()).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.userName").value("bharat"));
	}

	@Test
	public void testFindFailure() throws Exception {
		User user = new User(1000, "amith");
		when(userRepository.findById(1000)).thenReturn(Optional.empty());
		mvc.perform(get("/user/find/1000")).andDo(print()).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.status").value("User not found"));
	}

	@Test
	public void testSaveSuccess() throws Exception {
		User user = new User(20, "Shreyas");
		ObjectMapper mapper = new ObjectMapper();// from jackson
		String empJson = mapper.writeValueAsString(user);

		when(userRepository.existsById(20)).thenReturn(false);
		// Incoming JSON //outgoing JSON
		mvc.perform(post("/user/save/").content(empJson).contentType(MediaType.APPLICATION_JSON_VALUE))// consumes JSON
				.andDo(print()).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.status").value("User Saved"));
	}

	@Test
	public void testSaveFailure() throws Exception {
		User user = new User(10, "amith");
		ObjectMapper mapper = new ObjectMapper();// from jackson
		String userJson = mapper.writeValueAsString(user);

		when(userRepository.existsById(10)).thenReturn(true);
		// Incoming JSON //outgoing JSON
		mvc.perform(post("/user/save").content(userJson).contentType(MediaType.APPLICATION_JSON_VALUE))// consumes JSON
				.andDo(print()).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.status").value("User Already Exists"));
	}

	@Test
	public void testFindAll() {
		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", "application/json");
		HttpEntity<Void> req = new HttpEntity<>(headers);
		ResponseEntity<List<User>> productList = template.exchange("http://localhost:8181/user/list/", HttpMethod.GET,
				req, new ParameterizedTypeReference<List<User>>() {
				});
		Assertions.assertTrue(productList.getBody().size() > 0);
	}

	@Test

	public void testDeleteFailure() throws Exception {

		when(userRepository.existsById(1000)).thenReturn(false);

		// Incoming JSON //outgoing JSON
		mvc.perform(delete("/user/delete/1000").contentType(MediaType.APPLICATION_JSON_VALUE))// consumes JSON
				.andDo(print()).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.status").value("User not found"));
	}

	@Test
	public void testDeleteSuccess() throws Exception {

		when(userRepository.existsById(10)).thenReturn(true);

		// Incoming JSON //outgoing JSON
		mvc.perform(delete("/user/delete/10").contentType(MediaType.APPLICATION_JSON_VALUE))// consumes JSON
				.andDo(print()).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.status").value("User Deleted"));
	}

}
