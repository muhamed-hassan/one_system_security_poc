package com.poc.domain.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.poc.persistence.entities.SystemActor;
import com.poc.persistence.entities.User;
import com.poc.persistence.repositories.SystemActorRepository;
import com.poc.persistence.repositories.UserRepository;
import com.poc.interfaces.rest.models.NewUser;

@Service
public class UserService {
	
	@Autowired
	private SystemActorRepository systemActorRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	public void register(NewUser newUser) {
		
		SystemActor systemActor = systemActorRepository.getOne(newUser.getSystemActorId());
		if (systemActor == null) {
			throw new RuntimeException("Invalid systemActorId");
		}
		
		User user = new User();
		user.setUsername(newUser.getUsername());
		user.setPassword(passwordEncoder.encode(newUser.getPassword()));
		user.setName(newUser.getName());
		user.setEmail(newUser.getEmail());
		user.setMobile(newUser.getMobile());
		user.setSystemActor(systemActor);
		user.setAccountNonExpired(true);
		user.setAccountNonLocked(true);
		user.setCredentialsNonExpired(true);
		user.setEnabled(true);
		
		userRepository.save(user);
	}
	
}
