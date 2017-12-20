package com.cherkashyn.vitalii.computer_shop.rediscount.domain;

public class Points implements java.io.Serializable {

	private int kod;
	private String name;
	private String address;
	private String note;
	private Short isTrade;
	private String tel;
	private String email;

	public Points() {
	}

	public Points(int kod) {
		this.kod = kod;
	}

	public Points(int kod, String name, String address, String note,
			Short isTrade, String tel, String email) {
		this.kod = kod;
		this.name = name;
		this.address = address;
		this.note = note;
		this.isTrade = isTrade;
		this.tel = tel;
		this.email = email;
	}

	public int getKod() {
		return this.kod;
	}

	public void setKod(int kod) {
		this.kod = kod;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Short getIsTrade() {
		return this.isTrade;
	}

	public void setIsTrade(Short isTrade) {
		this.isTrade = isTrade;
	}

	public String getTel() {
		return this.tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
