package com.contact.api.service;

import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.contact.api.exception.BookNotFoundException;
import com.contact.api.model.ContactBook;
import com.contact.api.model.User;
import com.contact.api.repository.ContactBookRepository;
import com.contact.api.repository.ContactRepository;
import com.contact.api.repository.UserRepository;

@Service
public class ContactBookService {

	@Autowired
	private ContactBookRepository contactBookRepository;
	
	@Autowired
	private ContactRepository contactRepository;
	
	@Autowired
	private UserRepository userRepository;

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

	public ContactBook deleteBook(Long bookId) throws BookNotFoundException {
		Optional<ContactBook> contactBook =contactBookRepository.findByIdAndRetired(bookId,false);
		if(contactBook.isPresent()){
			ContactBook c=contactBook.get();
			c.setRetired(true);
			contactBookRepository.save(c);
			return c;
		}else{
			throw new BookNotFoundException(bookId);
		}
		
	}

}
