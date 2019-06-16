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
@Table(name="ORDER_LIST") 
public class Order_list implements Serializable {
	@Transient
	private final static long serialVersionUID=1L;
	@Id
	@Column(name="ID")
	@SequenceGenerator(name="generator",sequenceName="GEN_ORDER_LIST_ID")
	@GeneratedValue(generator="generator",strategy=GenerationType.AUTO)
    	private Integer id;

	@Column(name="ID_MODEL")
    	private Integer id_model;

	@Column(name="TIME_CREATE")
    	private Date time_create;

	@Column(name="TIME_GET_TO_PROCESS")
    	private Date time_get_to_process;

	@Column(name="TIME_RETURN_FROM_PROCESS")
    	private Date time_return_from_process;

	@Column(name="TIME_RETURN_TO_CUSTOMER")
    	private Date time_return_to_customer;

	@Column(name="UNIQUE_NUMBER")
    	private Integer unique_number;

	@Column(name="CONTROL_NUMBER")
    	private String control_number;

	@Column(name="ID_ORDER_GROUP")
    	private Integer id_order_group;

	@Column(name="FOR_SEND")
    	private Integer for_send;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getId_model() {
		return id_model;
	}

	public void setId_model(Integer idModel) {
		id_model = idModel;
	}

	public Date getTime_create() {
		return time_create;
	}

	public void setTime_create(Date timeCreate) {
		time_create = timeCreate;
	}

	public Date getTime_get_to_process() {
		return time_get_to_process;
	}

	public void setTime_get_to_process(Date timeGetToProcess) {
		time_get_to_process = timeGetToProcess;
	}

	public Date getTime_return_from_process() {
		return time_return_from_process;
	}

	public void setTime_return_from_process(Date timeReturnFromProcess) {
		time_return_from_process = timeReturnFromProcess;
	}

	public Date getTime_return_to_customer() {
		return time_return_to_customer;
	}

	public void setTime_return_to_customer(Date timeReturnToCustomer) {
		time_return_to_customer = timeReturnToCustomer;
	}

	public Integer getUnique_number() {
		return unique_number;
	}

	public void setUnique_number(Integer uniqueNumber) {
		unique_number = uniqueNumber;
	}

	public String getControl_number() {
		return control_number;
	}

	public void setControl_number(String controlNumber) {
		control_number = controlNumber;
	}

	public Integer getId_order_group() {
		return id_order_group;
	}

	public void setId_order_group(Integer idOrderGroup) {
		id_order_group = idOrderGroup;
	}

	public Integer getFor_send() {
		return for_send;
	}

	public void setFor_send(Integer forSend) {
		for_send = forSend;
	}

	
}
