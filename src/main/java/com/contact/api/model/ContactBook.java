package com.contact.api.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Where;

import com.contact.api.payload.ContactBookRequest;

import lombok.Data;

@Data
@Entity
@Table(name = "contact_book")
public class ContactBook extends BaseDomain {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 40)
    private String name;
    
    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
    @JoinTable(name = "contact_book_contact",
            joinColumns = @JoinColumn(name = "contact_book_id"),
            inverseJoinColumns = @JoinColumn(name = "contact_id"))
    @Where(clause = "retired=0")
    private Set<Contact> contacts = new HashSet<>();
    
    
    public ContactBook() {

    }


	public ContactBook(@Valid ContactBookRequest contactBookRequest) {
		this.name=contactBookRequest.getName();
	}


}