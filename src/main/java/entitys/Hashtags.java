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
public class Hashtags {


	@GeneratedValue(strategy = GenerationType.AUTO)
	@Id
	private long id;
	
	private String name;
	
	public void setName(String name) {
		this.name = "#"+name;
	}


	
	 

}
