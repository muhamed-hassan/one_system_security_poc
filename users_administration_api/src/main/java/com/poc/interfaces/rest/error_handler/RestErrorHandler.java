package com.poc.interfaces.rest.error_handler;

import java.util.Collections;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RestErrorHandler {

	private static final String ERROR = "error";
	
	@ExceptionHandler
	public ResponseEntity<Map<String, String>> handleExceptions(Exception exception) {
		var message = exception.getMessage() == null ? 
				"Unable to process this request." : exception.getMessage();
		var error = Collections.singletonMap(ERROR, message);
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
	}
	
}
