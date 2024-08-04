package com.demo.spring.dto;

import java.util.ArrayList;
import java.util.List;

public class UserDTO {
	private Integer userId;
	private String userName;
	private String email;

	private List<AccountDTO> accounts = new ArrayList<>();

	public List<AccountDTO> getAccounts() {
		return accounts;
	}

	public void setAccounts(List<AccountDTO> accounts) {
		this.accounts = accounts;
	}

	public UserDTO() {

	}

	public UserDTO(Integer userId, String userName, String email) {
		this.userId = userId;
		this.userName = userName;
		this.email = email;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
