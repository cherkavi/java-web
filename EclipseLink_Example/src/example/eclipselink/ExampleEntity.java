package example.eclipselink;
import java.io.Serializable;

import javax.persistence.*;

@Entity
public class ExampleEntity implements Serializable{
	private final static long serialVersionUID=1L;
	
	@Id
	private int id;
	@Transient
	private int tempValue;
	private String name;
	private String description;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getTempValue() {
		return tempValue;
	}
	public void setTempValue(int tempValue) {
		this.tempValue = tempValue;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	
}
