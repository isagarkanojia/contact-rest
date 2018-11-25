package com.contact.api.test;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.contact.api.controller.ContactConroller;
import com.contact.api.model.Contact;
import com.contact.api.service.ContactService;

@RunWith(SpringRunner.class)
@WebMvcTest(ContactConroller.class)
public class WebMockTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private ContactService service;

	@Test
	public void deletedShouldReturnDeletedContact() throws Exception {
		when(service.deleteContact(6L)).thenReturn(new Contact());
		this.mockMvc.perform(get("/contact/" + 6L)).andDo(print()).andExpect(status().isOk())
				.andExpect(content().string(containsString("Hello Mock")));
	}

	@Test
	public void shouldListAllContacts() throws Exception {
		this.mockMvc.perform(get("/contacts")).andDo(print()).andExpect(status().isOk())
				.andExpect(content().string(containsString("Hello Mock")));
	}
}