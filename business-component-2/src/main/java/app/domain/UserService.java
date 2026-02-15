package app.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import app.persistence.entities.SystemSecurityConfiguration;
import app.persistence.repositories.SystemSecurityConfigurationRepository;
import app.persistence.repositories.UserRepository;

@Service
public class UserService implements UserDetailsService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private SystemSecurityConfigurationRepository systemSecurityConfigurationRepository;

	@Override
	public UserDetails loadUserByUsername(String username) 
			throws UsernameNotFoundException {	
		
		UserDetails user = userRepository.findByUsername(username);
		
		return user;
	}
	
	public SystemSecurityConfiguration getSystemSecurityConfiguration() {
		
		SystemSecurityConfiguration systemSecurityConfiguration = systemSecurityConfigurationRepository.findById(1);
		
		return systemSecurityConfiguration;
	}

}
