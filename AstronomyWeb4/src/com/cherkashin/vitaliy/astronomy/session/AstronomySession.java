package com.cherkashin.vitaliy.astronomy.session;

import java.util.ArrayList;
import java.util.Date;

import org.apache.wicket.Request;
import org.apache.wicket.protocol.http.WebSession;

import algorithms.utils.DoublePlanet;

import com.cherkashin.vitaliy.astronomy.common_objects.City;

/** Http сессия приложения  */
public class AstronomySession extends WebSession{
	private final static long serialVersionUID=1L;
	
	/** город */
	private City city;
	/** дата рождения */
	private Date date;
	/** мужчина(true) женщина(false) */
	private boolean male;
	
	private ArrayList<DoublePlanet> aspects;
	/** Http сессия приложения  */
	public AstronomySession(Request request) {
		super(request);
	}

	/** место рождения указанного человека */
	public void setCity(City object) {
		this.city=object;
	}

	/** дата рождения указанного человека */
	public void setDateBirthday(Date object) {
		this.date=object;
	}

	/** получить город/место  */
	public City getCity(){
		return this.city;
	}
	
	/** получить дату рождения */
	public Date getDateBirthday(){
		return this.date;
	}

	/** установить список аспектов по данному пользователю */
	public void setListOfAspects(ArrayList<DoublePlanet> listOfAspects) {
		aspects=listOfAspects;
	}
	
	/** получить список аспектов по данному пользователю  */
	public ArrayList<DoublePlanet> getListOfAspects(){
		return this.aspects;
	}
	
	/** является ли данный пользователь мужчиной
	 * @return 
	 * <ul>
	 * 	<li><b>true</b> - мужчина</li>
	 * 	<li><b>false</b> - женщина </li>
	 * </ul>
	 *  */
	public boolean isMale(){
		return this.male;
	}
	
	/** установить пол данного человека
	 * @param male
	 * <ul>
	 * 	<li><b>true</b> - мужчина</li>
	 * 	<li><b>false</b> - женщина</li>
	 * </ul>
	 * */
	public void setMale(boolean male){
		this.male=male;
	}
}
