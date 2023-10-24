package com.poc.persistence.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "system_security_configuration")
@Entity
public class SystemSecurityConfiguration {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
	
	@Column(name = "jwt_secret")
	private String jwtSecret;
	
	@Column(name = "jwt_expiration")
	private int jwtExpiration;
	
	@Column(name = "authentication_path")
	private String authenticationPath;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getJwtSecret() {
		return jwtSecret;
	}

	public void setJwtSecret(String jwtSecret) {
		this.jwtSecret = jwtSecret;
	}

	public int getJwtExpiration() {
		return jwtExpiration;
	}

	public void setJwtExpiration(int jwtExpiration) {
		this.jwtExpiration = jwtExpiration;
	}

	public String getAuthenticationPath() {
		return authenticationPath;
	}

	public void setAuthenticationPath(String authenticationPath) {
		this.authenticationPath = authenticationPath;
	}	
	
}
