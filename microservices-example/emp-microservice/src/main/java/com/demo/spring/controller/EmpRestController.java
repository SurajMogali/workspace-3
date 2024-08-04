package com.demo.spring.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.spring.entity.Employee;
import com.demo.spring.entity.Message;
import com.demo.spring.repository.EmployeeRepository;

import io.micrometer.core.annotation.Timed;

@RestController
@RequestMapping("/employee")
public class EmpRestController {

	@Autowired
	EmployeeRepository empRepo;

	@GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed(value="requests.count.findbyid")
	public ResponseEntity findOneEmp(@PathVariable("id") int empId) {
		Optional<Employee> empOp = empRepo.findById(empId);
		if (empOp.isPresent()) {
			return ResponseEntity.ok(empOp.get());
		} else {
			return ResponseEntity.ok(new Message("Emp not found"));
		}

	}

	@PostMapping(path = "/", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Message> saveEmp(@RequestBody Employee emp) {
		if (empRepo.existsById(emp.getEmployeeId())) {
			return ResponseEntity.ok(new Message("Emp already exists"));
		} else {
			empRepo.save(emp);
			return ResponseEntity.ok(new Message("Emp saved"));
		}

	}

	@GetMapping(path = "/")
	@Timed(value="requests.count.listall")
	public ResponseEntity<List<Employee>> listAll() {
		return ResponseEntity.ok(empRepo.findAll());

	}

	@GetMapping(path = "/list/{dno}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Employee>> getAllByDno(@PathVariable("dno") int dno) {
		return ResponseEntity.ok(empRepo.findAllByDeptNo(dno));
	}

}
