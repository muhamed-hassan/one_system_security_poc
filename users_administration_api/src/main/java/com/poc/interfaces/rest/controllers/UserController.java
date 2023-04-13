package com.poc.interfaces.rest.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.poc.domain.services.UserService;
import com.poc.interfaces.rest.models.NewUser;

@RestController
@RequestMapping("v1/users")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private Validator validator; 
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Object> register(@RequestBody NewUser newUser) {
		
		String errorMessage = validator.validate(newUser);
		if (errorMessage != null) {			
			Map<String, String> error = new HashMap<String, String>(1);
			error.put("error", errorMessage);			
			return new ResponseEntity<Object>(error, HttpStatus.BAD_REQUEST);
		}
		
		userService.register(newUser);		
				
		return new ResponseEntity<Object>(HttpStatus.CREATED);
	}

}
