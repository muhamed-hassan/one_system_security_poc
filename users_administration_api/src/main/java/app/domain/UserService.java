package app.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import app.persistence.entities.SystemActor;
import app.persistence.entities.User;
import app.persistence.repositories.SystemActorRepository;
import app.persistence.repositories.UserRepository;

@Service
public class UserService {
	
	@Autowired
	private SystemActorRepository systemActorRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Transactional	
	public void register(int systemActorId, User user) {
		
		String username = (user.getName().replaceAll("\\ ", "_")).toLowerCase();
		long countOfExistedUsernames = userRepository.getCountOfUsername(username);
		user.setUsername(username + "_" + (countOfExistedUsernames + 1));	
		
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		
		SystemActor systemActor = systemActorRepository.findById(systemActorId);
		user.setSystemActor(systemActor);
		
		user.setAccountNonExpired(true);
		user.setAccountNonLocked(true);
		user.setCredentialsNonExpired(true);
		user.setEnabled(true);
		
		userRepository.save(user);
	}
	
}
