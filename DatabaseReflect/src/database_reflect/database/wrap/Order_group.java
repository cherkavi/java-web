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
@Table(name="ORDER_GROUP") 
public class Order_group implements Serializable {
	@Transient
	private final static long serialVersionUID=1L;
	
	@Id
	@Column(name="ID")
	@SequenceGenerator(name="generator",sequenceName="GEN_ORDER_GROUP_ID")
	@GeneratedValue(generator="generator",strategy=GenerationType.AUTO)
    	private Integer id;

	@Column(name="ID_CUSTOMER")
    	private Integer id_customer;

	@Column(name="DESCRIPTION")
    	private String description;

	@Column(name="FOR_SEND")
    	private Integer for_send;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getId_customer() {
		return id_customer;
	}

	public void setId_customer(Integer idCustomer) {
		id_customer = idCustomer;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getFor_send() {
		return for_send;
	}

	public void setFor_send(Integer forSend) {
		for_send = forSend;
	}

	
}
