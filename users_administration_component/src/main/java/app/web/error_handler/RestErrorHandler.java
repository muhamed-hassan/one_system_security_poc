package app.web.error_handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RestErrorHandler {
		
	@ExceptionHandler
    public ResponseEntity<ErrorResponseWithForm> handleWebValidationException(WebValidationException exception) {
		
		ErrorResponseWithForm errorResponse = new ErrorResponseWithForm();
		errorResponse.setError(exception.getErrorInformation());
		
		return new ResponseEntity<ErrorResponseWithForm>(errorResponse, HttpStatus.BAD_REQUEST);		
	}

}
