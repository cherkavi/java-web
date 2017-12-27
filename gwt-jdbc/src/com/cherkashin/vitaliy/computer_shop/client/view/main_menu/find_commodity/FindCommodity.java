package com.cherkashin.vitaliy.computer_shop.client.view.main_menu.find_commodity;

import com.cherkashin.vitaliy.computer_shop.client.utility.RootComposite;


import com.cherkashin.vitaliy.computer_shop.client.view.main_menu.MainMenu;
import com.extjs.gxt.ui.client.Style.LayoutRegion;
import com.extjs.gxt.ui.client.Style.Orientation;
import com.extjs.gxt.ui.client.event.ComponentEvent;
import com.extjs.gxt.ui.client.event.KeyListener;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.HorizontalPanel;
import com.extjs.gxt.ui.client.widget.Label;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.MessageBox;
import com.extjs.gxt.ui.client.widget.VerticalPanel;
import com.extjs.gxt.ui.client.widget.MessageBox.MessageBoxType;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.layout.BorderLayout;
import com.extjs.gxt.ui.client.widget.layout.BorderLayoutData;
import com.extjs.gxt.ui.client.widget.layout.FillLayout;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Widget;

public class FindCommodity extends ContentPanel{
	private FindCommodityConstants constants=GWT.create(FindCommodityConstants.class);
	private IFindCommodityAsync findManager=GWT.create(IFindCommodity.class);
	private TextField<String> editName=new TextField<String>();
	private TextField<String> editBarCode=new TextField<String>();
	private TextField<String> editSerial=new TextField<String>();
	private TableCommodity table;
	
	public FindCommodity(){
		this.setTitle(constants.title());
		this.setLayout(new BorderLayout());
		this.setWidth(RootComposite.getWindowWidth());
		this.setHeight(RootComposite.getWindowHeight());
		Button buttonClose=new Button("close"){
			@Override
			protected void onClick(ComponentEvent ce) {
				onButtonClose();
			}
		};
		this.addButton(buttonClose);
		
		HorizontalPanel panelInput=new HorizontalPanel();
		// panelInput.setHeight(70);
		panelInput.setWidth("100%");
		panelInput.setLayout(new FitLayout());
		
		editName.addKeyListener(new KeyListener(){
			@Override
			public void componentKeyUp(ComponentEvent event) {
				if(event.getKeyCode()==13){
					onButtonFilter();
				}
			}
		});
		editName.setFieldLabel(constants.captionEditName());
		panelInput.add(this.getPanel(editName,constants.captionEditName()));

		// barCode  
		editBarCode.addKeyListener(new KeyListener(){
			@Override
			public void componentKeyUp(ComponentEvent event) {
				if(event.getKeyCode()==13){
					onButtonFilter();
				}
			}
		});
		editBarCode.setFieldLabel(constants.captionEditBarCode());
		panelInput.add(this.getPanel(editBarCode,constants.captionEditBarCode()));

		// Serial 
		editSerial.addKeyListener(new KeyListener(){
			@Override
			public void componentKeyUp(ComponentEvent event) {
				if(event.getKeyCode()==13){
					onButtonFilter();
				}
			}
		});
		editSerial.setFieldLabel(constants.captionEditSerial());
		panelInput.add(this.getPanel(editSerial, constants.captionEditSerial()));
		
		Button buttonFilter=new Button(constants.captionButtonFilter()){
			protected void onClick(ComponentEvent ce) {
				onButtonFilter();
			};
		};
		buttonFilter.setHeight(25);
		buttonFilter.setWidth(RootComposite.getWindowWidth()-5);
		buttonFilter.setAutoWidth(false);
		HorizontalPanel panelButtonFilter=new HorizontalPanel();
		panelButtonFilter.setLayout(new FillLayout(Orientation.HORIZONTAL));
		panelButtonFilter.add(buttonFilter);
		
		/** панель, содержащая панель управления и кнопку  */
		LayoutContainer panelFilter=new LayoutContainer();
		panelFilter.setTitle(constants.title());
		// panelFilter.setHeaderVisible(false);
		panelFilter.setWidth("100%");
		panelFilter.setLayout(new BorderLayout());
		panelFilter.add(panelInput,new BorderLayoutData(LayoutRegion.CENTER));
		panelFilter.add(panelButtonFilter, new BorderLayoutData(LayoutRegion.SOUTH,30));
		// panelFilter.add(panelInput);
		// panelFilter.add(panelButtonFilter);
		panelFilter.setHeight(70);
		panelFilter.setWidth("100%");
		
		this.add(panelFilter, new BorderLayoutData(LayoutRegion.NORTH,70));
		table=new TableCommodity();
		this.add(table, new BorderLayoutData(LayoutRegion.CENTER));
		
		//this.setWidth("100%");
		//this.setHeight("100%");
	}
	
	/** получить панель, которая включает в себя Текст с Widget */
	private Widget getPanel(Widget widget, String text){
		VerticalPanel panel=new VerticalPanel();
		panel.add(new Label(text));
		panel.add(widget);
		return panel;
	}
	
	/** реакция на нажатие кнопки "закрыть окно " */
	private void onButtonClose(){
		RootComposite.showView(new MainMenu());
	}
	
	/** убрать впереди стоящий 0, если он есть */
	private String removeFirstZero(String barCode){
		String returnValue=barCode;
		if(returnValue!=null){
			if(returnValue.length()>0){
				if(returnValue.substring(0, 1).equals("0")){
					returnValue=returnValue.substring(1);
				}
			}
		}
		return returnValue;
	}
	
	/** реакция на нажатие кнопки фильтр */
	private void onButtonFilter(){
		final MessageBox waitMessage=new MessageBox();
		waitMessage.setProgressText("server exchange");
		waitMessage.setType(MessageBoxType.WAIT);
		waitMessage.show();
		
		this.findManager.findCommodity(this.editName.getValue(), 
									   this.removeFirstZero(this.editBarCode.getValue()), 
									   this.editSerial.getValue(), 
									   new AsyncCallback<RowElement[]>(){
			@Override
			public void onFailure(Throwable caught) {
				waitMessage.close();
				caught.printStackTrace();
				MessageBox.alert(constants.serverExchangeError(), caught.getMessage(), null);
			}

			@Override
			public void onSuccess(RowElement[] result) {
				waitMessage.close();
				if(result.length==0){
					MessageBox.alert("","No data found",null);
				}else{
					table.updateModel(result);
				}
			}
		});
		// System.out.println("ButtonFilter");
	}
}
