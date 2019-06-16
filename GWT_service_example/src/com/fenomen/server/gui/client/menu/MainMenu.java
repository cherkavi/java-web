package com.fenomen.server.gui.client.menu;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DecoratorPanel;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

public class MainMenu extends Composite{
	public MainMenu(){
		// create element's
		Button buttonModule = new Button("Module");
		Button buttonInfo=new Button("Info");
		Label labelMenu=new Label("Menu",false);
		labelMenu.addStyleName("MenuCaption");
		
		// add listener's
		buttonModule.addClickHandler(new ClickHandler(){
			@Override
			public void onClick(ClickEvent event) {
				onButtonModule();
			}
		});
		buttonInfo.addClickHandler(new ClickHandler(){
			@Override
			public void onClick(ClickEvent event) {
				onButtonInfo();
			}
		});
		
		// placing element's
		VerticalPanel panel=new VerticalPanel();
		panel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		panel.addStyleName("MainMenu");
		panel.add(labelMenu);
		panel.add(buttonModule);
		panel.add(buttonInfo);
		
		DecoratorPanel decorator=new DecoratorPanel();
		decorator.add(panel);
		buttonModule.setFocus(true);

		initWidget(decorator);
	}
	
	/** реакция на нажатие клавиши Module */
	private void onButtonModule(){
		// TODO 
		System.out.println("on module click");
	}
	
	/** реакция на нажатие клавиши Info */
	private void onButtonInfo(){
		// TODO 
		System.out.println("on button info click ");
	}
	
}
