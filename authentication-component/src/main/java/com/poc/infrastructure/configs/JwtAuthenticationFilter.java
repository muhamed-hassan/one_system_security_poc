package com.poc.infrastructure.configs;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.poc.infrastructure.models.Credentials;
import com.poc.persistence.entities.CustomGrantedAuthority;
import com.poc.persistence.entities.SystemSecurityConfiguration;
import com.poc.persistence.entities.User;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	
	private final SystemSecurityConfiguration systemSecurityConfiguration;
	
	private final AuthenticationManager authenticationManager;
	
	private final AuthenticationResponseHandler authenticationResponseHandler;
		    
    public JwtAuthenticationFilter(SystemSecurityConfiguration systemSecurityConfiguration, AuthenticationManager authenticationManager, 
    		AuthenticationResponseHandler authenticationResponseHandler) {
        this.systemSecurityConfiguration = systemSecurityConfiguration;
    	this.authenticationManager = authenticationManager;
    	this.authenticationResponseHandler = authenticationResponseHandler;
        setFilterProcessesUrl(systemSecurityConfiguration.getAuthenticationPath());
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
    	
    	String username = null;
        String password = null; 
    	try {
    		
    		InputStream inputStream = request.getInputStream();
			
			ObjectMapper mapper = new ObjectMapper();
			
			Credentials credentials = mapper.readValue(inputStream, Credentials.class);			
			username = credentials.getUsername();
			password = credentials.getPassword();			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
        return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
    }

    /*
     * Extracting the required screens (user's privileges) to be displayed to the user who is about to login based
     * on "Device-Type" request-header whether it is WEB or MOBILE
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, 
    		FilterChain filterChain, Authentication authentication) {
    	
    	String deviceTypeHeader = request.getHeader("Device-Type");    	
    	if (deviceTypeHeader == null) throw new RuntimeException("deviceTypeHeader undefined");
    	
    	User user = (User) authentication.getPrincipal();
    	Collection<? extends GrantedAuthority> authorities = user.getAuthorities();
    	Iterator<? extends GrantedAuthority> iterator = authorities.iterator();	
    	List<String> roles = new ArrayList<String>();        	
        switch (deviceTypeHeader) {
        	// Collecting WEB screens
			case "WEB":				
				while (iterator.hasNext()) {					
					CustomGrantedAuthority currentElement = (CustomGrantedAuthority) iterator.next();		
					if (currentElement.getUiScreen().getScreenType().getType().equals("WEB")) {
						String role = currentElement.getAuthority().replace("|WEB", "");					
						roles.add(role);
					}					
				}				
				break;
			// Collecting MOBILE screens
			case "MOBILE":				
				while (iterator.hasNext()) {					
					CustomGrantedAuthority currentElement = (CustomGrantedAuthority) iterator.next();					
					if (currentElement.getUiScreen().getScreenType().getType().equals("MOBILE")) {
						String role = currentElement.getAuthority().replace("|MOBILE", "");					
						roles.add(role);
					}
				}	
				break;
			default:
				throw new RuntimeException("deviceTypeHeader undefined");
		}        
               
        String token = Jwts.builder()
                        .signWith(Keys.hmacShaKeyFor(systemSecurityConfiguration.getJwtSecret().getBytes()))
                        .setHeaderParam("typ", "jwt")
                        .setIssuer("authentication-component")
                        .setSubject(user.getUsername())
                        .setExpiration(new Date(System.currentTimeMillis() + systemSecurityConfiguration.getJwtExpiration()))
                        .claim("rol", roles)
                        .compact();
        response.addHeader("Authorization", "Bearer " + token);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, 
    		AuthenticationException authenticationException) throws IOException {
    	authenticationResponseHandler.refuseRequest(response, HttpStatus.BAD_REQUEST.value(), "Invalid credentials");
    }
    
}
