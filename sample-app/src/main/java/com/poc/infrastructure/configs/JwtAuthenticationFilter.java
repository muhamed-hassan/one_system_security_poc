package com.poc.infrastructure.configs;

import static com.poc.infrastructure.configs.Constants.*;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

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
        var username = request.getParameter(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_USERNAME_KEY);
        var password = request.getParameter(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_PASSWORD_KEY);
        return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, 
    		FilterChain filterChain, Authentication authentication) {
    	var deviceTypeHeader = request.getHeader(DEVICE_TYPE_HEADER_KEY);    	
    	if (deviceTypeHeader == null) throw new RuntimeException("deviceTypeHeader undefined");
    	
    	var user = (User) authentication.getPrincipal();
    	List<String> roles = null;        
        switch (deviceTypeHeader) {
			case "WEB":
				roles = user.getAuthorities()
				            .stream()
				            .map(GrantedAuthority::getAuthority)
				            .map(authority -> authority.replace("|WEB", ""))
				            .collect(Collectors.toList());
				break;
			case "MOBILE":
				roles = user.getAuthorities()
						    .stream()
						    .map(GrantedAuthority::getAuthority)
						    .map(authority -> authority.replace("|MOBILE", ""))
						    .collect(Collectors.toList());
						break;
			default:
				throw new RuntimeException("deviceTypeHeader undefined");
		}        
               
        var token = Jwts.builder()
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
    		AuthenticationException authenticationException)
                        throws IOException {
    	authenticationResponseHandler.refuseRequest(response, HttpStatus.UNAUTHORIZED.value(), "Invalid credentials");
    }
    
}
