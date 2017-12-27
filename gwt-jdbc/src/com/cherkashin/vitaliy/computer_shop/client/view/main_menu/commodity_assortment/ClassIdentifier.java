package com.cherkashin.vitaliy.computer_shop.client.view.main_menu.commodity_assortment;

import com.google.gwt.user.client.rpc.IsSerializable;

public class ClassIdentifier implements IsSerializable{
	private Integer kod;
	private String name;
	
	public ClassIdentifier(){
	}
	
	public ClassIdentifier(Integer kod, String name){
		this.kod=kod;
		this.name=name;
	}

	public Integer getKod() {
		return kod;
	}

	public String getName() {
		return name;
	}

	public void setKod(Integer kod) {
		this.kod = kod;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
}
