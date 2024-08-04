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
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.demo.spring.DeptDTO;
import com.demo.spring.EmpDTO;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@RestController
@RequestMapping("/hr")
public class HrRestController {

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	@Qualifier("restTemplate2")
	RestTemplate restTemplate2;
	
	@GetMapping(path="/info")
	public String info() {
		return "This is HR";
	}

	@GetMapping(path = "/dept/{dno}")
	@CircuitBreaker(name="hr-service", fallbackMethod = "fallbackGetEmpInDept")
	public ResponseEntity getEmpInDept(@PathVariable("dno") Integer deptNo) {
		DeptDTO deptDto = null;
		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
		//HTTP headers let the client and the server pass additional information with an HTTP request or response
		HttpEntity<Void> req = new HttpEntity<>(headers);

		// service discovery
		// ResponseEntity<DeptDTO> response1 =
		// restTemplate.exchange("http://a/dept/"+deptNo,
		// HttpMethod.GET,req,DeptDTO.class);

		// get the dept for dno
		ResponseEntity<DeptDTO> response1 = restTemplate.exchange("http://dept-service/dept/" + deptNo, HttpMethod.GET,
				req, DeptDTO.class);
		deptDto = response1.getBody();
				
		// get all the employees in above dno
		ResponseEntity<List<EmpDTO>> response2 = restTemplate2.exchange("http://emp-service/employee/list/" + deptNo,
				HttpMethod.GET, req, new ParameterizedTypeReference<List<EmpDTO>>() {
				});

		List<EmpDTO> empList = response2.getBody();
		deptDto.setEmpList(empList);
		return ResponseEntity.ok(deptDto);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity handle404(Exception ex) {
		return ResponseEntity.status(404).body(ex.getMessage());
	}

	public ResponseEntity fallbackGetEmpInDept( Integer deptNo,Exception ex) {
		return ResponseEntity.ok("Service Down,Try after sometime");
	}
}