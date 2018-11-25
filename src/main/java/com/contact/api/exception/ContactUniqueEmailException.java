package com.contact.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ContactUniqueEmailException extends Exception {
    private String email;
    
    public ContactUniqueEmailException(String email) {
        super(String.format("Contact with email:  %s  already exists", email));
        this.email=email;
    }

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}


}
