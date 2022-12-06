package com.poc.infrastructure.configs;

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

import com.poc.domain.services.UserService;

import io.jsonwebtoken.Jwts;

public class JwtVerificationFilter extends OncePerRequestFilter {

	private final UserService userService;

    public JwtVerificationFilter(UserService userService) {
        this.userService = userService;
    }
    
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		var systemSecurityConfiguration = userService.loadJwtConfigs(); // cache it in memory level later
		var token = request.getHeader(Constants.AUTHORIZATION_HEADER_KEY);
        if (token != null && !token.isBlank() && token.startsWith(Constants.AUTHORIZATION_HEADER_VALUE_PREFIX)) {
            var parsedToken = Jwts.parserBuilder()
	                                .setSigningKey(systemSecurityConfiguration.getJwtSecret().getBytes())
	                                .build()
	                                .parseClaimsJws(token.replace(Constants.AUTHORIZATION_HEADER_VALUE_PREFIX, ""));
            
            var username = parsedToken.getBody().getSubject();         
            var roles = (List<String>) parsedToken.getBody().get("rol");            
            var authorities = roles.stream()
				            		.map(SimpleGrantedAuthority::new)
				            		.collect(Collectors.toList());
            
            if (username.isBlank() && authorities.isEmpty()) return;
            SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(username, null, authorities));
        }
        filterChain.doFilter(request, response);
	}

}
