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
@Table(name="COMMODITY")
public class Commodity {
	private static final long serialVersionUID = 1L;
	@Id
	@SequenceGenerator(name="generator",sequenceName="GEN_COMMODITY_ID")	
	@GeneratedValue(strategy=GenerationType.AUTO,generator="generator")	
	@Column(name="KOD")
	private int kod;
	
	@Column(name="ASSORTMENT_KOD")
	private int assortment_kod;
	
	@Column(name="QUANTITY")
	private int quantity;
	
	@Column(name="OPERATION_KOD")
	private int operation_kod;
	
	@Column(name="DATE_IN_OUT")
	private Date date_in_out;
	
	@Column(name="POINT_KOD")
	private int point_kod;
	
	@Column(name="NOTE",length=255)
	private String note;
	
	@Column(name="REMINDER_KOD")
	private int reminder_kod;
	
	@Column(name="DATE_WRITE")
	private Date date_write;
	
	@Column(name="SERIAL_KOD")
	private int serial_kod;
	
	@Column(name="DISCONT")
	private BigDecimal discont;

	public int getKod() {
		return kod;
	}

	public void setKod(int kod) {
		this.kod = kod;
	}

	public int getAssortment_kod() {
		return assortment_kod;
	}

	public void setAssortment_kod(int assortment_kod) {
		this.assortment_kod = assortment_kod;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public int getOperation_kod() {
		return operation_kod;
	}

	public void setOperation_kod(int operation_kod) {
		this.operation_kod = operation_kod;
	}

	public Date getDate_in_out() {
		return date_in_out;
	}

	public void setDate_in_out(Date date_in_out) {
		this.date_in_out = date_in_out;
	}

	public int getPoint_kod() {
		return point_kod;
	}

	public void setPoint_kod(int point_kod) {
		this.point_kod = point_kod;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public int getReminder_kod() {
		return reminder_kod;
	}

	public void setReminder_kod(int reminder_kod) {
		this.reminder_kod = reminder_kod;
	}

	public Date getDate_write() {
		return date_write;
	}

	public void setDate_write(Date date_write) {
		this.date_write = date_write;
	}

	public int getSerial_kod() {
		return serial_kod;
	}

	public void setSerial_kod(int serial_kod) {
		this.serial_kod = serial_kod;
	}

	public BigDecimal getDiscont() {
		return discont;
	}

	public void setDiscont(BigDecimal discont) {
		this.discont = discont;
	}
	
	
}
