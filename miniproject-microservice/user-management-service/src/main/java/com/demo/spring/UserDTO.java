package com.demo.spring;

import java.util.ArrayList;
import java.util.List;

public class UserDTO {

	private int userId;

	private String userName;

	List<ContactDTO> contactList = new ArrayList<>();

	public UserDTO() {
	}

	public UserDTO(int userId, String userName) {
		super();
		this.userId = userId;
		this.userName = userName;
	}

	public List<ContactDTO> getContactList() {
		return contactList;
	}

	public void setContactList(List<ContactDTO> contactList) {
		this.contactList = contactList;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

}
