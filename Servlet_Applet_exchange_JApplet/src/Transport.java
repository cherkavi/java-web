import java.io.Serializable;

/** транспортный объект, который будет
 * передаваться от аплета к сервлету и от сервлета к алпету*/
public class Transport implements Serializable{
	private static final long serialVersionUID = 1L;

	private String field_command;
	public Transport(String value){
		this.field_command=value;
	}
	public Transport(){
		
	}
	public void setCommand(String value){
		this.field_command=value;
	}
	public String getCommand(){
		return this.field_command;
	}
}
