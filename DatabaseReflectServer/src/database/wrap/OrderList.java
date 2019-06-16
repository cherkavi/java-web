package database.wrap;

import javax.persistence.Column;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.Date;
@Entity
@Table(name="ORDER_LIST") 
public class OrderList {
	@Transient
	private final static long serialVersionUID=1L;
	@Id
	@Column(name="ID")
	@SequenceGenerator(name="generator",sequenceName="GEN_ORDER_LIST_ID")
	@GeneratedValue(generator="generator",strategy=GenerationType.AUTO)
    	private Integer id;

	@Column(name="ID_MODEL")
    	private Integer idModel;

	@Column(name="TIME_CREATE")
    	private Date timeCreate;

	@Column(name="TIME_GET_TO_PROCESS")
    	private Date timeGetToProcess;

	@Column(name="TIME_RETURN_FROM_PROCESS")
    	private Date timeReturnFromProcess;

	@Column(name="TIME_RETURN_TO_CUSTOMER")
    	private Date timeReturnToCustomer;

	@Column(name="UNIQUE_NUMBER")
    	private Integer uniqueNumber;

	@Column(name="CONTROL_NUMBER")
    	private String controlNumber;

	@Column(name="ID_ORDER_GROUP")
    	private Integer idOrderGroup;

	@Column(name="FOR_SEND")
    	private Integer forSend;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getIdModel() {
		return idModel;
	}

	public void setIdModel(Integer idModel) {
		this.idModel = idModel;
	}

	public Date getTimeCreate() {
		return timeCreate;
	}

	public void setTimeCreate(Date timeCreate) {
		this.timeCreate = timeCreate;
	}

	public Date getTimeGetToProcess() {
		return this.timeGetToProcess;
	}

	public void setTimeGetToProcess(Date timeGetToProcess) {
		this.timeGetToProcess = timeGetToProcess;
	}

	public Date getTimeReturnFromProcess() {
		return timeReturnFromProcess;
	}

	public void setTimeReturnFromProcess(Date timeReturnFromProcess) {
		this.timeReturnFromProcess = timeReturnFromProcess;
	}

	public Date getTimeReturnToCustomer() {
		return timeReturnToCustomer;
	}

	public void setTimeReturnToCustomer(Date timeReturnToCustomer) {
		this.timeReturnToCustomer = timeReturnToCustomer;
	}

	public Integer getUniqueNumber() {
		return uniqueNumber;
	}

	public void setUniqueNumber(Integer uniqueNumber) {
		this.uniqueNumber = uniqueNumber;
	}

	public String getControlNumber() {
		return this.controlNumber;
	}

	public void setControlNumber(String controlNumber) {
		this.controlNumber = controlNumber;
	}

	public Integer getIdOrderGroup() {
		return this.idOrderGroup;
	}

	public void setIdOrderGroup(Integer idOrderGroup) {
		this.idOrderGroup = idOrderGroup;
	}

	public Integer getForSend() {
		return forSend;
	}

	public void setForSend(Integer forSend) {
		this.forSend = forSend;
	}

	public void generateNumber(Connection connection) {
		try{
			ResultSet rs=connection.createStatement().executeQuery("select gen_id(gen_order_number,1) from rdb$database");
			if(rs.next()){
				this.setUniqueNumber(rs.getInt(1));
			}
			rs.getStatement().close();
		}catch(Exception ex){
			System.err.println("OrderList#generateNumber Exception: "+ex.getMessage());
		}
	}

	public void generateControlNumber() {
		this.controlNumber=getUniqueString(9);
	}

	private final static String hexChars[] = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9"}; //
	
	/** сгенерировать случайную последовательность из Hex чисел, указанной длинны 
	 * @param count - длинна случайной последовательности, которую необходимо получить 
	 * */
	private String getUniqueString(int count){
        StringBuffer return_value=new StringBuffer();
        java.util.Random random=new java.util.Random();
        int temp_value;
        for(int counter=0;counter<count;counter++){
            temp_value=random.nextInt(hexChars.length);
            return_value.append(hexChars[temp_value]);
        }
        return return_value.toString();
	}
	
	
}
