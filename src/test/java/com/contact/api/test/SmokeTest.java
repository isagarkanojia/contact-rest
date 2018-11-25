package com.contact.api.test;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.contact.api.controller.AuthController;
import com.contact.api.controller.ContactConroller;
import com.contact.api.controller.UserController;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SmokeTest {

	@Autowired
	private ContactConroller contactController;

	@Autowired
	private ContactConroller contactBookcontroller;

	@Autowired
	private UserController userController;

	@Autowired
	private AuthController authController;

	@Test
	public void contexLoads() throws Exception {
		assertThat(contactController).isNotNull();
		assertThat(contactBookcontroller).isNotNull();
		assertThat(userController).isNotNull();
		assertThat(authController).isNotNull();

	}
}