package com.demo.spring.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.demo.spring.entity.Contact;

public interface ContactRepository extends JpaRepository<Contact, Integer> {

	@Query("UPDATE Contact c set c.city=:city, c.pinCode=:pinCode where c.contactId=:contactId")
	@Modifying
	@Transactional
	public int updateAddress(int contactId, String city, String pinCode);

	@Query("select c from Contact c where c.userId=:userId") // doubt
	public List<Contact> findAllContactsByUser(Integer userId);

	@Query("select c from Contact c where c.userId=:userId and c.contactTag=:contactTag")
	public List<Contact> findByTagAndUserId(String contactTag, Integer userId);

	@Query("delete from Contact c where c.userId=:userId")
	@Modifying
	@Transactional
	public Integer deleteAllById(Integer userId);

}