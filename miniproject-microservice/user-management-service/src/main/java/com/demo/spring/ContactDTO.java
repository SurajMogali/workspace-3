package com.demo.spring;

public class ContactDTO {

	private int contactId;
	private String contactTag;
	private String city;
	private String pinCode;
	private String email;
	private int userId;

	public ContactDTO() {
	}

	public ContactDTO(int contactId, String contactTag, String city, String pinCode, String email, int userId) {
		super();
		this.contactId = contactId;
		this.contactTag = contactTag;
		this.city = city;
		this.pinCode = pinCode;
		this.email = email;
		this.userId = userId;
	}

	public int getContactId() {
		return contactId;
	}

	public void setContactId(int contactId) {
		this.contactId = contactId;
	}

	public String getContactTag() {
		return contactTag;
	}

	public void setContactTag(String contactTag) {
		this.contactTag = contactTag;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getPinCode() {
		return pinCode;
	}

	public void setPinCode(String pinCode) {
		this.pinCode = pinCode;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

}
