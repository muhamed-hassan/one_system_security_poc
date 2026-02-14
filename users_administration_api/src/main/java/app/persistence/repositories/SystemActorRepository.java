package app.persistence.repositories;

import org.springframework.stereotype.Repository;

import app.persistence.entities.SystemActor;

@Repository
public class SystemActorRepository extends BaseRepository {

	public SystemActor findById(int id) {
		
		SystemActor systemActor  = entityManager.find(SystemActor.class, id);
		
		return systemActor;
	}
	
}
