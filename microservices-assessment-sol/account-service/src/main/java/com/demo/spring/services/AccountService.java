package com.demo.spring.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import com.demo.spring.entity.Account;
import com.demo.spring.exceptions.AccountNotFoundException;
import com.demo.spring.repositories.AccountRepository;

@Service
public class AccountService {

	@Autowired
	AccountRepository accountRepository;

	public Double balance(Integer AccountNo) throws AccountNotFoundException{
		
		if(accountRepository.existsById(AccountNo)) {
			return accountRepository.findById(AccountNo).get().getBalance();
		}else {
			throw new AccountNotFoundException();
		}

	}
	
	public Double deposit(Integer AccountNo,Double amount)throws AccountNotFoundException{
		if(accountRepository.existsById(AccountNo)) {
			Account account=accountRepository.findById(AccountNo).get();
			account.setBalance(account.getBalance()+amount);
			Account acc2=accountRepository.save(account);
			
			return acc2.getBalance();
		}else {
			throw new AccountNotFoundException();
		}
	}
	
	public Double withdraw(Integer AccountNo, Double amount) throws AccountNotFoundException{
		if(accountRepository.existsById(AccountNo)) {
			Account account=accountRepository.findById(AccountNo).get();
			account.setBalance(account.getBalance()-amount);
			Account acc2=accountRepository.save(account);
			
			return acc2.getBalance();
		}else {
			throw new AccountNotFoundException();
		}
	}
	
	public Account findAccount(Integer accountNo) throws AccountNotFoundException{
		return accountRepository.findById(accountNo).orElseThrow(()->new AccountNotFoundException());
	}
	
	public Account createAccount(Account account) {
		return accountRepository.save(account);
	}
	
	public List<Account> listAccountsByUserId(Integer id){
		return accountRepository.listAccountsByUserId(id);
	}
}
