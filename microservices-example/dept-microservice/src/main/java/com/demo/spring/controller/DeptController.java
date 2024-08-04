	package com.demo.spring.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.spring.Exception.DeptNotfoundException;
import com.demo.spring.entity.Dept;
import com.demo.spring.entity.Message;
import com.demo.spring.repository.DeptRepository;

@RestController
@RequestMapping("/dept")

public class DeptController {

	@Autowired
	DeptRepository deptRepository;

	@GetMapping(path = "/{dno}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Dept> findDeptById(@PathVariable("dno") Integer id) throws DeptNotfoundException {

		Optional<Dept> deptOp = deptRepository.findById(id);
		if (deptOp.isPresent()) {
			return ResponseEntity.ok(deptOp.get());
		} else {
			throw new DeptNotfoundException();
		}
	}

	@GetMapping(path = "/",produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Dept>> listAll() {
		return ResponseEntity.ok(deptRepository.findAll());
	}

	@ExceptionHandler(DeptNotfoundException.class)
	public ResponseEntity handleDeptNotFoundException() {
		return ResponseEntity.ok(new Message("Dept not found"));
	}
}
