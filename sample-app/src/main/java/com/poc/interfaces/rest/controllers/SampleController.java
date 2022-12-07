package com.poc.interfaces.rest.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
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
	
	@PreAuthorize("hasRole('user_a')")
	@GetMapping
	public ResponseEntity<Message> get() {
		var message = new Message();
		message.setBody(SecurityContextHolder.getContext().getAuthentication().getName());		
		return ResponseEntity.status(HttpStatus.OK).body(message);
	}
	
	@PreAuthorize("hasRole('user_b')")
	@DeleteMapping
	public ResponseEntity<Void> delete() {
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}
	
	@PreAuthorize("hasRole('user_b')")
	@PatchMapping
	public ResponseEntity<Void> patch(@RequestBody Message message) {	
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}
	
}
