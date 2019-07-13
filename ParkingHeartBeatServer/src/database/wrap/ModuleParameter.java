package database.wrap;
import java.util.Date;

import javax.persistence.*;

/** ответы модулей на поставленные им задачи {@link database.wrap.ModuleTask}*/
@Entity
@Table(name="module_parameter")
public class ModuleParameter {
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
	@Column(name="date_write")
	private Date dateWrite;
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
	public Date getDateWrite() {
		return dateWrite;
	}
	public void setDateWrite(Date dateWrite) {
		this.dateWrite = dateWrite;
	}
	
	
}
