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
	
	
	@Query( value = "SELECT c.id,c.name,c.email,c.number,c.created_date,c.modified_date,c.created_by,c.modified_by,c.retired "
			+" FROM contact c INNER JOIN contact_book_contact cn ON c.id=cn.contact_id "
			+" INNER JOIN contact_book cb ON cb.id=cn.contact_book_id Where "
			+" c.retired=0 AND cb.retired=0 AND cn.contact_book_id=:bookId AND ( c.email LIKE %:search% OR c.name LIKE %:search% )", 
			  nativeQuery = true)
	Collection<Contact> searchInContactBookByEmailAndName(@Param("search") String search,@Param("bookId") Long bookId);

			
}
