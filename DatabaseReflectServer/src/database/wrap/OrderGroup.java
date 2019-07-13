package database.wrap;

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
public class OrderGroup {
	@Transient
	private final static long serialVersionUID=1L;
	
	@Id
	@Column(name="ID")
	@SequenceGenerator(name="generator",sequenceName="GEN_ORDER_GROUP_ID")
	@GeneratedValue(generator="generator",strategy=GenerationType.AUTO)
    	private Integer id;

	@Column(name="ID_CUSTOMER")
    	private Integer idCustomer;

	@Column(name="DESCRIPTION")
    	private String description;

	@Column(name="FOR_SEND")
    	private Integer forSend;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getIdCustomer() {
		return idCustomer;
	}

	public void setIdCustomer(Integer idCustomer) {
		this.idCustomer = idCustomer;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getForSend() {
		return forSend;
	}

	public void setForSend(Integer forSend) {
		this.forSend = forSend;
	}

	
}
