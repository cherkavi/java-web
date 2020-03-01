package com.cherkashin.vitaliy.astronomy.session;

import java.util.ArrayList;
import java.util.Date;

import org.apache.wicket.Request;
import org.apache.wicket.protocol.http.WebSession;

import algorithms.utils.DoublePlanet;

import com.cherkashin.vitaliy.astronomy.common_objects.City;

/** Http ������ ����������  */
public class AstronomySession extends WebSession{
	private final static long serialVersionUID=1L;
	
	/** ����� */
	private City city;
	/** ���� �������� */
	private Date date;
	/** �������(true) �������(false) */
	private boolean male;
	
	private ArrayList<DoublePlanet> aspects;
	/** Http ������ ����������  */
	public AstronomySession(Request request) {
		super(request);
	}

	/** ����� �������� ���������� �������� */
	public void setCity(City object) {
		this.city=object;
	}

	/** ���� �������� ���������� �������� */
	public void setDateBirthday(Date object) {
		this.date=object;
	}

	/** �������� �����/�����  */
	public City getCity(){
		return this.city;
	}
	
	/** �������� ���� �������� */
	public Date getDateBirthday(){
		return this.date;
	}

	/** ���������� ������ �������� �� ������� ������������ */
	public void setListOfAspects(ArrayList<DoublePlanet> listOfAspects) {
		aspects=listOfAspects;
	}
	
	/** �������� ������ �������� �� ������� ������������  */
	public ArrayList<DoublePlanet> getListOfAspects(){
		return this.aspects;
	}
	
	/** �������� �� ������ ������������ ��������
	 * @return 
	 * <ul>
	 * 	<li><b>true</b> - �������</li>
	 * 	<li><b>false</b> - ������� </li>
	 * </ul>
	 *  */
	public boolean isMale(){
		return this.male;
	}
	
	/** ���������� ��� ������� ��������
	 * @param male
	 * <ul>
	 * 	<li><b>true</b> - �������</li>
	 * 	<li><b>false</b> - �������</li>
	 * </ul>
	 * */
	public void setMale(boolean male){
		this.male=male;
	}
}
