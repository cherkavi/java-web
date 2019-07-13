package database_reflect.database.wrap;

import java.io.Serializable;

import javax.persistence.Column;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
@Entity
@Table(name="CARTRIDGE_MODEL") 
public class Cartridge_model implements Serializable {
	@Transient
	private final static long serialVersionUID=1L;
	
	@Id
	@Column(name="ID")
	@SequenceGenerator(name="generator",sequenceName="GEN_CARTRIDGE_MODEL_ID")
	@GeneratedValue(generator="generator",strategy=GenerationType.AUTO)
    	private Integer id;

	@Column(name="ID_VENDOR")
    	private Integer id_vendor;

	@Column(name="NAME")
    	private String name;

	@Column(name="PRICE")
    	private Float price;

	@Column(name="FOR_SEND")
    	private Integer for_send;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getId_vendor() {
		return id_vendor;
	}

	public void setId_vendor(Integer idVendor) {
		id_vendor = idVendor;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Float getPrice() {
		return price;
	}

	public void setPrice(Float price) {
		this.price = price;
	}

	public Integer getFor_send() {
		return for_send;
	}

	public void setFor_send(Integer forSend) {
		for_send = forSend;
	}

	
	
}
