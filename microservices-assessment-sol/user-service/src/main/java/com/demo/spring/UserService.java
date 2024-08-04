package com.demo.spring;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.spring.entity.User;
import com.demo.spring.exceptions.UserExistsException;
import com.demo.spring.exceptions.UserNotFoundException;
import com.demo.spring.repositories.UserRepository;

@Service
public class UserService {

	@Autowired
	UserRepository userRepository;

	public User findUserById(Integer userId) throws UserNotFoundException {
		return userRepository.findById(userId).orElseThrow(()->new UserNotFoundException());
	}

	public User createUser(User user) throws UserExistsException {
		if(userRepository.existsById(user.getUserId())) {
			throw new UserExistsException();
		}else {
			
			return userRepository.save(user);
		}
	}

	public boolean deleteUser(Integer id) throws UserNotFoundException {
		if(!userRepository.existsById(id)) {
			throw new UserNotFoundException();
		}else {
			userRepository.deleteById(id);
			return true;
		}
	}

	public List<User> listAll() {
		return userRepository.findAll();
	}
}
