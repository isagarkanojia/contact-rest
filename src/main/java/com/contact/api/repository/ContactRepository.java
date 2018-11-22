package com.contact.api.repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.contact.api.model.Contact;


@Repository
public interface ContactRepository extends JpaRepository<Contact, Long> {

	Optional<Contact> findByIdAndRetired(Long contactId, boolean b);
		
	@Query( value = "SELECT c.id,c.name,c.email,c.number,c.created_date,c.modified_date,c.created_by,c.modified_by,c.retired "
			+" FROM contact c INNER JOIN contact_book_contact cn ON c.id=cn.contact_id "
			+" INNER JOIN contact_book cb ON cb.id=cn.contact_book_id Where "
			+" c.retired=0 AND cb.retired=0 AND cn.contact_book_id=:bookId AND ( c.email LIKE %:search% OR c.name LIKE %:search% )", 
			  nativeQuery = true)
	Collection<Contact> searchInContactBookByEmailAndName(@Param("search") String search,@Param("bookId") Long bookId);

	
	@Query( value = "SELECT c.id,c.name,c.email,c.number,c.created_date,c.modified_date,c.created_by,c.modified_by,c.retired "
			+" FROM contact as c INNER JOIN contact_book_contact cn ON c.id=cn.contact_id "
			+" INNER JOIN contact_book cb ON cb.id=cn.contact_book_id Where "
			+" c.retired=0 AND cb.retired=0 AND cn.contact_book_id=:bookId AND ( c.email LIKE %:search% OR c.name LIKE %:search% )", 
			  nativeQuery = true)
	Page<Contact> searchInContactBookByEmailAndName(@Param("search") String search,@Param("bookId") Long bookId, Pageable pageable);



	List<Contact> findByEmailAndContactbookid(String email, Long bookId);

	List<Contact> findByContactbookidAndRetired(Long bookId, boolean b);

	Page<Contact> findByContactbookidAndRetired(Long bookId, boolean b, Pageable createPageRequest);

			
}
