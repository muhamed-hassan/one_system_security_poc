package com.poc.infrastructure.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.poc.domain.UserService;
import com.poc.persistence.entities.SystemSecurityConfiguration;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
    private UserService userService;
	
	@Autowired
	private AuthenticationResponseHandler authenticationResponseHandler;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
	
	@Override
    protected void configure(HttpSecurity http) throws Exception {
		
		SystemSecurityConfiguration systemSecurityConfiguration = userService.loadJwtConfigs();
		
        http.csrf().disable().authorizeRequests()
            .antMatchers(systemSecurityConfiguration.getAuthenticationPath()).permitAll()
            .and()
            .addFilter(new JwtAuthenticationFilter(systemSecurityConfiguration, authenticationManager(), authenticationResponseHandler))
            .exceptionHandling();
    }
	
	@Override
    public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder.userDetailsService(userService)
                                    .passwordEncoder(passwordEncoder);
    }	
	
}
