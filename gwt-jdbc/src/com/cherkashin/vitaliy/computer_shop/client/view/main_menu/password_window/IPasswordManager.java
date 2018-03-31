package com.cherkashin.vitaliy.computer_shop.client.view.main_menu.password_window;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/** интерфейс для связи клиента и сервера  */
@RemoteServiceRelativePath("password_manager")
public interface IPasswordManager extends RemoteService{
	/** проверить пароль 
	 * @param password - пароль
	 * @return 
	 * <ul>
	 * 	<li><b>true</b> - пароль положительно опознан </li>
	 * 	<li><b>false</b> - пароль не опознан </li>
	 * </ul>
	 *  */
	public boolean checkPassword(String password);
}
