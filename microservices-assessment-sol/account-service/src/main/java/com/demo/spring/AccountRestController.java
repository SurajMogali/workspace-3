package com.demo.spring;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.demo.spring.entity.Account;
import com.demo.spring.exceptions.AccountNotFoundException;
import com.demo.spring.services.AccountService;
import com.demo.spring.util.Balance;
import com.demo.spring.util.Message;

@RestController
@RequestMapping("/accounts")
public class AccountRestController {
	@Autowired
	AccountService accountService;

	@GetMapping(path = "/balance/{accNo}")
	public ResponseEntity<Balance> getBalance(@PathVariable("accNo") Integer accNo) throws AccountNotFoundException {
		return ResponseEntity.ok(new Balance(accountService.balance(accNo)+""));
	}
	

	@PatchMapping(path = "/deposit",consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public ResponseEntity<Balance> depositAmmount(@RequestParam(name="accNo",required = true) Integer accNo,@RequestParam(name="amount",required = true) Double amount ) throws AccountNotFoundException {
		return ResponseEntity.ok(new Balance(accountService.deposit(accNo, amount)+""));
	}
	
	@GetMapping(path="/{accountNo}",produces="application/json")
	public ResponseEntity<Account> findAccount(@PathVariable("accountNo") Integer id) throws AccountNotFoundException{
		return ResponseEntity.ok(accountService.findAccount(id));
	}
	
	@GetMapping(path="/list/{id}",produces="application/json")
	public ResponseEntity<List<Account>> listAccounts(@PathVariable("id") Integer id){
		return ResponseEntity.ok(accountService.listAccountsByUserId(id));
	}
	
	@PostMapping(path = "/create",consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity addAccount(@RequestBody Account account){
		
		return ResponseEntity.ok(accountService.createAccount(account));
	}
	@ExceptionHandler(AccountNotFoundException.class)
	public ResponseEntity<Message> handleAccountNotFound(AccountNotFoundException ex){
		return ResponseEntity.status(404).body(new Message("Account Not Found"));
	}
}
