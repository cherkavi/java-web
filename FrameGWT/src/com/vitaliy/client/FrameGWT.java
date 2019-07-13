package com.vitaliy.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.dev.util.msg.Message;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class FrameGWT implements EntryPoint {
	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		Panel panel=new VerticalPanel();
		panel.setSize("300px", "200px");
		RootPanel.get("main").add(panel);
		
		Label label=new Label("This is GWT Example");
		panel.add(label);
		Button button=new Button("Click me");
		panel.add(button);
		button.addClickHandler(new ClickHandler(){
			@Override
			public void onClick(ClickEvent event) {
				getDialogBox().show();
			}
		});
	}
	
	private DialogBox getDialogBox(){
		final DialogBox returnValue=new DialogBox();
		returnValue.setTitle("this is title");
		returnValue.setSize("150px", "100px");
		VerticalPanel panelContent=new VerticalPanel();
		returnValue.setWidget(panelContent);
		
		panelContent.add(new Label("This is example"));
		Button buttonClose=new Button("Close");
		buttonClose.addClickHandler(new ClickHandler(){
			@Override
			public void onClick(ClickEvent event) {
				returnValue.hide();
			}
		});
		panelContent.add(buttonClose);
		returnValue.center();
		return returnValue;
	}
}
