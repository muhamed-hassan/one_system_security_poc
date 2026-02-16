package app.persistence.repositories;

import java.util.List;

import org.springframework.stereotype.Repository;

import app.persistence.entities.CustomGrantedAuthority;

@Repository
public class CustomGrantedAuthorityRepository extends BaseRepository {
	
	public List<CustomGrantedAuthority> findAll(int systemActorId) {
		
		String query = "SELECT customGrantedAuthority " + 
					   "FROM   CustomGrantedAuthority customGrantedAuthority " +
					   "WHERE  customGrantedAuthority.systemActor.id = :idParam";
	
		List<CustomGrantedAuthority> authorities = entityManager.createQuery(query, CustomGrantedAuthority.class)
																.setParameter("idParam", systemActorId)
																.getResultList();
		
		return authorities;
	}

}
