package com.cherkashin.vitaliy.computer_shop.client.view.main_menu.rediscount;

import java.util.HashMap;


import java.util.Iterator;

import com.cherkashin.vitaliy.computer_shop.client.utility.RootComposite;
import com.cherkashin.vitaliy.computer_shop.client.view.main_menu.MainMenu;
import com.cherkashin.vitaliy.computer_shop.client.view.main_menu.rediscount.trade_point.IRediscount;
import com.cherkashin.vitaliy.computer_shop.client.view.main_menu.rediscount.trade_point.IRediscountAsync;
import com.cherkashin.vitaliy.computer_shop.client.view.main_menu.rediscount.trade_point.Rediscount;
import com.extjs.gxt.ui.client.Style.Scroll;
import com.extjs.gxt.ui.client.widget.Dialog;
import com.extjs.gxt.ui.client.widget.HorizontalPanel;
import com.extjs.gxt.ui.client.widget.Label;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.MessageBox;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ListBox;
import com.extjs.gxt.ui.client.widget.layout.VBoxLayout;
import com.extjs.gxt.ui.client.widget.layout.VBoxLayout.VBoxLayoutAlign;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;

public class ChoiceTradePoint extends LayoutContainer{
	private CChoiceTradePoint constants=GWT.create(CChoiceTradePoint.class);
	private IGetPointsAsync manager=GWT.create(IGetPoints.class);
	private ListBox comboboxPoints=new ListBox(false);
	private MessageBox waitMessageBox=null;
	private IRediscountAsync managerRediscount=GWT.create(IRediscount.class);
	
	public ChoiceTradePoint(){
		// load values to combobox
		this.loadValuesToCombobox();
		this.initComponents();
	}
	
	private void loadValuesToCombobox(){
		waitMessageBox=MessageBox.wait("", "Server Exchange", "wait... ");
		this.manager.getPoints(new AsyncCallback<HashMap<Integer,String>>() {
			@Override
			public void onSuccess(HashMap<Integer,String> result) {
				waitMessageBox.close();
				onPointsLoad(result);
			}
			@Override
			public void onFailure(Throwable caught) {
				waitMessageBox.close();
				MessageBox.alert("Error", "Server Exchange Error", null);
			}
		});
		
	}
	
	private void onPointsLoad(HashMap<Integer,String> result){
		this.comboboxPoints.clear();
		Iterator<Integer> keys=result.keySet().iterator();
		while(keys.hasNext()){
			Integer key=keys.next();
			if(key!=null){
				this.comboboxPoints.addItem(result.get(key), key.toString());
			}
		}
	}
	
	/*
	 * private Map<String, Object> convertHashMap(HashMap<Integer, String> input){
		HashMap<String,Object> returnValue=new HashMap<String,Object>();
		Iterator<Integer> iterator=input.keySet().iterator();
		while(iterator.hasNext()){
			Integer key=iterator.next();
			if(key!=null){
				returnValue.put(key.toString(), input.get(key));
			}
		}
		return returnValue;
	}
	 */
	
	/** init components  */
	private void initComponents(){
		VBoxLayout layout=new VBoxLayout();
		layout.setVBoxLayoutAlign(VBoxLayoutAlign.CENTER);
		this.setLayout(layout);
		
		this.setWidth(RootComposite.getWindowWidth());
		this.setHeight(RootComposite.getWindowHeight());
		
		this.add(new Label(this.constants.title()));
		
		// LayoutContainer container=new LayoutContainer(new HBoxLayout());
		HorizontalPanel container=new HorizontalPanel();
		this.add(container);
		container.setBorders(true);
		
		comboboxPoints.setTitle(constants.captionComboboxPoints());
		container.add(comboboxPoints);
		Button buttonOpen=new Button(constants.captionButtonOpenPoint());
		buttonOpen.addClickHandler(new ClickHandler(){
			@Override
			public void onClick(ClickEvent event) {
				onButtonOpen();
			}
		});
		buttonOpen.setWidth("100%");
		container.add(buttonOpen);
		
		Button buttonClose=new Button(constants.captionButtonClose());
		buttonClose.setWidth("200px");
		this.add(buttonClose);
		buttonClose.addClickHandler(new ClickHandler(){
			@Override
			public void onClick(ClickEvent event) {
				onButtonClose();
			}
		});
	}
	
	/** button open click */
	private void onButtonOpen(){
		// проверить торговую точку на наличие в ней по текущему дню переучета
			// переучет в процессе - задать вопрос по поводу продолжения или начала нового 
			// переучет еще не создан - создать
		// System.out.println("Open>>> "+comboboxPoints.getValue(comboboxPoints.getSelectedIndex()));
		// System.out.println("Open>>> "+comboboxPoints.getItemText(comboboxPoints.getSelectedIndex()));
		try{
			int pointKod=Integer.parseInt(comboboxPoints.getValue(comboboxPoints.getSelectedIndex()));
			checkPoint(pointKod);
		}catch(Exception ex){
			System.err.println("ChoiceTradePoint get point number Exception:"+ex.getMessage());
			MessageBox.alert("", "ERROR", null);
		}
	}
	
	/** проверить торговую точку на наличие по ней уже имеющегося переучета  */
	private void checkPoint(final int pointKod){
		this.waitMessageBox=MessageBox.wait("", "Server Exchange", "wait...");
		managerRediscount.isRediscountExists(pointKod, new AsyncCallback<Boolean>(){
			@Override
			public void onFailure(Throwable caught) {
				ChoiceTradePoint.this.waitMessageBox.close();
				MessageBox.alert("Error", ChoiceTradePoint.this.constants.serverExchangeError(), null);
			}
			@Override
			public void onSuccess(Boolean result) {
				ChoiceTradePoint.this.waitMessageBox.close();
				if(result==true){
					// существует - продолжить ?
					 final Dialog simple = new Dialog();  
					 simple.setHeading(constants.rediscountExists());  
					 simple.setButtons(Dialog.YESNO);  
					 // simple.setBodyStyleName("pad-text");  
					 simple.addText(constants.questionRediscountContinue());  
					 simple.setScrollMode(Scroll.AUTO);  
					 simple.setHideOnButtonClick(true);
					 simple.show();
					 System.out.println("Exception - Dialog is not MODAL ");
					 System.out.println("Buttons: "+simple.getButtons());
					 System.out.println("ButtonBar: "+simple.getButtonBar());
					 if(simple.getButtonBar().equals("yes")){
						 // yes
						 // пользователь желает продолжить переучет
						 RootComposite.showView(new Rediscount(pointKod));
					 }else{
						 // no
						 // пользователь желает удалить данные и создать новый переучет
						 createRediscount(pointKod);
					 }
				}else{
					// не существует - создать переучет
					createRediscount(pointKod);
				}
			}
		});
	}
	
	/** создать новый переучет по данной торговой точке:  */
	private void createRediscount(final int pointKod){
		waitMessageBox=MessageBox.wait("", "Server exchange", "wait...");
		 managerRediscount.createRediscount(pointKod, new AsyncCallback<Boolean>() {
				@Override
				public void onFailure(Throwable caught) {
					ChoiceTradePoint.this.waitMessageBox.close();
					MessageBox.alert("Error", ChoiceTradePoint.this.constants.serverExchangeError(), null);
				}
				@Override
				public void onSuccess(Boolean result) {
					ChoiceTradePoint.this.waitMessageBox.close();
					if(result==true){
						// успешно создан
						RootComposite.showView(new Rediscount(pointKod));
					}else{
						// ошибка создания переучета 
						ChoiceTradePoint.this.waitMessageBox.close();
						MessageBox.alert("Error", ChoiceTradePoint.this.constants.createRediscountError(), null);
					}
				}
			});
	}
	
	/** close this window */
	private void onButtonClose(){
		RootComposite.showView(new MainMenu());
	}
}
