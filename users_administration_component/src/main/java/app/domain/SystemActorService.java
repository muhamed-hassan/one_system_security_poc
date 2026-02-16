package app.domain;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.persistence.entities.SystemActor;
import app.persistence.repositories.SystemActorRepository;

@Service
public class SystemActorService {
	
	@Autowired
	private SystemActorRepository systemActorRepository;
	
	public List<SystemActor> getAll() {
		
		List<SystemActor> systemActors = systemActorRepository.findAll();
		
		return systemActors;
	}

}
