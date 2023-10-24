package com.poc.web.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.poc.web.models.Message;

@RestController
@RequestMapping("business-component-2/resources")
public class SampleController {
	
	@PreAuthorize("hasRole('user_a')")
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<Message>> getList() {
		Message message1 = new Message();
		message1.setBody("sample text from business-component-2 - xx0");
		
		Message message2 = new Message();
		message2.setBody("sample text from business-component-2 - xx1");
		
		List<Message> responseBody = new ArrayList<Message>();
		responseBody.add(message1);
		responseBody.add(message2);
		
		return new ResponseEntity<List<Message>>(responseBody, HttpStatus.OK);
	}
		
	@PreAuthorize("hasRole('user_b')")
	@RequestMapping(method = RequestMethod.DELETE)
	public ResponseEntity<Object> delete() {
		return new ResponseEntity<Object>(HttpStatus.BAD_GATEWAY);
	}
	
}
