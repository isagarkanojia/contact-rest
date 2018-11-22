package com.contact.api.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.contact.api.exception.BookNotFoundException;
import com.contact.api.exception.ContactNotFoundException;
import com.contact.api.exception.ContactUniqueEmailException;
import com.contact.api.model.Contact;
import com.contact.api.payload.ContactRequest;
import com.contact.api.payload.ErrorDto;
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
			@PathVariable("bookId") Long bookId, @RequestHeader(value = "Authorization") String authHeader) {
		SingleResponse<Contact> resp = new SingleResponse<>();
		Long userId = AuthUtils.getUserIdFromToken(authHeader);
		Contact contact = new Contact(contactRequest);
		try {
			contact = contactService.saveContact(contact, bookId, userId);
		} catch (ContactUniqueEmailException e) {
			resp.setError(new ErrorDto("600", e.getMessage()));
			resp.setSuccess(false);
		} catch (Exception e) {
			resp.setError(new ErrorDto("600", e.getMessage()));
			resp.setSuccess(false);
		}
		resp.setSuccess(true);
		return resp;
	}

	@GetMapping("/contacts")
	public Response<Contact> getContacts(@PathVariable("bookId") Long bookId) {
		Response<Contact> resp = new Response<>();
		List<Contact> result = null;
		try {
			result = contactService.getContacts(bookId);
		} catch (BookNotFoundException e) {
			resp.setError(new ErrorDto("600", e.getMessage()));
			resp.setSuccess(false);
		} catch (Exception e) {
			resp.setError(new ErrorDto("600", e.getMessage()));
			resp.setSuccess(false);
		}
		resp.setData(result);
		resp.setSuccess(true);
		return resp;
	}

	@PutMapping("/contact/{contactId}")
	public SingleResponse<Contact> updateContact(@PathVariable("contactId") Long contactId,
			@RequestBody ContactRequest contactRequest,@PathVariable("bookId") Long bookId) {
		SingleResponse<Contact> resp = new SingleResponse<>();
		Contact contact = new Contact(contactRequest);
		contact.setId(contactId);
		contact.setContactbookid(bookId);
		Contact result = contactService.updateContact(contact);
		resp.setData(result);
		resp.setSuccess(true);
		return resp;
	}

	@DeleteMapping("/contact/{contactId}")
	public SingleResponse<Contact> deleteContact(@PathVariable("contactId") Long contactId) {
		SingleResponse<Contact> resp = new SingleResponse<>();
		try {
			contactService.deleteContact(contactId);
		} catch (ContactNotFoundException e) {
			resp.setError(new ErrorDto("600", e.getMessage()));
			resp.setSuccess(false);
		} catch (Exception e) {
			resp.setError(new ErrorDto("600", e.getMessage()));
			resp.setSuccess(false);
		}
		resp.setSuccess(true);
		return resp;

	}

	
	@GetMapping("/contacts/{search}")
	public Response<Contact> getContactsBySearch(@PathVariable("bookId") Long bookId,
			@PathVariable("search") String search) {
		Response<Contact> resp = new Response<>();
		ArrayList<Contact> result = null;
		try {
			result = contactService.getContactsBySearch(bookId,search);
		} catch (Exception e) {
			resp.setError(new ErrorDto("600", e.getMessage()));
			resp.setSuccess(false);
		}
		resp.setData(result);
		resp.setSuccess(true);
		return resp;
	}

	
	@GetMapping("/contactsPage")
	public Page<Contact> getContactsPage(@PathVariable("bookId") Long bookId,@RequestParam(defaultValue="0") int page) {
		Response<Contact> resp = new Response<>();
		Page<Contact> result = null;
		try {
			
			result=contactService.getContactsPage(bookId,page);
		
		} catch (Exception e) {
			resp.setError(new ErrorDto("600", e.getMessage()));
			resp.setSuccess(false);
		}
		
		return result;
	}

	
}
