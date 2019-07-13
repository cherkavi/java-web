package com.cherkashin.vitaliy.computer_shop.client.view.main_menu.commodity_assortment;

import com.cherkashin.vitaliy.computer_shop.client.utility.RootComposite;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FileUpload;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.Hidden;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteEvent;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteHandler;
import com.google.gwt.user.client.ui.FormPanel.SubmitEvent;
import com.google.gwt.user.client.ui.FormPanel.SubmitHandler;
import com.gwtext.client.core.RegionPosition;
import com.gwtext.client.widgets.Panel;
import com.gwtext.client.widgets.layout.BorderLayout;
import com.gwtext.client.widgets.layout.BorderLayoutData;
import com.gwtext.client.widgets.layout.VerticalLayout;

/** Загрузка ассортимента  */
public class CommodityAssortment extends Composite{
	private CommodityAssortmentConstants constants=GWT.create(CommodityAssortmentConstants.class);
	
	/** Загрузка ассортимента  */
	public CommodityAssortment(){
		Panel panelMain=new Panel();
		panelMain.setLayout(new BorderLayout());
		panelMain.setWidth(RootComposite.getWindowWidth());
		panelMain.setHeight(RootComposite.getWindowHeight());
		
		Panel panelGrid=new Panel();
		panelGrid.setWidth(100);
		panelGrid.setLayout(new VerticalLayout(5));
		panelGrid.add(new Label("grid stub"));
		panelMain.add(panelGrid, new BorderLayoutData(RegionPosition.CENTER));
		/*
		Panel panelManager=new Panel();
		panelMain.add(panelManager, new BorderLayoutData(RegionPosition.EAST));
		panelManager.setWidth(200);
		panelManager.setLayout(new VerticalLayout());
		panelManager.add(new Label("manager stub"));
		*/
		
		/* final FormPanel formPanel=new FormPanel();
		panelManager.add(formPanel);
		formPanel.setEncoding(FormPanel.ENCODING_MULTIPART);
		formPanel.setMethod(FormPanel.METHOD_POST);
		formPanel.setAction(GWT.getModuleBaseURL()+"CommodityAssortmentXlsUpload");
		*/
		
		/* FileUpload fileUpload=new FileUpload();
		formPanel.setWidget(fileUpload);
		// formPanel.add(fileUpload);
		fileUpload.setName("xlsFileUpload");
		*/
		
		// Hidden hiddenTextFile=new Hidden();
		// hiddenTextFile.setName("file_id");
		// hiddenTextFile.setValue("111222333444");
		// formPanel.add(hiddenTextFile);
		
		/*
		formPanel.addSubmitHandler(new SubmitHandler(){
			@Override
			public void onSubmit(SubmitEvent event) {
				// event.cancel();
				GWT.log("onSubmit active", null);
			}
		});
		
		formPanel.addSubmitCompleteHandler(new SubmitCompleteHandler(){
			@Override
			public void onSubmitComplete(SubmitCompleteEvent event) {
				Window.alert("Submit complete");
			}
		});
		
		Button buttonLoadFile=new Button(constants.captionButtonLoadFile());
		panelManager.add(buttonLoadFile);
		buttonLoadFile.addClickHandler(new ClickHandler(){
			@Override
			public void onClick(ClickEvent event) {
				formPanel.submit();
			}
		});
		
		Button buttonSave=new Button(constants.captionButtonSave());
		panelManager.add(buttonSave);
		buttonSave.addClickHandler(new ClickHandler(){
			@Override
			public void onClick(ClickEvent event) {
				GWT.log("button save click:", null);
				onButtonSave();
			}
		});
		*/
		
		initWidget(panelMain);
	}
	
	/** загрузить данные */
	private void onButtonSave(){
		
	}
}
