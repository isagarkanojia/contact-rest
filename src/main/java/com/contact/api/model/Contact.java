package com.contact.api.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.hibernate.annotations.NaturalId;

import com.contact.api.payload.ContactRequest;

import lombok.Data;

@Data
@Entity
@Table(name = "contact", uniqueConstraints = {
        @UniqueConstraint(columnNames = {
            "email"
        })
})
public class Contact extends BaseDomain {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 40)
    private String name;

    @NaturalId
    @NotBlank
    @Size(max = 40)
    @Email
    private String email;

    
    @Size(max = 100)
    private String number;
    
    
    public Contact() {

    }

	public Contact(ContactRequest contactRequest) {
		this.name=contactRequest.getName();
		this.email=contactRequest.getEmail();
		this.number=contactRequest.getNumber();
	}


}