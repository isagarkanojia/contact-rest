package com.contact.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.contact.api.model.ContactBook;


@Repository
public interface ContactBookRepository extends JpaRepository<ContactBook, Long> {

}
