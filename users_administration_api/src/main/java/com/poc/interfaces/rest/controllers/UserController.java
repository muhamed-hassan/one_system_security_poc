package com.poc.interfaces.rest.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.poc.domain.services.UserService;
import com.poc.interfaces.rest.models.NewUser;

@RestController
@RequestMapping("v1/users")
@Validated
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@PostMapping
	public ResponseEntity<Void> register(@Valid @RequestBody NewUser newUser) {
		
		userService.register(newUser);		
				
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

}
