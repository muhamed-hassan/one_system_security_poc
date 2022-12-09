package com.poc.infrastructure.configs;

import static com.poc.infrastructure.configs.Constants.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.poc.domain.services.UserService;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
    private UserService userService;
	
	@Autowired
	private AuthenticationResponseHandler authenticationResponseHandler;

	@Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
	
	@Override
    protected void configure(HttpSecurity http) throws Exception {
		var systemSecurityConfiguration = userService.loadJwtConfigs();
		
        http.csrf().disable()
    		.httpBasic().disable()
    		.formLogin().disable()
    		.logout().disable()
            .authorizeRequests()
            .antMatchers(AUTHENTICATION_URI).permitAll()
            .anyRequest().authenticated()
            .and()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .exceptionHandling()
            .authenticationEntryPoint((request, response, authenticationException) ->
				authenticationResponseHandler.refuseRequest(response, HttpStatus.FORBIDDEN.value(), INVALID_AUTHORIZATION_TOKEN))
            .and()
            .addFilter(new JwtAuthenticationFilter(systemSecurityConfiguration, authenticationManager(), authenticationResponseHandler))
            .addFilterAfter(new JwtVerificationFilter(systemSecurityConfiguration), JwtAuthenticationFilter.class);
    }
	
	@Override
    public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder.userDetailsService(userService)
                                    .passwordEncoder(passwordEncoder());
    }	
	
}
