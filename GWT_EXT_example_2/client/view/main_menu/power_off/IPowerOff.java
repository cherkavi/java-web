package com.cherkashin.vitaliy.computer_shop.client.view.main_menu.power_off;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("power_off")
public interface IPowerOff extends RemoteService{
	/** перезагрузка, если пароль указан правильно */
	public String reboot(String password);
}
