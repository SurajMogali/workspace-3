package com.demo.spring.dto;

public class AccountDTO {
	private Integer accountNumber;
	private String accountType;
	private Double balance;
	private Integer userId;

	public AccountDTO() {

	}

	public AccountDTO(Integer accountNumber, String accountType, Double balance, Integer userId) {
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
