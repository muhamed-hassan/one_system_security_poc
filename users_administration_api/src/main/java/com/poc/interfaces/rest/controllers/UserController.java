package com.poc.interfaces.rest.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.poc.domain.services.UserService;
import com.poc.interfaces.rest.models.NewUser;

@RestController
@RequestMapping("v1/users")
@Validated
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Object> register(@RequestBody NewUser newUser) {
		
		String errorMessage = validate(newUser);
		if (errorMessage != null) {			
			Map<String, String> error = new HashMap<String, String>(1);
			error.put("error", errorMessage);			
			return new ResponseEntity<Object>(error, HttpStatus.BAD_REQUEST);
		}
		
		userService.register(newUser);		
				
		return new ResponseEntity<Object>(HttpStatus.CREATED);
	}
	
	/* ******************************************************************************************************** */
	/* ******************************************************************************************************** */
	
	// https://en.wikipedia.org/wiki/Fail-fast approach is used to report validation errors
	private String validate(NewUser newUser) {
		
		String username = newUser.getUsername();
		if (username == null) {
			return "username is required";
		}
		username = username.trim();
		if (username.length() == 0) {
			return "username is required";
		}		
		
		String password = newUser.getPassword();
		if (password == null) {
			return "password is required";
		}
		password = password.trim();
		if (password.length() == 0) {
			return "password is required";
		}
		if (!password.matches("[a-zA-Z0-9]{8,}")) {
			return "password should be a mixture of at least 8 characters made of lower/upper case letters and digits";
		}
		
		String name = newUser.getName();
		if (name == null) {
			return "name is required";
		}
		name = name.trim();
		if (name.length() == 0) {
			return "name is required";
		}	
		
		String mobile = newUser.getMobile();
		if (mobile == null) {
			return "mobile is required";
		}
		mobile = mobile.trim();
		if (mobile.length() == 0) {
			return "mobile is required";
		}
		if (!mobile.matches("(01)[0-9]{9}")) {
			return "mobile is invalid";
		}
		
		String email = newUser.getEmail();
		if (email == null) {
			return "email is required";
		}
		email = email.trim();
		if (email.length() == 0) {
			return "email is required";
		}
		if (!email.matches("[a-zA-Z0-9_\\.]+(\\@)[a-zA-Z0-9]+(\\.)[a-zA-Z]+")) {
			return "email is invalid";
		}
		
		return null;
	}

}
