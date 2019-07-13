package bonpay.partner.database.wrap;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="SATELLITE")
public class Satellite {
	@Id
	@Column(name="ID")
	@SequenceGenerator(name="generator",sequenceName="GEN_SATELLITE_ID")
	@GeneratedValue(generator="generator",strategy=GenerationType.AUTO)
	private int id;
	@Column(name="NAME")
	private String name;
	@Column(name="KEY_FOR_SERVICE")
	private String key_for_service;
	@Column(name="KEY_FOR_SATELLITE")
	private String key_for_satellite;
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
	public String getKey_for_service() {
		return key_for_service;
	}
	public void setKey_for_service(String key_for_service) {
		this.key_for_service = key_for_service;
	}
	public String getKey_for_satellite() {
		return key_for_satellite;
	}
	public void setKey_for_satellite(String key_for_satellite) {
		this.key_for_satellite = key_for_satellite;
	}
}
