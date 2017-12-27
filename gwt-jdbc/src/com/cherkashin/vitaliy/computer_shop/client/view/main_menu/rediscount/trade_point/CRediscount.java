package com.cherkashin.vitaliy.computer_shop.client.view.main_menu.rediscount.trade_point;

import com.google.gwt.i18n.client.Constants;

public interface CRediscount extends Constants{
	
	@DefaultStringValue(value="server exchange Error")
	public String serverExchangeError();
	
	@DefaultStringValue(value="Close")
	public String buttonClose();
	
	@DefaultStringValue(value="BarCode")
	public String barcodeTitle();
	
	@DefaultStringValue(value="Fixed")
	public String barcodeButton();

	@DefaultStringValue(value="Check bar code")
	public String checkBarCodeError();

	@DefaultStringValue(value="The data of rediscount is exists ")
	public String rediscountExists();

	@DefaultStringValue(value="Are you have continue rediscount ?")
	public String questionRediscountContinue();
	
	@DefaultStringValue(value="Are you sure remove all Rediscount data ?")
	public String questionAboutDeleteAllData();

	@DefaultStringValue(value="create rediscount error")
	public String createRediscountError();
	
}
