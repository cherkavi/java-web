package database.wrap_mysql;

import javax.persistence.*;

@Entity
@Table(name="a_city")
public class A_CITY {
	@Id
	@GeneratedValue
	@Column(name="ID")
	private int id;
	
	@Column(name="NAME")
	private String name;
	
	@Column(name="LONGITUDE")
	private Float longitude;
	
	@Column(name="LATITUDE")
	private Float latitude;
	
	@Column(name="GMT")
	private int gmt;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Float getLongitude() {
		return longitude;
	}

	public void setLongitude(Float longitude) {
		this.longitude = longitude;
	}

	public Float getLatitude() {
		return latitude;
	}

	public void setLatitude(Float latitude) {
		this.latitude = latitude;
	}

	public int getGmt() {
		return gmt;
	}

	public void setGmt(int gmt) {
		this.gmt = gmt;
	}
	
	
}
