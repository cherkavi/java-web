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
@Table(name="ASSORTMENT")
public class Assortment {
	private static final long serialVersionUID = 1L;
	@Id
	@SequenceGenerator(name="generator",sequenceName="GEN_ASSORTMENT_ID")	
	@GeneratedValue(strategy=GenerationType.AUTO,generator="generator")	
	@Column(name="KOD")
	private int kod;
	
	@Column(name="NAME",length=150)
	private String name;
	
	@Column(name="NOTE",length=255)
	private String note;
	
	@Column(name="CLASS_KOD")
	private int class_kod;
	
	@Column(name="PRICE_KOD")
	private int price_kod;
	
	@Column(name="BAR_CODE",length=30)
	private String bar_code;
	
	@Column(name="DATE_WRITE")
	private Date date_write;
	
	@Column(name="BAR_CODE_COMPANY",length=30)
	private String bar_code_company;
	
	@Column(name="WARRANTY_MONTH")
	private int warranty_month;

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

	public int getClass_kod() {
		return class_kod;
	}

	public void setClass_kod(int class_kod) {
		this.class_kod = class_kod;
	}

	public int getPrice_kod() {
		return price_kod;
	}

	public void setPrice_kod(int price_kod) {
		this.price_kod = price_kod;
	}

	public String getBar_code() {
		return bar_code;
	}

	public void setBar_code(String bar_code) {
		this.bar_code = bar_code;
	}

	public Date getDate_write() {
		return date_write;
	}

	public void setDate_write(Date date_write) {
		this.date_write = date_write;
	}

	public String getBar_code_company() {
		return bar_code_company;
	}

	public void setBar_code_company(String bar_code_company) {
		this.bar_code_company = bar_code_company;
	}

	public int getWarranty_month() {
		return warranty_month;
	}

	public void setWarranty_month(int warranty_month) {
		this.warranty_month = warranty_month;
	}
	
	
	
}
