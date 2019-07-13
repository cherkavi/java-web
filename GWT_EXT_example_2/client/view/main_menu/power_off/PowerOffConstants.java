package com.cherkashin.vitaliy.computer_shop.client.view.main_menu.power_off;

import com.google.gwt.i18n.client.Constants;

public interface PowerOffConstants extends Constants{

	@DefaultStringValue(value="Are you sure to SERVER POWER OFF ?")
	String question();

	@DefaultStringValue(value="Reboot")
	String buttonOk();

	@DefaultStringValue(value="Cancel")
	String buttonCancel();

	@DefaultStringValue(value="Password Error")
	String passwordError();
	
	@DefaultStringValue(value="REBOOT")
	String reboot();
	
}
