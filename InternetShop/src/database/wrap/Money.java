package database.wrap;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="MONEY")
public class Money {
	private static final long serialVersionUID = 1L;
	@Id
	@SequenceGenerator(name="generator",sequenceName="GEN_MONEY_ID")	
	@GeneratedValue(strategy=GenerationType.AUTO,generator="generator")	
	@Column(name="KOD")
	private int kod;

	@Column(name="KOD_EXPENSES")
	private int kod_expenses;
	
	@Column(name="KOD_POINT")
	private int kod_point;
	
	@Column(name="KOD_PEOPLE")
	private int kod_people;
	
	@Column(name="AMOUNT")
	private BigDecimal amount;
	
	@Column(name="DATE_IN_OUT")
	private Date date;
	
	@Column(name="DATE_WRITE")
	private Date date_write;
	
	@Column(name="NOTE",length=50)
	private String note;
	
	@Column(name="KOD_COMMODITY")
	private int kod_commodity;
	
	@Column(name="KOD_ASSORTMENT")
	private int kod_assortment;
	
	@Column(name="KOD_CLIENT")
	private int kod_client;

	public int getKod() {
		return kod;
	}

	public void setKod(int kod) {
		this.kod = kod;
	}

	public int getKod_expenses() {
		return kod_expenses;
	}

	public void setKod_expenses(int kod_expenses) {
		this.kod_expenses = kod_expenses;
	}

	public int getKod_point() {
		return kod_point;
	}

	public void setKod_point(int kod_point) {
		this.kod_point = kod_point;
	}

	public int getKod_people() {
		return kod_people;
	}

	public void setKod_people(int kod_people) {
		this.kod_people = kod_people;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Date getDate_write() {
		return date_write;
	}

	public void setDate_write(Date date_write) {
		this.date_write = date_write;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public int getKod_commodity() {
		return kod_commodity;
	}

	public void setKod_commodity(int kod_commodity) {
		this.kod_commodity = kod_commodity;
	}

	public int getKod_assortment() {
		return kod_assortment;
	}

	public void setKod_assortment(int kod_assortment) {
		this.kod_assortment = kod_assortment;
	}

	public int getKod_client() {
		return kod_client;
	}

	public void setKod_client(int kod_client) {
		this.kod_client = kod_client;
	}
	
	
}
