package com.cherkashin.vitaliy.computer_shop.client.view.main_menu.find_commodity;

import com.google.gwt.i18n.client.Constants;

public interface FindCommodityConstants extends Constants{
	@DefaultStringValue("Find commodity on any Point")
	public String title();
	
	@DefaultStringValue("Filter")
	public String captionButtonFilter();
	
	@DefaultStringValue("Name")
	public String captionEditName();
	
	@DefaultStringValue("BarCode")
	public String captionEditBarCode();
	
	@DefaultStringValue("Serial")
	public String captionEditSerial();
	
	@DefaultStringValue("Server Exchange Error")
	public String serverExchangeError();

	@DefaultStringValue("Point")
	public String columnPoint();

	@DefaultStringValue("Name")
	public String columnName();

	@DefaultStringValue("BarCode")
	public String columnBarCode();

	@DefaultStringValue("Serial")
	public String columnSerial();

	@DefaultStringValue("Price")
	public String columnPrice();
}
