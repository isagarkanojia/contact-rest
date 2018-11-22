package com.contact.api.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.contact.api.exception.BookNotFoundException;
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

	public ContactBook saveBook(ContactBook contactBook, Long userId) {	
		User user=userRepository.findById(userId).get();
		contactBook.setCreatedby(user.getUsername());
		contactBook.setModifiedby(user.getUsername());
		contactBook.setUserid(user.getId());
		ContactBook book=contactBookRepository.save(contactBook);
		return book;
		
	}

	public List<ContactBook> getBooks(Long userId) {
		return contactBookRepository.findByUseridAndRetired(userId,false);
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
