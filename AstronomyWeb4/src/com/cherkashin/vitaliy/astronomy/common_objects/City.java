package com.cherkashin.vitaliy.astronomy.common_objects;

import java.io.Serializable;

/** объект, идентифицирующий один город из списка  */
public class City implements Serializable{
	private final static long serialVersionUID=1L;
	private int id;
	private String name;
	private float longitude;
	private float latitude;
	private int gmt;
	
	public City(){
	}
	
	public City(int id, String name, float longitude, float latitude, Integer gmt){
		this.id=id;
		this.name=name;
		this.longitude=longitude;
		this.latitude=latitude;
		if(gmt==null){
			this.gmt=0;
		}else{
			this.gmt=gmt;
		}
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public float getLongitude() {
		return longitude;
	}

	public float getLatitude() {
		return latitude;
	}

	public int getGmt(){
		return this.gmt;
	}
	
}
