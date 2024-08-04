package com.demo.spring;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.demo.spring.entity.Account;
import com.demo.spring.repositories.AccountRepository;
import com.demo.spring.util.AccountType;

@Component
public class InitData implements CommandLineRunner {

	@Autowired
	AccountRepository accountRepository;

	@Override
	public void run(String... args) throws Exception {
		accountRepository.saveAll(Arrays.asList(
				new Account(10001, AccountType.SAVINGS, 50000.0, 101),
				new Account(10002, AccountType.DEPOSIT, 60000.0, 102),
				new Account(10003, AccountType.SAVINGS, 10000.0, 101),
				new Account(10004, AccountType.SAVINGS, 60000.0, 104)
				));

	}

}
