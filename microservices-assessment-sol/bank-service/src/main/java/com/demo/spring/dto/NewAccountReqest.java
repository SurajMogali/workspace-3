package com.demo.spring.dto;

public class NewAccountReqest {
	private String accountType;
	private Double balance;
	private Integer userId;

	public NewAccountReqest() {

	}

	public NewAccountReqest(Integer userId, String accountType, Double balance) {
		this.accountType = accountType;
		this.balance = balance;
		this.userId = userId;
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
