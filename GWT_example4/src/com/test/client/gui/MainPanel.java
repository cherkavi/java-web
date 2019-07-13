package com.test.client.gui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.test.client.IGetSimpleObject;
import com.test.client.IGetSimpleObjectAsync;
import com.test.client.RandomService;
import com.test.client.RandomServiceAsync;
import com.test.client.gui.common.SimpleObject;

public class MainPanel extends Composite{
	private Label label;
	private RandomServiceAsync service;
	private IGetSimpleObjectAsync serviceSimpleObject;
	
	public MainPanel(RandomServiceAsync service){
		if(service==null){
			this.service=GWT.create(RandomService.class);
		}
		serviceSimpleObject=GWT.create(IGetSimpleObject.class);
		// create element's
		Button button=new Button("button");
		label=new Label("<empty>");
		// add actionlistener's
		button.addClickHandler(new ClickHandler(){
			@Override
			public void onClick(ClickEvent event) {
				onButtonClick();
			}
		});
		
		// placing component's
		VerticalPanel panel=new VerticalPanel();
		panel.add(label);
		panel.add(button);
		this.initWidget(panel);
	}
	
	private void onButtonClick(){
		System.out.println("button_click");
		/*service.getRandomString(new AsyncCallback<String>(){
			@Override
			public void onFailure(Throwable caught) {
				label.setText("error");
			}
			@Override
			public void onSuccess(String result) {
				label.setText(result);
			}
		});*/
		serviceSimpleObject.getSimpleObject(5, new AsyncCallback<SimpleObject>(){

			@Override
			public void onFailure(Throwable caught) {
				label.setText("error");
			}

			@Override
			public void onSuccess(SimpleObject result) {
				label.setText(result.toString());
			}
			
		});
	}
}
