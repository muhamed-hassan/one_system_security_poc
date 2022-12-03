package com.poc.persistence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.poc.persistence.entities.SystemActor;

@Repository 
public interface SystemActorRepository extends JpaRepository<SystemActor, Integer> {

}
