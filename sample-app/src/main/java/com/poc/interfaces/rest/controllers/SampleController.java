package com.poc.interfaces.rest.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.poc.interfaces.rest.models.Message;

@RestController
@RequestMapping("v1/resources")
public class SampleController {

	@PreAuthorize("hasRole('user_a')")
	@PostMapping
	public ResponseEntity<Void> post(@RequestBody Message message) {
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}
	
}
