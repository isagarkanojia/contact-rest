package com.contact.api.repository;

import java.util.Collection;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.contact.api.model.Contact;


@Repository
public interface ContactRepository extends JpaRepository<Contact, Long> {

	Optional<Contact> findByIdAndRetired(Long contactId, boolean b);


	
	@Query( value = "SELECT cn.email FROM contact_book_contact c INNER JOIN contact_book cb ON  c.contact_book_id=cb.id "
			+ " INNER JOIN contact cn ON cn.id=c.contact_book_id WHERE "
			+ " cn.email=:email AND cn.retired=0 AND cb.retired=0 AND c.contact_book_id=:bookId ", 
			  nativeQuery = true)
	Collection<Object> findContactInContactBookByEmail(@Param("email") String email,@Param("bookId") Long bookId);
	
}
