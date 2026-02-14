package app.persistence.repositories;

import javax.persistence.NoResultException;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import app.persistence.entities.User;

@Repository
public class UserRepository extends BaseRepository {
	
	public User findByUsername(String username) {
		
		String query = "SELECT user " + 
					   "FROM   User user " +
					   "WHERE  user.username = :usernameParam";
	
		User user;
		try {
			
			user = entityManager.createQuery(query, User.class)
								.setParameter("usernameParam", username)
								.getSingleResult();
			
		} catch (NoResultException e) {
			throw new UsernameNotFoundException("The user with username " + username + " does not exist");
		}
		
		return user;
	}

}
