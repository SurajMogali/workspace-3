package com.demo.spring.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.demo.spring.entity.Account;

public interface AccountRepository extends JpaRepository<Account, Integer> {

	@Query("select a from Account a where a.userId=:userId")
	public List<Account> listAccountsByUserId(Integer userId);
}
