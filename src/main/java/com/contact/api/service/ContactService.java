package com.contact.api.service;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.contact.api.model.Contact;
import com.contact.api.model.ContactBook;
import com.contact.api.model.User;
import com.contact.api.repository.ContactBookRepository;
import com.contact.api.repository.ContactRepository;
import com.contact.api.repository.UserRepository;

@Service
public class ContactService {

	@Autowired
	private ContactRepository contactRepository;
	
	@Autowired
	private ContactBookRepository contactBookRepository;
	
	@Autowired
	private UserRepository userRepository;

	private static final Logger logger = LoggerFactory.getLogger(ContactService.class);

	public Contact saveContact(Contact contact, Long bookId,Long userId) {
		User user=userRepository.findById(userId).get();
		ContactBook book=contactBookRepository.findById(bookId).get();
		contact.setCreatedBy(user.getUsername());
		contact.setModifiedBy(user.getUsername());
		contact=contactRepository.save(contact);
		book.getContacts().add(contact);
		userRepository.save(user);
		return contact;
	}

	public Set<Contact> getContacts(Long bookId) {
		ContactBook book=contactBookRepository.findById(bookId).get();
		return book.getContacts();
	}

	public Contact updateContact(Contact contact) {
		contact=contactRepository.save(contact);
		return contact;
	}

	public Contact deleteContact(Long contactId) {
		Contact contact =contactRepository.findById(contactId).get();
		contact.setRetired(true);
		contactRepository.save(contact);
		return contact;
	}

}
