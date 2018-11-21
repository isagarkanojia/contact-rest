package com.contact.api.controller;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.util.Base64;
import java.util.Collections;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.contact.api.exception.AppException;
import com.contact.api.model.Mail;
import com.contact.api.model.PasswordResetToken;
import com.contact.api.model.Role;
import com.contact.api.model.RoleName;
import com.contact.api.model.User;
import com.contact.api.payload.JwtAuthenticationResponse;
import com.contact.api.payload.LoginRequest;
import com.contact.api.payload.PasswordResetDto;
import com.contact.api.payload.SignUpRequest;
import com.contact.api.repository.PasswordResetTokenRepository;
import com.contact.api.repository.RoleRepository;
import com.contact.api.repository.UserRepository;
import com.contact.api.response.SingleResponse;
import com.contact.api.security.JwtTokenProvider;
import com.contact.api.service.MyEmailService;
import com.contact.api.service.UserService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

	@Autowired
	PasswordResetTokenRepository tokenRepository;

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	UserRepository userRepository;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	JwtTokenProvider tokenProvider;

	@Autowired
	MyEmailService myEmailService;

	@Autowired
	UserService userService;

	@Autowired
	MyEmailService mailService;

	@PostMapping("/signin")
	public SingleResponse<JwtAuthenticationResponse> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
		SingleResponse<JwtAuthenticationResponse> resp = new SingleResponse<>();
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsernameOrEmail(), loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);

		User user = userRepository
				.findByUsernameOrEmail(loginRequest.getUsernameOrEmail(), loginRequest.getUsernameOrEmail()).get();

		String jwt = tokenProvider.generateToken(authentication);
		JwtAuthenticationResponse j = new JwtAuthenticationResponse(jwt);
		j.setUser(user);
		resp.setData(j);
		resp.setSuccess(true);
		return resp;
	}

	@PostMapping("/signup")
	public SingleResponse<String> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
		SingleResponse<String> resp = new SingleResponse<>();
		if (userRepository.existsByUsername(signUpRequest.getUsername())) {
			resp.setData("Username is already taken!");
			resp.setStatusCode(HttpStatus.BAD_REQUEST.toString());
			return resp;
		}

		if (userRepository.existsByEmail(signUpRequest.getEmail())) {
			resp.setData("Email Address already in use!");
			resp.setStatusCode(HttpStatus.BAD_REQUEST.toString());
			return resp;
		}

		// Creating user's account
		User user = new User(signUpRequest);

		user.setPassword(passwordEncoder.encode(user.getPassword()));

		Role userRole = roleRepository.findByName(RoleName.ROLE_USER)
				.orElseThrow(() -> new AppException("User Role not set."));

		user.setRoles(Collections.singleton(userRole));

		User result = userRepository.save(user);

		URI location = ServletUriComponentsBuilder.fromCurrentContextPath().path("/users/{username}")
				.buildAndExpand(result.getUsername()).toUri();

		resp.setData("User registered successfully");
		resp.setStatusCode("200");
		return resp;
	}

	@GetMapping(value = "/logout")
	public SingleResponse<String> logout(HttpServletRequest request, HttpServletResponse response) {
		SingleResponse<String> result = new SingleResponse<String>();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null) {
			String username = auth.getName();
			new SecurityContextLogoutHandler().logout(request, response, auth);
		}
		result.setSuccess(true);
		result.setStatusCode("200");
		result.setData("You are successfully logged out");
		return result;
	}

	@PostMapping(value = "/resetPassword")
	public SingleResponse<String> handlePasswordReset(@RequestBody PasswordResetDto passwordResetDTO) {

		SingleResponse<String> result = new SingleResponse<String>();
		PasswordResetToken token = tokenRepository.findByToken(passwordResetDTO.getToken());
		if (token != null) {

			User user = token.getUser();
			String updatedPassword = "";
			try {
				updatedPassword = Base64.getEncoder().encodeToString(passwordResetDTO.getPassword().getBytes("utf-8"));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			userService.updatePassword(updatedPassword, user.getId());
			tokenRepository.delete(token);
		}
		result.setData("Password reset successfully");
		result.setStatusCode("200");
		result.setSuccess(true);
		return result;
	}

	@GetMapping(value = "/resetPassword/{email}")
	public SingleResponse<String> sendResetPasswordMail(HttpServletRequest request,@PathVariable("email") String email) {

		SingleResponse<String> result = new SingleResponse<>();

		User user = userRepository.findUserByEmail(email);
		if (user == null) {
			result.setSuccess(false);
			result.setStatusCode("412");
			result.setData("Email ID does not exist in out database, please enter correct email id.");
			return result;
		} else {
			PasswordResetToken token = new PasswordResetToken();
			token.setToken(UUID.randomUUID().toString());
			token.setUser(user);
			token.setExpiryDate(72);
			tokenRepository.save(token);

			Mail mail = new Mail();
			mail.setFrom("raj.singh.dit8@gmail.com");
			mail.setTo(user.getEmail());
			mail.setSubject("Password reset request");

			String url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
					+ "/SecuredWeb/service/reset-password?token=" + token.getToken();
			System.err.println(url);
			mail.setBody(url);

			mailService.sendEmail(mail);

			result.setData(
					"If you are registered, then an email with password reset link would have been sent to your email id.");
			result.setStatusCode("200");
			result.setSuccess(true);
			return result;
		}
	}
}
