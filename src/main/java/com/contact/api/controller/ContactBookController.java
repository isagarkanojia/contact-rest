package com.contact.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.contact.api.exception.BookNotFoundException;
import com.contact.api.model.ContactBook;
import com.contact.api.payload.ContactBookRequest;
import com.contact.api.payload.ErrorDto;
import com.contact.api.response.Response;
import com.contact.api.response.SingleResponse;
import com.contact.api.service.ContactBookService;
import com.contact.api.util.AuthUtils;

@RestController
public class ContactBookController {

	@Autowired
	private ContactBookService contactBookService;

	@PostMapping("/book")
	public SingleResponse<ContactBook> saveBook(@RequestBody ContactBookRequest contactBookRequest,
			@RequestHeader(value = "Authorization") String authHeader) {
		SingleResponse<ContactBook> resp = new SingleResponse<>();
		Long userId = AuthUtils.getUserIdFromToken(authHeader);
		ContactBook contactBook = new ContactBook(contactBookRequest);
		contactBookService.saveBook(contactBook, userId);
		resp.setSuccess(true);
		return resp;
	}

	@GetMapping("/books")
	public Response<ContactBook> getBooks(@RequestHeader(value = "Authorization") String authHeader) {
		Response<ContactBook> resp = new Response<>();
		Long userId = AuthUtils.getUserIdFromToken(authHeader);
		List<ContactBook> result = contactBookService.getBooks(userId);
		resp.setData(result);
		resp.setSuccess(true);
		return resp;
	}
	
	@DeleteMapping("/book/{bookId}")
	public SingleResponse<ContactBook> deleteBook(@PathVariable("bookId") Long bookId) {
		SingleResponse<ContactBook> resp = new SingleResponse<>();
		try {
			contactBookService.deleteBook(bookId);
		} catch (BookNotFoundException e) {
			resp.setError(new ErrorDto("600", e.getMessage()));
			resp.setSuccess(false);
		} catch (Exception e) {
			resp.setError(new ErrorDto("600", e.getMessage()));
			resp.setSuccess(false);
		}
		resp.setSuccess(true);
		return resp;
	}
}
