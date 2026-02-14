package app.persistence.entities;

import java.util.List;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Table(name = "system_actor")
@Entity
public class SystemActor {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
	
	private String type;
	
	@OneToMany(mappedBy = "systemActor", fetch = FetchType.EAGER)
	private List<CustomGrantedAuthority> authorities;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<CustomGrantedAuthority> getAuthorities() {
		return authorities;
	}

	public void setAuthorities(List<CustomGrantedAuthority> authorities) {
		this.authorities = authorities;
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
		SystemActor other = (SystemActor) object;
		return id == other.getId();
	}

}
