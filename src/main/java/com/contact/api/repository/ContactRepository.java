package com.contact.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.contact.api.model.Contact;


@Repository
public interface ContactRepository extends JpaRepository<Contact, Long> {

}
