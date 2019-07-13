package com.cherkashin.vitaliy.computer_shop.client.view.main_menu.power_off;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface IPowerOffAsync {
	void reboot(String password, AsyncCallback<String> callback);
}
