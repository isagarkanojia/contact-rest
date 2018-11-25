package com.contact.api.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.contact.api.model.Contact;


@Repository
public interface ContactRepository extends JpaRepository<Contact, Long> {

	Optional<Contact> findByIdAndRetired(Long contactId, boolean b);
		
	List<Contact> findByEmailAndContactbookid(String email, Long bookId);

	List<Contact> findByContactbookidAndRetired(Long bookId, boolean b);

	Page<Contact> findByContactbookidAndRetired(Long bookId, boolean b, Pageable createPageRequest);

	Page<Contact> findByContactbookidAndRetiredAndNameContainingIgnoreCaseOrEmailContainingIgnoreCase(Long bookId,
			boolean b, String search, String search2, Pageable createPageRequest);

			
}
