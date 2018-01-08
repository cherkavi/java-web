package com.cxf.commons;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="TestBean")
public class TestBean implements Serializable{
	private final static long serialVersionUID=1L;
	
	private int id;
	private String name;
	
	public TestBean(){
	}
	
	public TestBean(int id, String name){
		this.id=id;
		this.name=name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "TestBean [id=" + id + ", name=" + name + "]";
	}
	
}
