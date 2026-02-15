package app.persistence.repositories;

import javax.persistence.NoResultException;

import org.springframework.stereotype.Repository;

import app.persistence.entities.SystemSecurityConfiguration;
import app.persistence.exceptions.DataNotFoundException;

@Repository
public class SystemSecurityConfigurationRepository extends BaseRepository {

	public SystemSecurityConfiguration findById(int id) {
		
		String query = "SELECT systemSecurityConfiguration " + 
					   "FROM   SystemSecurityConfiguration systemSecurityConfiguration " +
					   "WHERE  systemSecurityConfiguration.id = :idParam";

		SystemSecurityConfiguration systemSecurityConfiguration;
		try {
			
			systemSecurityConfiguration = entityManager.createQuery(query, SystemSecurityConfiguration.class)
														.setParameter("idParam", id)
														.getSingleResult();
			
		} catch (NoResultException e) {
			throw new DataNotFoundException("Failed to load system-security-configuration");
		}
	
		return systemSecurityConfiguration;
	}

}
