package com.cherkashin.vitaliy.computer_shop.client.view.main_menu.find_commodity;

import com.cherkashin.vitaliy.computer_shop.client.utility.RootComposite;


import com.cherkashin.vitaliy.computer_shop.client.view.main_menu.MainMenu;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.gwtext.client.core.EventObject;
import com.gwtext.client.core.Function;
import com.gwtext.client.core.RegionPosition;
import com.gwtext.client.widgets.MessageBox;
import com.gwtext.client.widgets.MessageBoxConfig;
import com.gwtext.client.widgets.Panel;
import com.gwtext.client.widgets.Tool;
import com.gwtext.client.widgets.WaitConfig;
import com.gwtext.client.widgets.event.KeyListener;
import com.gwtext.client.widgets.form.TextField;
import com.gwtext.client.widgets.layout.BorderLayout;
import com.gwtext.client.widgets.layout.BorderLayoutData;
import com.gwtext.client.widgets.layout.HorizontalLayout;
import com.gwtext.client.widgets.layout.VerticalLayout;

public class FindCommodity extends Composite{
	private FindCommodityConstants constants=GWT.create(FindCommodityConstants.class);
	private IFindCommodityAsync findManager=GWT.create(IFindCommodity.class);
	private TextField editName=new TextField();
	private TextField editBarCode=new TextField();
	private TextField editSerial=new TextField();
	private TableCommodity table;
	
	public FindCommodity(){
		Panel panel=new Panel();
		panel.setTitle(constants.title());
		panel.setLayout(new BorderLayout());
		panel.setWidth(RootComposite.getWindowWidth()-10);
		panel.setHeight(RootComposite.getWindowHeight()-10);
		panel.addTool(new Tool(Tool.CLOSE,new Function() {
			@Override
			public void execute() {
				onButtonClose();
			}
		}));

		
		Panel panelName=new Panel();
		panelName.setWidth("100%");
		panelName.setLayout(new VerticalLayout());
		panelName.add(new Label(constants.captionEditName()));
		panelName.add(editName);
		editName.addKeyListener(13, new KeyListener(){
			@Override
			public void onKey(int key, EventObject e) {
				onButtonFilter();
			}
		});

		Panel panelBarCode=new Panel();
		panelBarCode.setWidth("100%");
		panelBarCode.setLayout(new VerticalLayout());
		panelBarCode.add(new Label(constants.captionEditBarCode()));
		panelBarCode.add(editBarCode);
		editBarCode.addKeyListener(13, new KeyListener(){
			@Override
			public void onKey(int key, EventObject e) {
				onButtonFilter();
			}
		});

		Panel panelSerial=new Panel();
		panelSerial.setWidth("100%");
		panelSerial.setLayout(new VerticalLayout());
		panelSerial.add(new Label(constants.captionEditSerial()));
		panelSerial.add(editSerial);
		editSerial.addKeyListener(13, new KeyListener(){
			@Override
			public void onKey(int key, EventObject e) {
				onButtonFilter();
			}
		});
		
		/** панель, содержащая элементы ввода и их заголовки */
		Panel panelEdit=new Panel();
		panelEdit.setWidth("100%");
		panelEdit.setLayout(new HorizontalLayout(5));
		panelEdit.add(panelName);
		panelEdit.add(panelBarCode);
		panelEdit.add(panelSerial);
		
		Button buttonFilter=new Button(constants.captionButtonFilter());
		buttonFilter.addClickHandler(new ClickHandler(){
			@Override
			public void onClick(ClickEvent event) {
				onButtonFilter();
			}
		});
		/** панель, содержащая панель управления и кнопку  */
		Panel panelFilter=new Panel();
		panelFilter.setWidth("100%");
		panelFilter.setLayout(new BorderLayout());
		panelFilter.add(panelEdit,new BorderLayoutData(RegionPosition.CENTER));
		panelFilter.add(buttonFilter, new BorderLayoutData(RegionPosition.SOUTH));
		panelFilter.setHeight(70);
		panel.add(panelFilter, new BorderLayoutData(RegionPosition.NORTH));
		
		table=new TableCommodity(this.constants);
		panel.add(table, new BorderLayoutData(RegionPosition.CENTER));
		
		this.initWidget(panel);
		//this.setWidth("100%");
		//this.setHeight("100%");
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
		MessageBox.show(new MessageBoxConfig(){
			{
				this.setWait(true);
				//this.setTitle("wait");
				//this.setMsg("wait for response");
				this.setProgressText("server exchange");
				this.setWidth(200);
				this.setWaitConfig(new WaitConfig(){
					{
						this.setInterval(300);
					}
				});
			}
		});
		
		this.findManager.findCommodity(this.editName.getText(), 
									   this.removeFirstZero(this.editBarCode.getText()), 
									   this.editSerial.getText(), 
									   new AsyncCallback<RowElement[]>(){
			@Override
			public void onFailure(Throwable caught) {
				caught.printStackTrace();
				MessageBox.alert(constants.serverExchangeError(),caught.getMessage());
			}

			@Override
			public void onSuccess(RowElement[] result) {
				if(result.length==0){
					MessageBox.hide();
					MessageBox.alert("No data found");
				}else{
					table.updateModel(result);
					MessageBox.hide();
				}
			}
		});
		System.out.println("ButtonFilter");
	}
}
