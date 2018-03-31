package com.cherkashin.vitaliy.computer_shop.client.view.main_menu.password_window;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface IPasswordManagerAsync {
	/** ��������� ������ 
	 * @param password - ������
	 * @param 
	 * <ul>
	 * 	<li><b>true</b> - ������ ������������ ������� </li>
	 * 	<li><b>false</b> - ������ �� ������� </li>
	 * </ul>
	 *  */
	void checkPassword(String password, AsyncCallback<Boolean> callback);

}
