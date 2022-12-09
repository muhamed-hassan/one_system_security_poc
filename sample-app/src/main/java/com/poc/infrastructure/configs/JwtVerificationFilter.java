package com.poc.infrastructure.configs;

import static com.poc.infrastructure.configs.Constants.*;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.poc.persistence.entities.SystemSecurityConfiguration;

import io.jsonwebtoken.Jwts;

public class JwtVerificationFilter extends OncePerRequestFilter {
	
	private final SystemSecurityConfiguration systemSecurityConfiguration;

    public JwtVerificationFilter(SystemSecurityConfiguration systemSecurityConfiguration) {
        this.systemSecurityConfiguration = systemSecurityConfiguration;
    }
    
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		var token = request.getHeader(AUTHORIZATION_HEADER_KEY);
        if (token != null && !token.isBlank() && token.startsWith(AUTHORIZATION_HEADER_VALUE_PREFIX)) {
            var parsedToken = Jwts.parserBuilder()
	                                .setSigningKey(systemSecurityConfiguration.getJwtSecret().getBytes())
	                                .build()
	                                .parseClaimsJws(token.replace(AUTHORIZATION_HEADER_VALUE_PREFIX, ""));
            
            var username = parsedToken.getBody().getSubject();         
            var roles = (List<String>) parsedToken.getBody().get(JWT_PAYLOAD_CLAIM);            
            var authorities = roles.stream()
				            		.map(SimpleGrantedAuthority::new)
				            		.collect(Collectors.toList());
            
            if (username.isBlank() && authorities.isEmpty()) return;
            SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(username, null, authorities));
        }
        filterChain.doFilter(request, response);
	}

}
