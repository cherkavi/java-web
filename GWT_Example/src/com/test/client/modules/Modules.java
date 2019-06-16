package com.test.client.modules;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class Modules extends Composite{
	public Modules(String rootName){
		// create component's
		Button button=new Button("show modules");
		
		// add listener's
		button.addClickHandler(new ClickHandler(){
			@Override
			public void onClick(ClickEvent event) {
				System.out.println("Modules");
			}
		});
		
		// placing component's 
		VerticalPanel panel=new VerticalPanel();
		panel.add(button);
		initWidget(panel);
		RootPanel.get(rootName).add(this);

	}
}
