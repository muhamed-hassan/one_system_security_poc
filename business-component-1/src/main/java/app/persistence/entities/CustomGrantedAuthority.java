package app.persistence.entities;

import java.util.Objects;

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
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}
		if (object == null) {
			return false;
		}
		if (getClass() != object.getClass()) {
			return false;
		}
		CustomGrantedAuthority other = (CustomGrantedAuthority) object;
		return id == other.getId();
	}

}
