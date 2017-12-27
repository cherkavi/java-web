package database.wrap;

import java.util.Date;

import javax.persistence.*;

@Entity
@Table(name="PRICE")
public class Price implements Cloneable{
	@Id
	@GeneratedValue(generator="generator", strategy=GenerationType.AUTO)
	@SequenceGenerator(name="generator", sequenceName="GEN_PRICE_ID")
	@Column(name="KOD")
	private int kod;
	@Column(name="PRICE_BUYING")
	private float priceBuying;
	@Column(name="PRICE")
	private float price;
	@Column(name="VALID")
	private Integer valid;
	@Column(name="NOTE", length=255)
	private String note;
	@Column(name="DATE_WRITE")
	private Date dateWrite;
	@Column(name="NEXT_KOD")
	private Integer nextKod;
	
	
	public int getKod() {
		return kod;
	}
	public void setKod(int kod) {
		this.kod = kod;
	}
	public float getPriceBuying() {
		return priceBuying;
	}
	public void setPriceBuying(float priceBuying) {
		this.priceBuying = priceBuying;
	}
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	public Integer getValid() {
		return valid;
	}
	public void setValid(Integer valid) {
		this.valid = valid;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public Date getDateWrite() {
		return dateWrite;
	}
	public void setDateWrite(Date dateWrite) {
		this.dateWrite = dateWrite;
	}
	public Integer getNextKod() {
		return nextKod;
	}
	public void setNextKod(Integer nextKod) {
		this.nextKod = nextKod;
	}
	
	@Override
	public Price clone(){
		Price price=new Price();
		price.setKod(kod);
		price.setPriceBuying(this.getPriceBuying());
		price.setPrice(this.getPrice());
		price.setValid(this.getValid());
		price.setNote(this.getNote());
		price.setDateWrite(this.getDateWrite());
		price.setNextKod(this.getNextKod());
		return price;
	}
}
