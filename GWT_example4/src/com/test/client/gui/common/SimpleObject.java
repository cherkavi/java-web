package com.test.client.gui.common;

import com.google.gwt.user.client.rpc.IsSerializable;

public class SimpleObject implements IsSerializable{
	private int id;
	private String name;
	public SimpleObject(){
	}
	
	public SimpleObject(int id, String name){
		this.id=id;
		this.name=name;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString(){
		return "Id:"+id+"  Name:"+name;
	}
}
