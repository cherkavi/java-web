package com.cherkashyn.vitalii.computer_shop.rediscount.domain;

// Generated 14.09.2013 14:22:22 by Hibernate Tools 3.4.0.CR1

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * AssortmentTempId generated by hbm2java
 */
@Embeddable
public class AssortmentTempId implements java.io.Serializable {

	private int kod;
	private String name;
	private String note;
	private Integer classKod;
	private Integer priceKod;
	private String barCode;
	private Date dateWrite;
	private String barCodeCompany;

	public AssortmentTempId() {
	}

	public AssortmentTempId(int kod) {
		this.kod = kod;
	}

	public AssortmentTempId(int kod, String name, String note,
			Integer classKod, Integer priceKod, String barCode, Date dateWrite,
			String barCodeCompany) {
		this.kod = kod;
		this.name = name;
		this.note = note;
		this.classKod = classKod;
		this.priceKod = priceKod;
		this.barCode = barCode;
		this.dateWrite = dateWrite;
		this.barCodeCompany = barCodeCompany;
	}

	@Column(name = "KOD", nullable = false)
	public int getKod() {
		return this.kod;
	}

	public void setKod(int kod) {
		this.kod = kod;
	}

	@Column(name = "NAME", length = 100)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "NOTE")
	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	@Column(name = "CLASS_KOD")
	public Integer getClassKod() {
		return this.classKod;
	}

	public void setClassKod(Integer classKod) {
		this.classKod = classKod;
	}

	@Column(name = "PRICE_KOD")
	public Integer getPriceKod() {
		return this.priceKod;
	}

	public void setPriceKod(Integer priceKod) {
		this.priceKod = priceKod;
	}

	@Column(name = "BAR_CODE", length = 30)
	public String getBarCode() {
		return this.barCode;
	}

	public void setBarCode(String barCode) {
		this.barCode = barCode;
	}

	@Column(name = "DATE_WRITE", length = 19)
	public Date getDateWrite() {
		return this.dateWrite;
	}

	public void setDateWrite(Date dateWrite) {
		this.dateWrite = dateWrite;
	}

	@Column(name = "BAR_CODE_COMPANY", length = 30)
	public String getBarCodeCompany() {
		return this.barCodeCompany;
	}

	public void setBarCodeCompany(String barCodeCompany) {
		this.barCodeCompany = barCodeCompany;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof AssortmentTempId))
			return false;
		AssortmentTempId castOther = (AssortmentTempId) other;

		return (this.getKod() == castOther.getKod())
				&& ((this.getName() == castOther.getName()) || (this.getName() != null
						&& castOther.getName() != null && this.getName()
						.equals(castOther.getName())))
				&& ((this.getNote() == castOther.getNote()) || (this.getNote() != null
						&& castOther.getNote() != null && this.getNote()
						.equals(castOther.getNote())))
				&& ((this.getClassKod() == castOther.getClassKod()) || (this
						.getClassKod() != null
						&& castOther.getClassKod() != null && this
						.getClassKod().equals(castOther.getClassKod())))
				&& ((this.getPriceKod() == castOther.getPriceKod()) || (this
						.getPriceKod() != null
						&& castOther.getPriceKod() != null && this
						.getPriceKod().equals(castOther.getPriceKod())))
				&& ((this.getBarCode() == castOther.getBarCode()) || (this
						.getBarCode() != null && castOther.getBarCode() != null && this
						.getBarCode().equals(castOther.getBarCode())))
				&& ((this.getDateWrite() == castOther.getDateWrite()) || (this
						.getDateWrite() != null
						&& castOther.getDateWrite() != null && this
						.getDateWrite().equals(castOther.getDateWrite())))
				&& ((this.getBarCodeCompany() == castOther.getBarCodeCompany()) || (this
						.getBarCodeCompany() != null
						&& castOther.getBarCodeCompany() != null && this
						.getBarCodeCompany().equals(
								castOther.getBarCodeCompany())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + this.getKod();
		result = 37 * result
				+ (getName() == null ? 0 : this.getName().hashCode());
		result = 37 * result
				+ (getNote() == null ? 0 : this.getNote().hashCode());
		result = 37 * result
				+ (getClassKod() == null ? 0 : this.getClassKod().hashCode());
		result = 37 * result
				+ (getPriceKod() == null ? 0 : this.getPriceKod().hashCode());
		result = 37 * result
				+ (getBarCode() == null ? 0 : this.getBarCode().hashCode());
		result = 37 * result
				+ (getDateWrite() == null ? 0 : this.getDateWrite().hashCode());
		result = 37
				* result
				+ (getBarCodeCompany() == null ? 0 : this.getBarCodeCompany()
						.hashCode());
		return result;
	}

}
