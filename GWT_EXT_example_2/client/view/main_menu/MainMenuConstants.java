package com.cherkashin.vitaliy.computer_shop.client.view.main_menu;

import com.google.gwt.i18n.client.Constants;

public interface MainMenuConstants extends Constants{
	@DefaultStringValue("Main Menu")
	public String titlePanel();
	
	@DefaultStringValue("Find commodity")
	public String buttonFindCommodity();
	
	@DefaultStringValue("Power OFF")
	public String buttonPowerOff();
	
	@DefaultStringValue(value="Currency")
	String buttonCourse();

	@DefaultStringValue(value="Commodity Assortment")
	public String commodityAssortment();
}
