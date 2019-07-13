package com.cherkashin.vitaliy.computer_shop.client;

import com.cherkashin.vitaliy.computer_shop.client.utility.RootComposite;
import com.cherkashin.vitaliy.computer_shop.client.view.main_menu.MainMenu;
import com.google.gwt.core.client.EntryPoint;


/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class ComputerShopCommodityExists implements EntryPoint {
	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		RootComposite.setMain("main");
		RootComposite.showView(new MainMenu());
	}
}
