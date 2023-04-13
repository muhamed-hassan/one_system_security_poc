package com.poc.interfaces.rest.controllers;

import org.springframework.stereotype.Component;

import com.poc.interfaces.rest.models.NewUser;

//https://en.wikipedia.org/wiki/Fail-fast approach is used to report validation errors
@Component
public class Validator {

	public String validate(NewUser newUser) {
		
		String password = newUser.getPassword();
		if (password == null) {
			return "password is required";
		}
		password = password.trim();
		if (!password.matches("[a-zA-Z0-9]{8,}")) {
			return "password should be a mixture of at least 8 characters made of lower/upper case letters and digits";
		}
		
		String name = newUser.getName();
		if (name == null) {
			return "name is required";
		}
		name = name.trim();
		if (!name.matches("[a-zA-Z\\ ]{5,100}")) {
			return "name contains invalid characters and shall contain letters only with maximum of 100";
		}	
		
		String mobile = newUser.getMobile();
		if (mobile == null) {
			return "mobile is required";
		}
		mobile = mobile.trim();
		if (!mobile.matches("(01)[0-9]{9}")) {
			return "mobile contains invalid characters and shall be in this form 01123456789";
		}
		
		String email = newUser.getEmail();
		if (email == null) {
			return "email is required";
		}
		email = email.trim();
		if (!email.matches("[a-zA-Z0-9_\\.]+(\\@)[a-zA-Z0-9]+(\\.)[a-zA-Z]+")) {
			return "email format is invalid";
		}
		
		return null;
	}

}
