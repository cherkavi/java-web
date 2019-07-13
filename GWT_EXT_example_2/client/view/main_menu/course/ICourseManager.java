package com.cherkashin.vitaliy.computer_shop.client.view.main_menu.course;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/** ���������/���������� ����� �����  */
@RemoteServiceRelativePath("course_manager")
public interface ICourseManager extends RemoteService{
	/** �������� ������� �������� ����� �����  
	 * */
	public Float getCurrencyValue();
	
	/** ���������� ������� �������� ����� �����  
	 * @param value - ������� �������� ����� ������  
	 * */
	public boolean setCurrencyValue(float value);
}
