package database.wrap;
import java.util.Date;

import javax.persistence.*;

/** сердцебиение модуля - сохранение всех сигналов, которые модуль выдает как сигнал OnLine */
@Entity
@Table(name="module_heart_beat")
public class ModuleHeartBeat {
	@Id
	@GeneratedValue
	@Column(name="id")
	private int id;
	@Column(name="id_module")
	private int idModule;
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
	public Date getDateWrite() {
		return dateWrite;
	}
	public void setDateWrite(Date dateWrite) {
		this.dateWrite = dateWrite;
	}
	
	
}
