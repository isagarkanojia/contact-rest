package com.contact.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ContactNotFoundException extends Exception {
    private Long contactId;
    
    public ContactNotFoundException( Long contactId) {
        super(String.format("Contact not found with id: %s", contactId));
        this.contactId=contactId;
    }

	/**
	 * @return the contactId
	 */
	public Long getContactId() {
		return contactId;
	}

	/**
	 * @param contactId the contactId to set
	 */
	public void setContactId(Long contactId) {
		this.contactId = contactId;
	}
}
