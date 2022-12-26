package com.poc.domain.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.poc.persistence.entities.SystemSecurityConfiguration;
import com.poc.persistence.repositories.SystemSecurityConfigurationRepository;
import com.poc.persistence.repositories.UserRepository;

@Service
public class UserService implements UserDetailsService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private SystemSecurityConfigurationRepository systemSecurityConfigurationRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {	
		
		UserDetails user = userRepository.findByUsername(username);
		if (user == null) {
			throw new UsernameNotFoundException("This user name " + username + " does not exist");
		}
		
		return user;
	}
	
	public SystemSecurityConfiguration loadJwtConfigs() {
		
		SystemSecurityConfiguration configuration = systemSecurityConfigurationRepository.findOne(1);
		if (configuration == null) {
			throw new RuntimeException("Failed to load Jwt Configs => please contact the System Admin");
		}
		
		return configuration;
	}

}
