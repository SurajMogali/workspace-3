package com.demo.spring.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.spring.UserService;
import com.demo.spring.entity.User;
import com.demo.spring.exceptions.UserExistsException;
import com.demo.spring.exceptions.UserNotFoundException;
import com.demo.spring.repositories.UserRepository;
import com.demo.spring.util.Message;

@RestController
@RequestMapping("/users")
public class UserRestController {

	@Autowired
	UserService userService;
	
	@GetMapping(path="/{id}",produces="application/json")
	public ResponseEntity<User> getUserById(@PathVariable("id") Integer userId) throws UserNotFoundException{
		return ResponseEntity.ok(userService.findUserById(userId));
	}
	
	@GetMapping(produces="application/json")
	public ResponseEntity<List<User>> listAllUsers(){
		return ResponseEntity.ok(userService.listAll());
	}
	
	@PostMapping(path="/create",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<User> createUser(@RequestBody User user) throws UserExistsException{
		return ResponseEntity.ok(userService.createUser(user));
	}
	
}
