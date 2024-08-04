package com.demo.spring.controllers;

import java.lang.System.Logger;
import java.util.List;
import java.util.Optional;

import org.hibernate.annotations.common.util.impl.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.demo.spring.entity.Emp;
import com.demo.spring.repository.EmpRepository;
import com.demo.spring.util.Message;

@RestController
public class EmpRestController {

	@Autowired
	EmpRepository empRepository;

	
	@GetMapping(path = "/find/{empId}", produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE }) 

	public ResponseEntity findOneEmp(@PathVariable("empId") int id) {
		Optional<Emp> empOp = empRepository.findById(id);

		if (empOp.isPresent()) {
			return ResponseEntity.ok(empOp.get());
		} else {
			throw new EmpNotFoundException();

		}

	}
	

	@PostMapping(path = "/save", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Message> saveEmp(@RequestBody Emp e) {

		if (empRepository.existsById(e.getEmpID())) {
			return ResponseEntity.ok(new Message("Emp Already Exsists"));
		} else {
			empRepository.save(e);
			return ResponseEntity.ok(new Message("Emp Saved"));
		}
	}

	@DeleteMapping(path = "/delete", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Message> deleteEmp(@RequestBody Emp e) {

		if (empRepository.existsById(e.getEmpID())) {
			empRepository.delete(e);
			return ResponseEntity.ok(new Message("Deleted"));
		} else {
			return ResponseEntity.ok(new Message("Emp not FOund"));
		}
	}

	@PutMapping(path = "/update", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)

	public ResponseEntity<Message> updateEmp(@RequestBody Emp e) {

		if (empRepository.existsById(e.getEmpID())) {
			empRepository.save(e);
			return ResponseEntity.ok(new Message("updated"));
		} else {
			return ResponseEntity.ok(new Message("Emp not FOund"));
		}
	}

	@PatchMapping(path = "/updatesal/{empId}/{salary}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Message> updateSalary(@PathVariable("empId") int id, @PathVariable("salary") double sal) {

		Optional<Emp> empOp = empRepository.findById(id);
		if (empOp.isPresent()) {
			empRepository.updateSalary(id, sal);
			return ResponseEntity.ok(new Message("Salary Updated"));
		} else {
			return ResponseEntity.ok(new Message("Emp does not exist"));
		}
	}

	@GetMapping(path = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Emp>> getEmpList() {
		return ResponseEntity.ok(empRepository.findAll());

	}

	@ExceptionHandler(EmpNotFoundException.class)
	public ResponseEntity<Message> handleEmpNotFound(EmpNotFoundException ex) {
		return ResponseEntity.ok(new Message("Emp not found"));
	}
}