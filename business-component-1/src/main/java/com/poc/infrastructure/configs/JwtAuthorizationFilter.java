package com.poc.infrastructure.configs;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.poc.persistence.entities.SystemSecurityConfiguration;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;

public class JwtAuthorizationFilter extends OncePerRequestFilter {
		
	private final SystemSecurityConfiguration systemSecurityConfiguration;

    public JwtAuthorizationFilter(SystemSecurityConfiguration systemSecurityConfiguration) {
        this.systemSecurityConfiguration = systemSecurityConfiguration;
    }
    
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
				
		String authorizationHeaderValue = request.getHeader("Authorization");
        if (authorizationHeaderValue != null && authorizationHeaderValue.length() > 0 && authorizationHeaderValue.startsWith("Bearer ")) {
        	
        	String token = authorizationHeaderValue.replace("Bearer ", "");
        	Jws<Claims> parsedToken = Jwts.parserBuilder()
			                                .setSigningKey(systemSecurityConfiguration.getJwtSecret().getBytes())
			                                .build()
			                                .parseClaimsJws(token);
            
            String username = parsedToken.getBody().getSubject();         
            List<String> roles = (List<String>) parsedToken.getBody().get("rol");
            
            List<SimpleGrantedAuthority> authorities = new ArrayList<SimpleGrantedAuthority>();
            Iterator<String> iterator = roles.iterator();	
            while (iterator.hasNext()) {					
            	String currentElement = iterator.next();					
            	SimpleGrantedAuthority authority = new SimpleGrantedAuthority(currentElement);					
            	authorities.add(authority);
			}
            
            if (username.length() == 0 && authorities.isEmpty()) return;
            
            SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(username, null, authorities));
        }
        
        filterChain.doFilter(request, response);
	}

}
