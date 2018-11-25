package com.contact.api.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
		List<Contact> emails = contactRepository.findByEmailAndContactbookid(contact.getEmail(), bookId);
		if (!emails.isEmpty()) {
			throw new ContactUniqueEmailException(contact.getEmail());
		} else {
			User user = userRepository.findById(userId).get();
			contact.setCreatedby(user.getUsername());
			contact.setModifiedby(user.getUsername());
			contact.setContactbookid(bookId);
			contact = contactRepository.save(contact);
			return contact;
		}
	}

	public List<Contact> getContacts(Long bookId) throws BookNotFoundException {
		Optional<ContactBook> book = contactBookRepository.findByIdAndRetired(bookId, false);
		if (book.isPresent()) {
			return contactRepository.findByContactbookidAndRetired(bookId, false);
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

	public Page<Contact> getContactsPage(Long bookId, int page) {
		return contactRepository.findByContactbookidAndRetired(bookId,false,createPageRequest(page));
	}

	private Pageable createPageRequest(int page) {
	    return new PageRequest(page, 10);
	}
	
	public Page<Contact> getContactsPageSearch(Long bookId, int page,String search) {
		return contactRepository.findByContactbookidAndRetiredAndNameOrEmailContainingIgnoreCase(bookId,false,search,createPageRequest(page));
	}
}
