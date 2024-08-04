package com.demo.spring.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.demo.spring.DTO.EmpDTO;

@Controller
public class UIController {

	@Autowired
	RestTemplate restTemplate;
	
	@GetMapping(path = "/greet")
	public ModelAndView greet() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("greeter");
		mv.addObject("greetMessage","Hello from Thymeleaf");
		return mv;
		
	}
	
	@GetMapping(path = "/findOne")
	public ModelAndView findById(@RequestParam(name ="id", required = true) int id) {
		ModelAndView mv = new ModelAndView();
		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
		HttpEntity<Void> req = new HttpEntity<>(headers);
		ResponseEntity<EmpDTO> response = restTemplate.exchange("http://localhost:8181/find/"+id, HttpMethod.GET, req, EmpDTO.class);
		mv.addObject("emp",response.getBody());
		mv.setViewName("findOne");
		return mv;
		
	}
	
	@GetMapping(path = "/list")
	public ModelAndView findAll() {
		ModelAndView mv = new ModelAndView();
		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
		HttpEntity<Void> req = new HttpEntity<>(headers);
		ResponseEntity<List<EmpDTO>> response = restTemplate.exchange("http://localhost:8181/list", HttpMethod.GET, req, new ParameterizedTypeReference<List<EmpDTO>>(){});
		mv.addObject("empList",response.getBody());
		mv.setViewName("list");
		return mv;
	}
	
	@PostMapping(path="/saveEmp")
	public ModelAndView saveEmp(EmpDTO empDTO) {
		ModelAndView mv = new ModelAndView();
		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
		HttpEntity<EmpDTO> req = new HttpEntity<>(empDTO,headers);
		ResponseEntity<String> response = restTemplate.exchange("http://localhost:8181/save", HttpMethod.POST, req, String.class);
		mv.setViewName("save_success");
		mv.addObject("response",response.getBody());
		System.out.println(response.getBody());		
		return mv;
	}
}
