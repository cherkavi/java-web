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
@Table(name="CLIENTS")
public class Clients {
	private static final long serialVersionUID = 1L;
	@Id
	@SequenceGenerator(name="generator",sequenceName="GEN_CLIENTS_ID")	
	@GeneratedValue(strategy=GenerationType.AUTO,generator="generator")	
	@Column(name="KOD")
	private int kod;
	
	@Column(name="BAR_CODE", length=10)
	private String bar_code;
	
	@Column(name="EMAIL", length=20)
	private String email;
	
	@Column(name="PHONE",length=20)
	private String phone;
	
	@Column(name="PHONE_2",length=20)
	private String phone_2;
	
	@Column(name="ADDRESS", length=75)
	private String address;
	
	@Column(name="NAME",length=20)
	private String name;
	
	@Column(name="SURNAME",length=20)
	private String surname;
	
	@Column(name="FATHER_NAME",length=20)
	private String father_name;
	
	@Column(name="NOTE",length=75)
	private String note;
	
	@Column(name="PERCENT")
	private float percent;
	
	@Column(name="DATE_WRITE")
	private Date date_write;
	
	public Clients(){
		
	}

	public int getKod() {
		return kod;
	}

	public void setKod(int kod) {
		this.kod = kod;
	}

	public String getBar_code() {
		return bar_code;
	}

	public void setBar_code(String bar_code) {
		this.bar_code = bar_code;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPhone_2() {
		return phone_2;
	}

	public void setPhone_2(String phone_2) {
		this.phone_2 = phone_2;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getFather_name() {
		return father_name;
	}

	public void setFather_name(String father_name) {
		this.father_name = father_name;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public float getPercent() {
		return percent;
	}

	public void setPercent(float percent) {
		this.percent = percent;
	}

	public Date getDate_write() {
		return date_write;
	}

	public void setDate_write(Date date_write) {
		this.date_write = date_write;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}
	
	
}
