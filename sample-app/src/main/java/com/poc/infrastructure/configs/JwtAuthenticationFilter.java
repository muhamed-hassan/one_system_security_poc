package com.poc.infrastructure.configs;

import static com.poc.infrastructure.configs.Constants.AUTHENTICATION_URI;
import static com.poc.infrastructure.configs.Constants.AUTHORIZATION_HEADER_KEY;
import static com.poc.infrastructure.configs.Constants.AUTHORIZATION_HEADER_VALUE_PREFIX;
import static com.poc.infrastructure.configs.Constants.DEVICE_TYPE_HEADER_KEY;
import static com.poc.infrastructure.configs.Constants.JWT_HEADER_TYPE;
import static com.poc.infrastructure.configs.Constants.JWT_PAYLOAD_CLAIM;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.poc.persistence.entities.SystemSecurityConfiguration;
import com.poc.persistence.entities.User;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	private final SystemSecurityConfiguration systemSecurityConfiguration;
	
	private final AuthenticationManager authenticationManager;
	
	private final AuthenticationResponseHandler authenticationResponseHandler;	
	
	@Value("${spring.application.name}")
    private String apiName;
	    
    public JwtAuthenticationFilter(SystemSecurityConfiguration systemSecurityConfiguration, AuthenticationManager authenticationManager, 
    		AuthenticationResponseHandler authenticationResponseHandler) {
        this.systemSecurityConfiguration = systemSecurityConfiguration;
    	this.authenticationManager = authenticationManager;
    	this.authenticationResponseHandler = authenticationResponseHandler;
        setFilterProcessesUrl(AUTHENTICATION_URI);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
    }

    /*
     * extracting the required screens to be displayed to the user who is about to login based
     * on "Device-Type" request-header whether it is WEB or MOBILE
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, 
    		FilterChain filterChain, Authentication authentication) {
    	
    	String deviceTypeHeader = request.getHeader(DEVICE_TYPE_HEADER_KEY);    	
    	if (deviceTypeHeader == null) throw new RuntimeException("deviceTypeHeader undefined");
    	
    	User user = (User) authentication.getPrincipal();
    	Collection<? extends GrantedAuthority> authorities = user.getAuthorities();
    	Iterator<? extends GrantedAuthority> iterator = authorities.iterator();	
    	List<String> roles = new ArrayList<String>();        
        switch (deviceTypeHeader) {
        	// Collecting WEB screens
			case "WEB":				
				while (iterator.hasNext()) {					
					GrantedAuthority currentElement = iterator.next();					
					String role = currentElement.getAuthority().replace("|WEB", "");					
					roles.add(role);
				}				
				break;
			// Collecting MOBILE screens
			case "MOBILE":				
				while (iterator.hasNext()) {					
					GrantedAuthority currentElement = iterator.next();					
					String role = currentElement.getAuthority().replace("|MOBILE", "");					
					roles.add(role);
				}	
				break;
			default:
				throw new RuntimeException("deviceTypeHeader undefined");
		}        
               
        String token = Jwts.builder()
                        .signWith(Keys.hmacShaKeyFor(systemSecurityConfiguration.getJwtSecret().getBytes()))
                        .setHeaderParam(JWT_HEADER_TYPE, "jwt")
                        .setIssuer(apiName)
                        .setSubject(user.getUsername())
                        .setExpiration(new Date(System.currentTimeMillis() + systemSecurityConfiguration.getJwtExpiration()))
                        .claim(JWT_PAYLOAD_CLAIM, roles)
                        .compact();
        response.addHeader(AUTHORIZATION_HEADER_KEY, AUTHORIZATION_HEADER_VALUE_PREFIX + token);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, 
    		AuthenticationException authenticationException) throws IOException {
    	authenticationResponseHandler.refuseRequest(response, HttpStatus.UNAUTHORIZED.value(), "Invalid credentials");
    }
    
}
