package app.persistence.repositories;

import org.springframework.stereotype.Repository;

import app.persistence.entities.User;

@Repository
public class UserRepository extends BaseRepository {
	
	public void save(User user) {
		
		entityManager.persist(user);
	}
	
	public long getCountOfUsername(String username) {
		
		String countQuery = "SELECT COUNT(*) " + 
							"FROM   User user " +
							"WHERE  user.username LIKE :usernameParam%";

		long totalElements = entityManager.createQuery(countQuery, Long.class)
											.setParameter("usernameParam", username)
											.getSingleResult();
		
		return totalElements;
	}

}
