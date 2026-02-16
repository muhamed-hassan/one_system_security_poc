package app.domain;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import app.persistence.entities.CustomGrantedAuthority;
import app.persistence.entities.SystemSecurityConfiguration;
import app.persistence.entities.User;
import app.persistence.repositories.CustomGrantedAuthorityRepository;
import app.persistence.repositories.SystemSecurityConfigurationRepository;
import app.persistence.repositories.UserRepository;

@Service
public class UserService implements UserDetailsService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private SystemSecurityConfigurationRepository systemSecurityConfigurationRepository;
	
	@Autowired
	private CustomGrantedAuthorityRepository customGrantedAuthorityRepository;

	@Override
	public UserDetails loadUserByUsername(String username) 
			throws UsernameNotFoundException {	
		
		User user = userRepository.findByUsername(username);
		
		List<CustomGrantedAuthority> authorities = customGrantedAuthorityRepository.findAll(user.getSystemActor().getId());
		user.setAuthorities(authorities);
		
		return user;
	}
	
	public SystemSecurityConfiguration getSystemSecurityConfiguration() {
		
		SystemSecurityConfiguration systemSecurityConfiguration = systemSecurityConfigurationRepository.findById(1);
		
		return systemSecurityConfiguration;
	}

}
