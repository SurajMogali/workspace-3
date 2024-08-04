package com.demo.spring.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Account {

	@Id
	@Column(name = "ACCOUNT_NUMBER")
	private Integer accountNumber;
	@Column(name = "ACCOUNT_TYPE")
	private String accountType;

	@Column(name = "BALANCE")
	private Double balance;

	@Column(name = "USER_ID")
	private Integer userId;

	public Account() {

	}

	public Account(Integer accountNumber, String accountType, Double balance, Integer userId) {
		this.accountNumber = accountNumber;
		this.accountType = accountType;
		this.balance = balance;
		this.userId = userId;
	}

	public Integer getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(Integer accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public Double getBalance() {
		return balance;
	}

	public void setBalance(Double balance) {
		this.balance = balance;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

}
