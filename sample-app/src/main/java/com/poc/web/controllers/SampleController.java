package com.poc.web.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.poc.web.models.Message;

@RestController
@RequestMapping("v1/resources")
public class SampleController {

	@PreAuthorize("hasRole('user_a')")
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Object> post(@RequestBody Message message) {
		return new ResponseEntity<Object>(HttpStatus.NO_CONTENT);
	}
	
	@PreAuthorize("hasRole('user_a')")
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<Message> get() {
		Message message = new Message();
		message.setBody(SecurityContextHolder.getContext().getAuthentication().getName());	
		return new ResponseEntity<Message>(message, HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('user_a')")
	@RequestMapping(method = RequestMethod.GET, value = "forced-exception")
	public ResponseEntity<Message> getWithException() {
		
		if (true) {
			throw new RuntimeException("forced exception => getWithException()");
		}
		
		Message message = new Message();
		message.setBody(SecurityContextHolder.getContext().getAuthentication().getName());	
		return new ResponseEntity<Message>(message, HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('user_b')")
	@RequestMapping(method = RequestMethod.DELETE)
	public ResponseEntity<Object> delete() {
		return new ResponseEntity<Object>(HttpStatus.NO_CONTENT);
	}
	
	@PreAuthorize("hasRole('user_b')")
	@RequestMapping(method = RequestMethod.PUT)
	public ResponseEntity<Object> put(@RequestBody Message message) {	
		return new ResponseEntity<Object>(HttpStatus.NO_CONTENT);
	}
	
}
