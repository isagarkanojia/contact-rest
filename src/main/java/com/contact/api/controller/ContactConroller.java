package com.contact.api.controller;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.contact.api.model.Contact;
import com.contact.api.payload.ContactRequest;
import com.contact.api.response.Response;
import com.contact.api.response.SingleResponse;
import com.contact.api.service.ContactService;
import com.contact.api.util.AuthUtils;

@RestController()
@RequestMapping("/book/{bookId}/")
public class ContactConroller {

	@Autowired
	private ContactService contactService;
	
	@PostMapping("/contact")
	public SingleResponse<Contact> saveContact(@RequestBody ContactRequest contactRequest,
			@PathVariable("bookId") Long bookId,@RequestHeader(value = "Authorization") String authHeader) {
		SingleResponse<Contact> resp = new SingleResponse<>();
		Long userId = AuthUtils.getUserIdFromToken(authHeader);
		Contact contact = new Contact(contactRequest);
		Contact result = contactService.saveContact(contact, bookId,userId);
		resp.setData(result);
		resp.setSuccess(true);
		return resp;
	}
	
	@GetMapping("/contacts")
	public Response<Contact> getContacts(@PathVariable("bookId") Long bookId) {
		Response<Contact> resp = new Response<>();
		Set<Contact> result = contactService.getContacts(bookId);
		resp.setData(result);
		resp.setSuccess(true);
		return resp;
	}

	
	
	@PutMapping("/contact/{contactId}")
	public SingleResponse<Contact> updateContact(@PathVariable("contactId") Long contactId
			,@RequestBody ContactRequest contactRequest) {
		SingleResponse<Contact> resp = new SingleResponse<>();
		Contact contact = new Contact(contactRequest);
		contact.setId(contactId);
		Contact result = contactService.updateContact(contact);
		resp.setData(result);
		resp.setSuccess(true);
		return resp;
	}
	
	@DeleteMapping("/contact/{contactId}")
	public SingleResponse<Contact> deleteContact(@PathVariable("contactId") Long contactId) {
		SingleResponse<Contact> resp = new SingleResponse<>();
		Contact result=contactService.deleteContact(contactId);
		resp.setData(result);
		resp.setSuccess(true);
		return resp;
	}
	
	
}
