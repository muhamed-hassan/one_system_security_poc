package com.poc.persistence.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;

@Table(name = "granted_authority")
@Entity
public class CustomGrantedAuthority implements GrantedAuthority {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
	
	@ManyToOne
    @JoinColumn(name = "ui_screen_id")
	private UiScreen uiScreen;
	
	@ManyToOne
    @JoinColumn(name = "system_actor_id")
	private SystemActor systemActor;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public UiScreen getUiScreen() {
		return uiScreen;
	}

	public void setUiScreen(UiScreen uiScreen) {
		this.uiScreen = uiScreen;
	}		

	public SystemActor getSystemActor() {
		return systemActor;
	}

	public void setSystemActor(SystemActor systemActor) {
		this.systemActor = systemActor;
	}

	@Override
	public String getAuthority() {		
		return uiScreen.getScreenName() + "|" + uiScreen.getScreenType().getType();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CustomGrantedAuthority other = (CustomGrantedAuthority) obj;
		if (id != other.id)
			return false;
		return true;
	}

}
