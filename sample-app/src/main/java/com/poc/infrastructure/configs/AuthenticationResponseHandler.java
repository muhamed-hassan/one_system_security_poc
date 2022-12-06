package com.poc.infrastructure.configs;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationResponseHandler {

	public void refuseRequest(HttpServletResponse response, int httpStatus, String message) throws IOException {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(httpStatus);
        var writer = response.getWriter();
        writer.write(new Error(message).toJson());
        writer.flush();
    }
	
	final class Error {

	    private final String message;

	    public Error(String message) {
	        this.message = message;
	    }

	    public String getMessage() {
	        return message;
	    }

	    public String toJson() {
	        return new StringBuilder("{\"message").append("\":\"").append(message).append("\"}").toString();
	    }

	}
	
}
