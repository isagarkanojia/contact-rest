package com.contact.api.service;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.contact.api.exception.BookNotFoundException;
import com.contact.api.exception.ContactNotFoundException;
import com.contact.api.exception.ContactUniqueEmailException;
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

	public Contact saveContact(Contact contact, Long bookId, Long userId) throws ContactUniqueEmailException {
		Collection<Object> emails = contactRepository.findContactInContactBookByEmail(contact.getEmail(),bookId);
		if (!emails.isEmpty()) {
			throw new ContactUniqueEmailException(contact.getEmail());
		} else {
			User user = userRepository.findById(userId).get();
			ContactBook book = contactBookRepository.findById(bookId).get();
			contact.setCreatedBy(user.getUsername());
			contact.setModifiedBy(user.getUsername());
			contact = contactRepository.save(contact);
			book.getContacts().add(contact);
			userRepository.save(user);
			return contact;
		}
	}

	public Set<Contact> getContacts(Long bookId) throws BookNotFoundException {
		Optional<ContactBook> book = contactBookRepository.findByIdAndRetired(bookId, false);
		if (book.isPresent()) {
			ContactBook b = book.get();
			return b.getContacts();
		} else {
			throw new BookNotFoundException(bookId);
		}
	}

	public Contact updateContact(Contact contact) {
		contact = contactRepository.save(contact);
		return contact;
	}

	public Contact deleteContact(Long contactId) throws ContactNotFoundException {
		Optional<Contact> contact = contactRepository.findByIdAndRetired(contactId, false);
		if (contact.isPresent()) {
			Contact c = contact.get();
			c.setRetired(true);
			contactRepository.save(c);
			return c;
		} else {
			throw new ContactNotFoundException(contactId);
		}

	}

}
