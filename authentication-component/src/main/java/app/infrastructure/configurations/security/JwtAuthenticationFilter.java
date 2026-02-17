package app.infrastructure.configurations.security;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

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

import app.persistence.entities.CustomGrantedAuthority;
import app.persistence.entities.SystemSecurityConfiguration;
import app.persistence.entities.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	
	private SystemSecurityConfiguration systemSecurityConfiguration;
	
	private AuthenticationManager authenticationManager;
	
	private AuthenticationResponseHandler authenticationResponseHandler;
		    
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

    // Extracting the required screens (user's privileges) to be displayed to the user who is about to login
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, 
    		FilterChain filterChain, Authentication authentication) {
    	
    	User user = (User) authentication.getPrincipal();
    	Collection<? extends GrantedAuthority> authorities = user.getAuthorities();
    	Iterator<? extends GrantedAuthority> iterator = authorities.iterator();	
    	ArrayList<String> roles = new ArrayList<String>(); 
    	while (iterator.hasNext()) {					
			CustomGrantedAuthority currentElement = (CustomGrantedAuthority) iterator.next();		
			String role = currentElement.getAuthority();					
			roles.add(role);				
		}	    
               
        String token = Jwts.builder()
                        .signWith(Keys.hmacShaKeyFor(systemSecurityConfiguration.getJwtSecret().getBytes()))
                        .setHeaderParam("typ", "jwt")
                        .setIssuer(systemSecurityConfiguration.getAutomatedSystemName())
                        .setSubject(user.getUsername())
                        .setIssuedAt(new Date())
                        .setExpiration(new Date(System.currentTimeMillis() + systemSecurityConfiguration.getJwtExpiration()))
                        .claim("rol", roles)
                        .compact();
        response.addHeader("Authorization", "Bearer " + token);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, 
    		AuthenticationException authenticationException) 
    				throws IOException {
    	authenticationResponseHandler.refuseRequest(response, HttpStatus.BAD_REQUEST.value(), "Invalid credentials");
    }
    
}
