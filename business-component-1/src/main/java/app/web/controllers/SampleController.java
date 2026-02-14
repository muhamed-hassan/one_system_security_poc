package app.web.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import app.web.models.Message;

@RestController
@RequestMapping("business-component-1/resources")
public class SampleController {

	@PreAuthorize("hasRole('user_a')")
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Object> post(@RequestBody Message message) {
		
		return new ResponseEntity<Object>(HttpStatus.CREATED);
	}
	
	@PreAuthorize("hasRole('user_a')")
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<Message> get() {
		
		Message message = new Message();
		message.setBody(SecurityContextHolder.getContext().getAuthentication().getName() + " || business-component-1");	
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
