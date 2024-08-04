package com.demo.spring.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.spring.entity.Contact;
import com.demo.spring.exception.ContactAlreadyExistsException;
import com.demo.spring.exception.ContactNotFoundException;
import com.demo.spring.repository.ContactRepository;
import com.demo.spring.util.Message;

@RestController
@RequestMapping(path = "/contact")
public class ContactRestController {

	@Autowired
	ContactRepository contactRepository;

	@GetMapping(path = "/findall", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Contact>> listAllContacts() {
		return ResponseEntity.ok(contactRepository.findAll());
	}

	@PostMapping(path = "/save", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Message> saveUser(@RequestBody Contact contact) {
		if (contactRepository.existsById(contact.getContactId())) {
			throw new ContactAlreadyExistsException();
		} else {
			contactRepository.save(contact);
			return ResponseEntity.ok(new Message("Contact Saved"));
		}
	}

	@DeleteMapping(path = "/delete/{contactId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Message> deleteUser(@PathVariable("contactId") Integer contactId) {
		if (contactRepository.existsById(contactId)) {
			contactRepository.deleteById(contactId);
			return ResponseEntity.ok(new Message("Contact Deleted"));
		} else {
			throw new ContactNotFoundException();
		}
	}

	@PatchMapping(path = "/update/{contactid}/{city}/{pincode}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Message> updateAddress(@PathVariable("contactid") Integer id,
			@PathVariable("city") String city, @PathVariable("pincode") String pincode) {
		if (contactRepository.existsById(id)) {
			contactRepository.updateAddress(id, city, pincode);
			return ResponseEntity.ok(new Message("Contact updated"));
		} else {
			throw new ContactNotFoundException();
		}
	}

	@DeleteMapping(path = "/deleteallcontact/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Message> deleteAll(@PathVariable("userId") int id) {
		contactRepository.deleteAllById(id);
		return ResponseEntity.ok(new Message("Contacts deleted with userId:" + id));
	}

	@ExceptionHandler(ContactNotFoundException.class)
	public ResponseEntity<Message> handleContactNotFoundException(ContactNotFoundException une) {
		return ResponseEntity.ok(new Message("Contact not found"));
	}

	@ExceptionHandler(ContactAlreadyExistsException.class)
	public ResponseEntity<Message> handleContactAlreadyExistsException(ContactAlreadyExistsException uae) {
		return ResponseEntity.ok(new Message("Contact Already Exists"));
	}

	@GetMapping(path = "/listcontacts/{userid}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Contact>> listContactsbyUserId(@PathVariable("userid") Integer id) {
		return ResponseEntity.ok(contactRepository.findAllContactsByUser(id));
	}

	@GetMapping(path = "/listtag/{contacttag}/{userid}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Contact>> findByTagandId(@PathVariable("contacttag") String Tag,
			@PathVariable("userid") Integer id) {
		return ResponseEntity.ok(contactRepository.findByTagAndUserId(Tag, id));
	}
	
	

}
