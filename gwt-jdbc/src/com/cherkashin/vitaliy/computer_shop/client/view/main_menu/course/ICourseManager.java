package com.cherkashin.vitaliy.computer_shop.client.view.main_menu.course;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/** получение/сохранение курса валют  */
@RemoteServiceRelativePath("course_manager")
public interface ICourseManager extends RemoteService{
	/** получить текущее значение курса валют  
	 * */
	public Float getCurrencyValue();
	
	/** установить текущее значение курса валют  
	 * @param value - текущее значение курса валюты  
	 * */
	public boolean setCurrencyValue(float value);
}
