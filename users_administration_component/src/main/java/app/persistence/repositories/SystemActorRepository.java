package app.persistence.repositories;

import java.util.List;

import javax.persistence.NoResultException;

import org.springframework.stereotype.Repository;

import app.persistence.entities.SystemActor;
import app.persistence.exceptions.DataNotFoundException;

@Repository
public class SystemActorRepository extends BaseRepository {

	public SystemActor findById(int id) {
		
		String query = "SELECT systemActor " + 
					   "FROM   SystemActor systemActor " +
					   "WHERE  systemActor.id = :idParam";

		SystemActor systemActor;
		try {
			
			systemActor = entityManager.createQuery(query, SystemActor.class)
										.setParameter("idParam", id)
										.getSingleResult();
			
		} catch (NoResultException e) {
			throw new DataNotFoundException();
		}
	
		return systemActor;
	}
	
	public List<SystemActor> findAll() {
		
		String query = "SELECT systemActor " + 
					   "FROM   SystemActor systemActor";

		List<SystemActor> systemActors = entityManager.createQuery(query, SystemActor.class)
														.getResultList();
	
		return systemActors;
	}
	
}
