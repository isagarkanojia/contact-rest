package com.contact.api.service;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.contact.api.model.ContactBook;
import com.contact.api.model.User;
import com.contact.api.repository.ContactBookRepository;
import com.contact.api.repository.UserRepository;

@Service
public class ContactBookService {

	@Autowired
	private ContactBookRepository contactBookRepository;
	
	@Autowired
	private UserRepository userRepository;

	private static final Logger logger = LoggerFactory.getLogger(ContactBookService.class);

	public ContactBook saveBook(ContactBook contactBook, Long userId) {	
		User user=userRepository.findById(userId).get();
		contactBook.setCreatedBy(user.getUsername());
		contactBook.setModifiedBy(user.getUsername());
		ContactBook book=contactBookRepository.save(contactBook);
		user.getContactBooks().add(book);
		userRepository.save(user);
		return book;
		
	}

	public Set<ContactBook> getBooks(Long userId) {
		User user =userRepository.findById(userId).get();
		return user.getContactBooks();
	}

}
