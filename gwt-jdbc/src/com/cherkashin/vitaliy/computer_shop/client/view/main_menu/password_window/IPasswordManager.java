package com.cherkashin.vitaliy.computer_shop.client.view.main_menu.password_window;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/** ��������� ��� ����� ������� � �������  */
@RemoteServiceRelativePath("password_manager")
public interface IPasswordManager extends RemoteService{
	/** ��������� ������ 
	 * @param password - ������
	 * @return 
	 * <ul>
	 * 	<li><b>true</b> - ������ ������������ ������� </li>
	 * 	<li><b>false</b> - ������ �� ������� </li>
	 * </ul>
	 *  */
	public boolean checkPassword(String password);
}
