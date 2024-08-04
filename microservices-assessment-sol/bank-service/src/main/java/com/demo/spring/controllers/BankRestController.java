package com.demo.spring.controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.demo.spring.dto.AccountDTO;
import com.demo.spring.dto.NewAccountReqest;
import com.demo.spring.dto.ServerConfiguration;
import com.demo.spring.dto.UserDTO;
import com.demo.spring.util.Message;

@RestController
@RequestMapping("/bank")
@EnableConfigurationProperties(ServerConfiguration.class)
public class BankRestController {
	@Autowired
	RestTemplate restTemplate;

@Autowired
ServerConfiguration servers;
	/**
	 * 
	 * @param id
	 * @return a List of Account for a User with the given user 'id'
	 */
	@GetMapping(path="/accounts/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity getAccountsForUser(@PathVariable("id") Integer id) {
		HttpHeaders headers= new HttpHeaders();
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
		
		HttpEntity<Void> req= new HttpEntity<>(headers);
		
		
		ResponseEntity<UserDTO> response1=restTemplate.exchange(servers.getUsersServer()+"/users/"+id, HttpMethod.GET,req, UserDTO.class);
		UserDTO userDTO=response1.getBody();
		
		
		ResponseEntity<List<AccountDTO>> response2=
				restTemplate.exchange(servers.getAccountServer()+"/accounts/list/"+userDTO.getUserId(), 
						HttpMethod.GET,req,new ParameterizedTypeReference<List<AccountDTO>>() {	});
		
		List<AccountDTO> accList=response2.getBody();
		userDTO.setAccounts(accList);
		return ResponseEntity.ok(userDTO);
	}
	
	@PostMapping(path="/bank/newAccount", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Message> createNewAccount(@RequestBody NewAccountReqest accRequest){
		
		HttpHeaders headers= new HttpHeaders();
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
		
		HttpEntity<Void> req= new HttpEntity<>(headers);
		
		
		ResponseEntity<UserDTO> response1=restTemplate.exchange(servers.getUsersServer()+"/users/"+accRequest.getUserId(), HttpMethod.GET,req, UserDTO.class);
		UserDTO userDTO=response1.getBody();
		
		AccountDTO accDto=new AccountDTO(1000+(int)Math.random()*10, accRequest.getAccountType(),accRequest.getBalance(),userDTO.getUserId());
		HttpEntity<AccountDTO> newReq=new HttpEntity<>(accDto, headers);
		ResponseEntity<UserDTO> response2=restTemplate.exchange(servers.getAccountServer()+"/accounts/create", HttpMethod.POST,newReq, UserDTO.class);
		
		return ResponseEntity.ok(new Message("Account No :"+accDto.getAccountNumber()));
		
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity processException(Exception ex) {
		return ResponseEntity.status(404).body(new Message("User Not Found"));
	}
}
