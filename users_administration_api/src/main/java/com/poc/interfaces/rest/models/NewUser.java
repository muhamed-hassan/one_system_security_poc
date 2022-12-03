package com.poc.interfaces.rest.models;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class NewUser {
	
	@NotBlank(message = "username is required")
	private String username;
	
	@NotBlank(message = "password is required")
	@Pattern(regexp = "[a-zA-Z0-9]{8,}", message = "password should be a mixture of at least 8 characters made of lower/upper case letters and digits")
	private String password;
	
	@NotBlank(message = "name is required")
	private String name;
	
	@NotBlank(message = "mobile is required")
	@Pattern(regexp = "(01)[0-9]{9}", message = "password should be a mixture of at least 8 characters made of lower/upper case letters and digits")
	private String mobile;
	
	@NotBlank(message = "email is required")
	@Email(message = "email is inavlid")
	private String email;
    
    private int systemActorId;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getSystemActorId() {
		return systemActorId;
	}

	public void setSystemActorId(int systemActorId) {
		this.systemActorId = systemActorId;
	}

}
