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
        writer.write(formatErrorBody(message));
        writer.flush();
    }
	
	private String formatErrorBody(String message) {
		return new StringBuilder("{\"message").append("\":\"").append(message).append("\"}").toString();
	}
	
}
