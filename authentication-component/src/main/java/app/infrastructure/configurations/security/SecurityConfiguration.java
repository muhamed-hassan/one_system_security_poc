package app.infrastructure.configurations.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import app.domain.UserService;
import app.persistence.entities.SystemSecurityConfiguration;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	
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
		
		SystemSecurityConfiguration systemSecurityConfiguration = userService.getSystemSecurityConfiguration();
		
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
