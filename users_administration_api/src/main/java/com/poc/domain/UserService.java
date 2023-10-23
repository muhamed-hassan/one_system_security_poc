package com.poc.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.poc.persistence.entities.SystemActor;
import com.poc.persistence.entities.User;
import com.poc.persistence.repositories.SystemActorRepository;
import com.poc.persistence.repositories.UserRepository;
import com.poc.web.models.NewUser;

@Service
public class UserService {
	
	@Autowired
	private SystemActorRepository systemActorRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Transactional	
	public void register(NewUser newUser) {
		
		SystemActor systemActor = systemActorRepository.findOne(newUser.getSystemActorId());
		if (systemActor == null) {
			throw new IllegalArgumentException("Invalid systemActorId");
		}
		
		User user = new User();
				
		String userName = (newUser.getName().replaceAll("\\ ", "_")).toLowerCase();
		long countOfExistedUsernames = userRepository.getCountOfUsername(userName);
		user.setUsername(userName + "_" + (countOfExistedUsernames + 1));		
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
