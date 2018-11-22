package com.contact.api.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.contact.api.payload.ContactBookRequest;

import lombok.Data;

@Data
@Entity
@Table(name = "contactbook")
public class ContactBook extends BaseDomain {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private Long userid;

    @NotBlank
    @Size(max = 40)
    private String name;
    
    
    public ContactBook() {

    }


	public ContactBook(ContactBookRequest contactBookRequest) {
		this.name=contactBookRequest.getName();
	}


}