package database.wrap;

import java.util.Date;

import javax.persistence.*;

@Entity
@Table(name="ASSORTMENT")
public class Assortment {
	@Id
	@GeneratedValue(generator="generator", strategy=GenerationType.AUTO)
	@SequenceGenerator(name="generator", sequenceName="GEN_ASSORTMENT_ID")
	@Column(name="KOD")
	private int kod;
	@Column(name="NAME", length=150)
	private String name;
	@Column(name="NOTE", length=255)
	private String note;
	@Column(name="CLASS_KOD")
	private Integer classKod;
	@Column(name="PRICE_KOD")
	private Integer priceKod;
	@Column(name="BAR_CODE")
	private String barCode;
	@Column(name="DATE_WRITE")
	private Date dateWrite;
	@Column(name="BAR_CODE_COMPANY", length=30)
	private String barCodeCompany;
	@Column(name="WARRANTY_MONTH")
	private Integer warrantyMonth;
	@Column(name="KOD_PHOTO")
	private Integer kodPhoto;
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
	public Integer getClassKod() {
		return classKod;
	}
	public void setClassKod(Integer classKod) {
		this.classKod = classKod;
	}
	public Integer getPriceKod() {
		return priceKod;
	}
	public void setPriceKod(Integer priceKod) {
		this.priceKod = priceKod;
	}
	public String getBarCode() {
		return barCode;
	}
	public void setBarCode(String barCode) {
		this.barCode = barCode;
	}
	public Date getDateWrite() {
		return dateWrite;
	}
	public void setDateWrite(Date dateWrite) {
		this.dateWrite = dateWrite;
	}
	public String getBarCodeCompany() {
		return barCodeCompany;
	}
	public void setBarCodeCompany(String barCodeCompany) {
		this.barCodeCompany = barCodeCompany;
	}
	public Integer getWarrantyMonth() {
		return warrantyMonth;
	}
	public void setWarrantyMonth(Integer warrantyMonth) {
		this.warrantyMonth = warrantyMonth;
	}
	public Integer getKodPhoto() {
		return kodPhoto;
	}
	public void setKodPhoto(Integer kodPhoto) {
		this.kodPhoto = kodPhoto;
	}
	
	
}
