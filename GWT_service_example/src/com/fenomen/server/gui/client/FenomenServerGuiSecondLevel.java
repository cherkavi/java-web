package com.fenomen.server.gui.client;

import com.fenomen.server.gui.client.menu.MainMenu;
import com.google.gwt.core.client.EntryPoint;

import com.google.gwt.user.client.ui.RootPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class FenomenServerGuiSecondLevel implements EntryPoint {

	/*
	 * Create a remote service proxy to talk to the server-side Greeting service.
	private final GreetingServiceAsync greetingService = GWT.create(GreetingService.class);
	 */

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		final MainMenu mainMenu=new MainMenu();
		RootPanel.get("main_frame").add(mainMenu);		
	}
	
}
