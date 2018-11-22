package com.contact.api.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.contact.api.payload.ContactRequest;

import lombok.Data;

@Data
@Entity
@Table(name = "contact")
public class Contact extends BaseDomain {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 40)
    private String name;

    private Long contactbookid;
     
    @NotBlank
    @Size(max = 40)
    private String email;

    

    private Long number;
    
    
    public Contact() {

    }

	public Contact(ContactRequest contactRequest) {
		this.name=contactRequest.getName();
		this.email=contactRequest.getEmail();
		this.number=contactRequest.getNumber();
	}




}