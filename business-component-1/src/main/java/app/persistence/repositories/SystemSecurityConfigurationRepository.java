package app.persistence.repositories;

import org.springframework.stereotype.Repository;

import app.persistence.entities.SystemSecurityConfiguration;

@Repository
public class SystemSecurityConfigurationRepository extends BaseRepository {

	public SystemSecurityConfiguration findById(int id) {
		
		SystemSecurityConfiguration systemSecurityConfiguration = entityManager.find(SystemSecurityConfiguration.class, id);
		
		return systemSecurityConfiguration;
	}

}
