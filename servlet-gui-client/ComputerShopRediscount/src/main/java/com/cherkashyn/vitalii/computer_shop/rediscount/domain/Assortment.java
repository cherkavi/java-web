package com.cherkashyn.vitalii.computer_shop.rediscount.domain;

// Generated 14.09.2013 14:22:22 by Hibernate Tools 3.4.0.CR1

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Assortment generated by hbm2java
 */
@Entity
@Table(name = "ASSORTMENT")
public class Assortment implements java.io.Serializable {

	private int kod;
	private Supplier supplier;
	private String name;
	private String note;
	private Integer classKod;
	private Integer priceKod;
	private String barCode;
	private Date dateWrite;
	private String barCodeCompany;
	private Short warrantyMonth;
	private Integer kodPhoto;
	private Short notAssembly;
	private Short updateInServer;

	public Assortment() {
	}

	public Assortment(int kod) {
		this.kod = kod;
	}

	public Assortment(int kod, Supplier supplier, String name, String note,
			Integer classKod, Integer priceKod, String barCode, Date dateWrite,
			String barCodeCompany, Short warrantyMonth, Integer kodPhoto,
			Short notAssembly, Short updateInServer) {
		this.kod = kod;
		this.supplier = supplier;
		this.name = name;
		this.note = note;
		this.classKod = classKod;
		this.priceKod = priceKod;
		this.barCode = barCode;
		this.dateWrite = dateWrite;
		this.barCodeCompany = barCodeCompany;
		this.warrantyMonth = warrantyMonth;
		this.kodPhoto = kodPhoto;
		this.notAssembly = notAssembly;
		this.updateInServer = updateInServer;
	}

	@Id
	@Column(name = "KOD", unique = true, nullable = false)
	public int getKod() {
		return this.kod;
	}

	public void setKod(int kod) {
		this.kod = kod;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SUPPLIER_KOD")
	public Supplier getSupplier() {
		return this.supplier;
	}

	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}

	@Column(name = "NAME", length = 150)
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

	@Temporal(TemporalType.TIMESTAMP)
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

	@Column(name = "WARRANTY_MONTH")
	public Short getWarrantyMonth() {
		return this.warrantyMonth;
	}

	public void setWarrantyMonth(Short warrantyMonth) {
		this.warrantyMonth = warrantyMonth;
	}

	@Column(name = "KOD_PHOTO")
	public Integer getKodPhoto() {
		return this.kodPhoto;
	}

	public void setKodPhoto(Integer kodPhoto) {
		this.kodPhoto = kodPhoto;
	}

	@Column(name = "NOT_ASSEMBLY")
	public Short getNotAssembly() {
		return this.notAssembly;
	}

	public void setNotAssembly(Short notAssembly) {
		this.notAssembly = notAssembly;
	}

	@Column(name = "UPDATE_IN_SERVER")
	public Short getUpdateInServer() {
		return this.updateInServer;
	}

	public void setUpdateInServer(Short updateInServer) {
		this.updateInServer = updateInServer;
	}

}