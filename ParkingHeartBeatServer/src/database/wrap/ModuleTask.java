package database.wrap;
import java.util.Date;

import javax.persistence.*;

/** задачи для модулей, которые удаленные модули должны забрать и подтвердить то что отработали и получили */
@Entity
@Table(name="module_task")
public class ModuleTask {
	@Id
	@GeneratedValue
	@Column(name="id")
	private int id;
	@Column(name="id_module")
	private int idModule;
	@Column(name="param_name",length=45)
	private String paramName;
	@Column(name="param_value",length=255)
	private String paramValue;
	@Column(name="id_state")
	private Integer idState;
	@Column(name="date_write")
	private Date dateWrite;
	@Column(name="answer",length=255)
	private String answer;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getIdModule() {
		return idModule;
	}
	public void setIdModule(int idModule) {
		this.idModule = idModule;
	}
	public String getParamName() {
		return paramName;
	}
	public void setParamName(String paramName) {
		this.paramName = paramName;
	}
	public String getParamValue() {
		return paramValue;
	}
	public void setParamValue(String paramValue) {
		this.paramValue = paramValue;
	}
	public Integer getIdState() {
		return idState;
	}
	public void setIdState(Integer idState) {
		this.idState = idState;
	}
	public Date getDateWrite() {
		return dateWrite;
	}
	public void setDateWrite(Date dateWrite) {
		this.dateWrite = dateWrite;
	}
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	
}
