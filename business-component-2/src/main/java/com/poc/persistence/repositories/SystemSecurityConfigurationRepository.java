package com.poc.persistence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.poc.persistence.entities.SystemSecurityConfiguration;

@Repository
public interface SystemSecurityConfigurationRepository extends JpaRepository<SystemSecurityConfiguration, Integer> {

}
