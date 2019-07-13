package com.example.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class GWTexample implements EntryPoint {

	@Override
	public void onModuleLoad() {
		// create element's
		RootPanel panel=RootPanel.get();
		final Label label=new Label("this is label");
		Button button=new Button("this is button");
		Button button2=new Button("this is another button");
		// add listener's
		button.addMouseDownHandler(new MouseDownHandler(){
			@Override
			public void onMouseDown(MouseDownEvent event) {
				label.setText("click to button");
			}
		});
		button2.addMouseDownHandler(new MouseDownHandler(){
			@Override
			public void onMouseDown(MouseDownEvent event) {
				label.setText("click to button2");
			}
		});
		
		// placing
		panel.add(label);
		panel.add(button);
		panel.add(button2);
	}
}
