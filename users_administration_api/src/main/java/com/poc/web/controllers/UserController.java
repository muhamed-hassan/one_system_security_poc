package com.poc.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.poc.domain.UserService;
import com.poc.web.models.NewUser;

@RestController
@RequestMapping("v1/users")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private Validator validator; 
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Object> register(@RequestBody NewUser newUser) {
		
		validator.validate(newUser);
		
		userService.register(newUser);		
				
		return new ResponseEntity<Object>(HttpStatus.CREATED);
	}

}
