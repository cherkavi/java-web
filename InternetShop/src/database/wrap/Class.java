package database.wrap;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="CLASS")
public class Class {
	private static final long serialVersionUID = 1L;
	@Id
	@SequenceGenerator(name="generator",sequenceName="GEN_CLASS_ID")	
	@GeneratedValue(strategy=GenerationType.AUTO,generator="generator")	
	@Column(name="KOD")
	private int kod;
	
	@Column(name="NAME",length=255)
	private String name;
	
	@Column(name="NOTE",length=255)
	private String note;
	
	@Column(name="DATE_WRITE")
	private Date date_write;

	public int getKod() {
		return kod;
	}

	public void setKod(int kod) {
		this.kod = kod;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Date getDate_write() {
		return date_write;
	}

	public void setDate_write(Date date_write) {
		this.date_write = date_write;
	}
	
	
}
