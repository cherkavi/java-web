package database.wrap;
import java.util.Date;

import javax.persistence.*;

@Entity
@Table(name="COURSE")
public class Course {
	@Id
	@SequenceGenerator(name="generator",sequenceName="GEN_COURSE_ID")
	@GeneratedValue(generator="generator",strategy=GenerationType.AUTO)
	@Column(name="ID")
	private Integer id;
	@Column(name="CURRENCY_VALUE",length=100)
	private Float currencyValue;
	@Column(name="DATE_WRITE")
	private Date dateWrite;
	@Column(name="DATE_SET")
	private Date dateSet;

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Float getCurrencyValue() {
		return currencyValue;
	}
	public void setCurrencyValue(Float currencyValue) {
		this.currencyValue = currencyValue;
	}
	public Date getDateWrite() {
		return dateWrite;
	}
	public void setDateWrite(Date dateWrite) {
		this.dateWrite = dateWrite;
	}
	public Date getDateSet() {
		return dateSet;
	}
	public void setDateSet(Date dateSet) {
		this.dateSet = dateSet;
	}
	
	
}
