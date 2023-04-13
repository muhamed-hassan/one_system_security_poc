package com.poc.persistence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.poc.persistence.entities.User;

@Repository 
public interface UserRepository extends JpaRepository<User, Integer> {

	@Query("SELECT COUNT(*) "
			+ "FROM User user "
			+ "WHERE user.username LIKE :username%")
	public long getCountOfUsername(@Param("username") String username);
	
}
