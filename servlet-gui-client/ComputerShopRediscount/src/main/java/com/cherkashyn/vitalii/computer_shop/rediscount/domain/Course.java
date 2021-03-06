package com.cherkashyn.vitalii.computer_shop.rediscount.domain;

// Generated 14.09.2013 14:22:22 by Hibernate Tools 3.4.0.CR1

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Course generated by hbm2java
 */
@Entity
@Table(name = "COURSE")
public class Course implements java.io.Serializable {

	private int kod;
	private BigDecimal currencyValue;
	private Date dateSet;
	private Date dateWrite;

	public Course() {
	}

	public Course(int kod) {
		this.kod = kod;
	}

	public Course(int kod, BigDecimal currencyValue, Date dateSet,
			Date dateWrite) {
		this.kod = kod;
		this.currencyValue = currencyValue;
		this.dateSet = dateSet;
		this.dateWrite = dateWrite;
	}

	@Id
	@Column(name = "KOD", unique = true, nullable = false)
	public int getKod() {
		return this.kod;
	}

	public void setKod(int kod) {
		this.kod = kod;
	}

	@Column(name = "CURRENCY_VALUE", precision = 15, scale = 3)
	public BigDecimal getCurrencyValue() {
		return this.currencyValue;
	}

	public void setCurrencyValue(BigDecimal currencyValue) {
		this.currencyValue = currencyValue;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "DATE_SET", length = 10)
	public Date getDateSet() {
		return this.dateSet;
	}

	public void setDateSet(Date dateSet) {
		this.dateSet = dateSet;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DATE_WRITE", length = 19)
	public Date getDateWrite() {
		return this.dateWrite;
	}

	public void setDateWrite(Date dateWrite) {
		this.dateWrite = dateWrite;
	}

}
