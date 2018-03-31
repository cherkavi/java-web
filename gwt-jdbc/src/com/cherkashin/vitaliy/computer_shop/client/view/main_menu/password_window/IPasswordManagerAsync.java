package com.cherkashin.vitaliy.computer_shop.client.view.main_menu.password_window;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface IPasswordManagerAsync {
	/** проверить пароль 
	 * @param password - пароль
	 * @param 
	 * <ul>
	 * 	<li><b>true</b> - пароль положительно опознан </li>
	 * 	<li><b>false</b> - пароль не опознан </li>
	 * </ul>
	 *  */
	void checkPassword(String password, AsyncCallback<Boolean> callback);

}
