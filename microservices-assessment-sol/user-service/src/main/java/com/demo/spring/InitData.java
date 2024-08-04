package com.demo.spring;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.demo.spring.entity.User;
import com.demo.spring.repositories.UserRepository;

@Component
public class InitData implements CommandLineRunner {

	@Autowired
	UserRepository userRepository;

	@Override
	public void run(String... args) throws Exception {
		userRepository.saveAll(Arrays.asList(
				new User(101, "John", "john@unimail.com"),
				new User(102, "Peter", "peter@unimail.com"),
				new User(103, "Jenny", "jenny@unimail.com"),
				new User(104, "Ken", "ken@unimail.com")
				));

	}

}
