package com.demo.spring.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="USERS")
public class User {
	@Id
	@Column(name = "USER_ID")
	private Integer userId;
	@Column(name = "USER_NAME")
	private String userName;

	@Column(name = "EMAIL")
	private String email;

	public User() {

	}

	public User(Integer userId, String userName, String email) {
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
