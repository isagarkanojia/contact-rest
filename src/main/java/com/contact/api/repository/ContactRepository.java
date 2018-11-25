package com.contact.api.repository;

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
		
	List<Contact> findByEmailAndContactbookid(String email, Long bookId);

	List<Contact> findByContactbookidAndRetired(Long bookId, boolean b);

	Page<Contact> findByContactbookidAndRetired(Long bookId, boolean b, Pageable createPageRequest);

	@Query("select c from Contact c where c.contactbookid=:bookId AND c.retired=:b AND (c.name like %:search%  OR c.email like %:search%  ) ")
	Page<Contact> findByContactbookidAndRetiredAndNameOrEmailContainingIgnoreCase(@Param("bookId") 
	Long bookId, @Param("b") boolean b, @Param("search") String search,
			Pageable createPageRequest);

	List<Contact> findByEmailAndContactbookidAndRetired(String email, Long bookId, boolean b);

			
}
