package entitys;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.validation.constraints.Null;

import twitter4j.GeoLocation;
import twitter4j.User;



@Entity
public class Tweets {
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Id
	private long id;
	
	private long user;
	@Lob
	private String text;
	
	private GeoLocation location;
	private boolean valid;
	public void setUser(long l) {
		this.user = l;
	}
	public void setText(String text) {
		this.text = text;
	}
	public void setLocation(GeoLocation geoLocation) {
		this.location = geoLocation;
	}
	public void setValid(boolean valid) {
		this.valid = valid;
	}

}
