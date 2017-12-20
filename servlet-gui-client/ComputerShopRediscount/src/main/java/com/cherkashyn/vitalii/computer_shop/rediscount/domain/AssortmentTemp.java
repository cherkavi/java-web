package com.cherkashyn.vitalii.computer_shop.rediscount.domain;

// Generated 14.09.2013 14:22:22 by Hibernate Tools 3.4.0.CR1

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * AssortmentTemp generated by hbm2java
 */
@Entity
@Table(name = "ASSORTMENT_TEMP")
public class AssortmentTemp implements java.io.Serializable {

	private AssortmentTempId id;

	public AssortmentTemp() {
	}

	public AssortmentTemp(AssortmentTempId id) {
		this.id = id;
	}

	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "kod", column = @Column(name = "KOD", nullable = false)),
			@AttributeOverride(name = "name", column = @Column(name = "NAME", length = 100)),
			@AttributeOverride(name = "note", column = @Column(name = "NOTE")),
			@AttributeOverride(name = "classKod", column = @Column(name = "CLASS_KOD")),
			@AttributeOverride(name = "priceKod", column = @Column(name = "PRICE_KOD")),
			@AttributeOverride(name = "barCode", column = @Column(name = "BAR_CODE", length = 30)),
			@AttributeOverride(name = "dateWrite", column = @Column(name = "DATE_WRITE", length = 19)),
			@AttributeOverride(name = "barCodeCompany", column = @Column(name = "BAR_CODE_COMPANY", length = 30)) })
	public AssortmentTempId getId() {
		return this.id;
	}

	public void setId(AssortmentTempId id) {
		this.id = id;
	}

}
