package com.poc.infrastructure.configs;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

@Component
public class AuthenticationResponseHandler {

	@Autowired
	private ObjectMapper mapper;
	
	public void refuseRequest(HttpServletResponse response, int httpStatus, String message) throws IOException {
        
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(httpStatus);
        PrintWriter writer = response.getWriter();
        
        ObjectNode errorMessage = mapper.createObjectNode();
        errorMessage.put("message", message);        
        
        writer.write(mapper.writeValueAsString(errorMessage));
        writer.flush();
    }	
	
}
