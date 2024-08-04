package com.demo.spring.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.demo.spring.ContactDTO;
import com.demo.spring.UserAlreadyExistsException;
import com.demo.spring.UserDTO;
import com.demo.spring.UserNotFoundException;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@RestController
@RequestMapping("/manage")
public class ManagementController {

	@Autowired
	RestTemplate restTemplate;// RestTemplate is the central Spring class for client-side HTTP access.

	@Autowired

	@Qualifier("restTemplate2")
	RestTemplate restTemplate2;

	@GetMapping(path = "/listallcontacts/{userId}")
	@CircuitBreaker(name = "management-service", fallbackMethod = "fallbackGetListAllContacts")
	public ResponseEntity listall(@PathVariable("userId") Integer userId) {

		UserDTO userDto = null;
		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);

		HttpEntity<Void> req = new HttpEntity<>(headers);

		ResponseEntity<UserDTO> response1 = restTemplate.exchange("http://user-service/user/find/" + userId,
				HttpMethod.GET, req, UserDTO.class);
		userDto = response1.getBody();

		ResponseEntity<List<ContactDTO>> response2 = restTemplate2.exchange(
				"http://contact-service/contact/listcontacts/" + userId, HttpMethod.GET, req,
				new ParameterizedTypeReference<List<ContactDTO>>() {
				});

		List<ContactDTO> contactList = response2.getBody();

		userDto.setContactList(contactList);
		return ResponseEntity.ok(userDto);
	}

	@GetMapping(path = "/findcontact/{contacttag}/{userid}", produces = MediaType.APPLICATION_JSON_VALUE)
	@CircuitBreaker(name = "management-service", fallbackMethod = "fallbackGetFindContact")
	public ResponseEntity<List<ContactDTO>> findContact(@PathVariable("contacttag") String ct,
			@PathVariable("userid") Integer userId) {
		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
		HttpEntity<Void> req = new HttpEntity<>(headers);
		ResponseEntity<UserDTO> response1 = restTemplate.exchange("http://user-service/user/find/" + userId,
				HttpMethod.GET, req, UserDTO.class);
		UserDTO userDto = response1.getBody();
		if (userDto.getUserId() != 0) {
			ResponseEntity<List<ContactDTO>> response2 = restTemplate2.exchange(
					"http://contact-service/contact/listtag/" + ct + "/" + userId, HttpMethod.GET, req,
					new ParameterizedTypeReference<List<ContactDTO>>() {
					});

			List<ContactDTO> contactList = response2.getBody();
			return ResponseEntity.ok(contactList);
		} else {
			throw new UserNotFoundException();
		}

	}

	@DeleteMapping(path = "/delete/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
	@CircuitBreaker(name = "management-service", fallbackMethod = "fallbackGetDelete")
	public ResponseEntity deleteUserWithAllContacts(@PathVariable("userId") Integer userId) {

		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
		HttpEntity<Void> req = new HttpEntity<>(headers);

		ResponseEntity<String> response1 = restTemplate.exchange("http://user-service/user/delete/" + userId,
				HttpMethod.DELETE, req, String.class);
		System.out.println(response1.getBody());

		if (response1.getBody().contains("User Deleted")) {

			// UserDTO userDto = response1.getBody();

			ResponseEntity<ContactDTO> response2 = restTemplate2.exchange(
					"http://contact-service/contact/deleteallcontact/" + userId, HttpMethod.DELETE, req,
					ContactDTO.class);

			return ResponseEntity.ok("Contact Deleted");

		} else {
			throw new UserNotFoundException();
		}
	}

	@PostMapping(path = "/save", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@CircuitBreaker(name = "management-service", fallbackMethod = "fallbackGetSave")
	public ResponseEntity saveUserAndContact(@RequestBody UserDTO userDto) {

		ContactDTO cDto = userDto.getContactList().get(0);

		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
		headers.set("Content-Type", MediaType.APPLICATION_JSON_VALUE);

		HttpEntity<UserDTO> req1 = new HttpEntity<>(userDto, headers);

		HttpEntity<ContactDTO> req2 = new HttpEntity<>(cDto, headers);

		ResponseEntity<String> response1 = restTemplate.exchange("http://user-service/user/save/", HttpMethod.POST,
				req1, String.class);

		// System.out.println(response1.getBody());
		if (response1.getBody().contains("User Saved")) {

			restTemplate2.exchange("http://contact-service/contact/save/", HttpMethod.POST, req2, String.class);

			return ResponseEntity.ok("User Saved");

		} else {
			throw new UserAlreadyExistsException();
		}
	}

	@ExceptionHandler(UserAlreadyExistsException.class)
	public ResponseEntity handle2(UserAlreadyExistsException une) {
		return ResponseEntity.ok("User already Exists");
	}

	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity handle(UserNotFoundException une) {
		return ResponseEntity.ok("User does not exsist");
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity handle404(Exception ex) {
		return ResponseEntity.status(404).body(ex.getMessage());
	}

	public ResponseEntity fallbackGetListAllContacts(Integer userId, Exception ex) {
		return ResponseEntity.ok("Service Down try after try some time..");
	}

	public ResponseEntity fallbackGetFindContact(String ct, Integer userId, Exception ex) {
		return ResponseEntity.ok("Service Down  try after try some time..");
	}

	public ResponseEntity fallbackGetDelete(Integer userId, Exception ex) {
		return ResponseEntity.ok("Service Down try  after try some time..");
	}

	public ResponseEntity fallbackGetSave(UserDTO userDto, Exception ex) {
		return ResponseEntity.ok("Service Down try  after try some time..");
	}
}
