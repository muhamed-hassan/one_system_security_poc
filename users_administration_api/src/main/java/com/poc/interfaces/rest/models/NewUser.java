package com.poc.interfaces.rest.models;

public class NewUser {
	
	private String password;
	
	private String name;
	
	private String mobile;

	private String email;
    
    private int systemActorId;

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
