package com.example.tempvaadinliferay;

import com.vaadin.Application;

import com.vaadin.ui.*;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component.Event;
import com.vaadin.ui.Component.Listener;
import com.vaadin.ui.Window.Notification;


public class TempvaadinliferayApplication extends Application {
	private final static long serialVersionUID=1L;
	
	@Override
	public void init() {
		final Window mainWindow = new Window("Tempvaadinliferay Application");
		Label label = new Label("Hello Vaadin user");
		
		mainWindow.addComponent(label);
		
		Button button=new Button("click me");
		mainWindow.addComponent(button);
		button.addListener(new ClickListener() {
			public void buttonClick(ClickEvent event) {
				mainWindow.showNotification("title", "message", Notification.DELAY_FOREVER&Notification.TYPE_HUMANIZED_MESSAGE);
			}
		});
		
		setMainWindow(mainWindow);
	}

}
