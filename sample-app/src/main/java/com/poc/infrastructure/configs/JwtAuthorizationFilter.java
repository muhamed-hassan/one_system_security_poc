package com.poc.infrastructure.configs;

import static com.poc.infrastructure.configs.Constants.AUTHORIZATION_HEADER_KEY;
import static com.poc.infrastructure.configs.Constants.AUTHORIZATION_HEADER_VALUE_PREFIX;
import static com.poc.infrastructure.configs.Constants.JWT_PAYLOAD_CLAIM;

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
		
		String token = request.getHeader(AUTHORIZATION_HEADER_KEY);
        if (token != null && token.length() > 0 && token.startsWith(AUTHORIZATION_HEADER_VALUE_PREFIX)) {
        	
        	Jws<Claims> parsedToken = Jwts.parserBuilder()
			                                .setSigningKey(systemSecurityConfiguration.getJwtSecret().getBytes())
			                                .build()
			                                .parseClaimsJws(token.replace(AUTHORIZATION_HEADER_VALUE_PREFIX, ""));
            
            String username = parsedToken.getBody().getSubject();         
            List<String> roles = (List<String>) parsedToken.getBody().get(JWT_PAYLOAD_CLAIM);
            
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
