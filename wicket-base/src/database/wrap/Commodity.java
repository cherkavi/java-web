package database.wrap;

import java.util.Date;

import javax.persistence.*;

@Entity
@Table(name="COMMODITY")
public class Commodity {
	@Id
	@SequenceGenerator(name="generator",sequenceName="GEN_COMMODITY_ID")
	@GeneratedValue(generator="generator", strategy=GenerationType.AUTO)
	@Column(name="ID")
	private Integer id;
	@Column(name="DATE_WRITE")
	private Date dateWrite;
	@Column(name="ID_SECTION")
	private Integer idSection;
	@Column(name="DESCRIPTION",length=200)
	private String description;
	@Column(name="NAME",length=70)
	private String name;
	@Column(name="PRICE_USD")
	private Float priceUsd;
	@Column(name="IMAGE_PATH",length=255)
	private String imageFilename;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Date getDateWrite() {
		return dateWrite;
	}
	public void setDateWrite(Date dateWrite) {
		this.dateWrite = dateWrite;
	}
	public Integer getIdSection() {
		return idSection;
	}
	public void setIdSection(Integer idSection) {
		this.idSection = idSection;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Float getPriceUsd() {
		return priceUsd;
	}
	public void setPriceUsd(Float priceUsd) {
		this.priceUsd = priceUsd;
	}
	public String getImageFilename() {
		return imageFilename;
	}
	public void setImageFilename(String imageFilename) {
		this.imageFilename = imageFilename;
	}
	
	
}
