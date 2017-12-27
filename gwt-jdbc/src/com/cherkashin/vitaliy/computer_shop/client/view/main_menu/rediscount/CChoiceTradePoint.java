package com.cherkashin.vitaliy.computer_shop.client.view.main_menu.rediscount;

import com.google.gwt.i18n.client.Constants;

public interface CChoiceTradePoint extends Constants{
	@DefaultStringValue(value="Close")
	public String captionButtonClose();

	@DefaultStringValue(value="Points")
	public String captionComboboxPoints();

	@DefaultStringValue(value="Open")
	public String captionButtonOpenPoint();

	@DefaultStringValue(value="Rediscount for Point:")
	public String title();

	@DefaultStringValue(value="Server exchange Error")
	public String serverExchangeError();

	@DefaultStringValue(value="The rediscount is exists")
	public String rediscountExists();

	@DefaultStringValue(value="Do you have continue rediscount (yes) or create new (no) ?")
	public String questionRediscountContinue();
	
	@DefaultStringValue(value="Create rediscount ERROR ")
	public String createRediscountError();
}
