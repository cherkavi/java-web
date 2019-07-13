package database_reflect.database.wrap;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import java.io.Serializable;
import java.util.Date;
@Entity
@Table(name="CUSTOMER") 
public class Customer implements Serializable {
	@Transient
	private final static long serialVersionUID=1L;
	
	@Id
	@Column(name="ID")
	@SequenceGenerator(name="generator",sequenceName="GEN_CUSTOMER_ID")
	@GeneratedValue(generator="generator",strategy=GenerationType.AUTO)
    	private Integer id;

	@Column(name="SURNAME")
    	private String surname;

	@Column(name="NAME")
    	private String name;

	@Column(name="TIME_CREATE")
    	private Date time_create;

	@Column(name="FOR_SEND")
    	private Integer for_send;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getTime_create() {
		return time_create;
	}

	public void setTime_create(Date timeCreate) {
		time_create = timeCreate;
	}

	public Integer getFor_send() {
		return for_send;
	}

	public void setFor_send(Integer forSend) {
		for_send = forSend;
	}

}
