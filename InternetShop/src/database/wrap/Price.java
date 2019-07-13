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
@Table(name="PRICE")
public class Price {

	@Id
	@SequenceGenerator(name="generator",sequenceName="GEN_PRICE_ID")	
	@GeneratedValue(strategy=GenerationType.AUTO,generator="generator")	
	@Column(name="KOD")
	private int kod;
	
	@Column(name="PRICE_BUYING")
	private BigDecimal price_buying;
	
	@Column(name="PRICE")
	private BigDecimal price;
	
	@Column(name="VALID")
	private short valid;
	
	@Column(name="NOTE",length=255)
	private String note;
	
	@Column(name="DATE_WRITE")
	private Date date_write;
	
	@Column(name="NEXT_KOD")
	private int next_kod;

	public int getKod() {
		return kod;
	}

	public void setKod(int kod) {
		this.kod = kod;
	}

	public BigDecimal getPrice_buying() {
		return price_buying;
	}

	public void setPrice_buying(BigDecimal price_buying) {
		this.price_buying = price_buying;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public short getValid() {
		return valid;
	}

	public void setValid(short valid) {
		this.valid = valid;
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

	public int getNext_kod() {
		return next_kod;
	}

	public void setNext_kod(int next_kod) {
		this.next_kod = next_kod;
	}
	
	
	
}
