package com.poc.infrastructure.configs;

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

import com.poc.domain.services.UserService;
import com.poc.persistence.entities.User;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	private final UserService userService;
	
	private final AuthenticationManager authenticationManager;
	
	private final AuthenticationResponseHandler authenticationResponseHandler;
	
	@Value("${spring.application.name}")
    private String apiName;
	    
    public JwtAuthenticationFilter(UserService userService, AuthenticationManager authenticationManager, 
    		AuthenticationResponseHandler authenticationResponseHandler) {
        this.userService = userService;
    	this.authenticationManager = authenticationManager;
    	this.authenticationResponseHandler = authenticationResponseHandler;
        setFilterProcessesUrl("/authenticate");
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
    	var deviceTypeHeader = request.getHeader("Device-Type");    	
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
        
        var systemSecurityConfiguration = userService.loadJwtConfigs(); // cache it in memory level later
        var token = Jwts.builder()
                        .signWith(Keys.hmacShaKeyFor(systemSecurityConfiguration.getJwtSecret().getBytes()))
                        .setHeaderParam("typ", "jwt")
                        .setIssuer(apiName)
                        .setSubject(user.getUsername())
                        .setExpiration(new Date(System.currentTimeMillis() + systemSecurityConfiguration.getJwtExpiration()))
                        .claim("rol", roles)
                        .compact();
        response.addHeader(Constants.AUTHORIZATION_HEADER_KEY, Constants.AUTHORIZATION_HEADER_VALUE_PREFIX + token);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, 
    		AuthenticationException authenticationException)
                        throws IOException {
    	authenticationResponseHandler.refuseRequest(response, HttpStatus.UNAUTHORIZED.value(), "Invalid credentials");
    }
    
}
