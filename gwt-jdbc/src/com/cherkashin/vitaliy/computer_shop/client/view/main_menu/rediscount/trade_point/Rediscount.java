package com.cherkashin.vitaliy.computer_shop.client.view.main_menu.rediscount.trade_point;

import com.cherkashin.vitaliy.computer_shop.client.utility.RootComposite;
import com.cherkashin.vitaliy.computer_shop.client.view.main_menu.rediscount.ChoiceTradePoint;
import com.extjs.gxt.ui.client.Style.Scroll;
import com.extjs.gxt.ui.client.event.ComponentEvent;
import com.extjs.gxt.ui.client.event.KeyListener;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.Dialog;
import com.extjs.gxt.ui.client.widget.Label;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.MessageBox;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.layout.HBoxLayout;
import com.extjs.gxt.ui.client.widget.layout.VBoxLayout;
import com.extjs.gxt.ui.client.widget.layout.HBoxLayout.HBoxLayoutAlign;
import com.extjs.gxt.ui.client.widget.layout.VBoxLayout.VBoxLayoutAlign;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;

/** Переучет  */
public class Rediscount extends LayoutContainer{
	private int pointKod=0;
	private MessageBox waitMessageBox=null;
	private IRediscountAsync manager=GWT.create(IRediscount.class);
	private CRediscount constants=GWT.create(CRediscount.class);
	private TextField<String> textBarCode=new TextField<String>();
	/** имя стиля, который подразумевает отображение ошибки ввода в текстовое поле*/
	private static final String styleTextError="text_error";
	/** имя стиля, который подразумевает валдный вод в текстовое поле ( либо же нейтральное состояние ) */
	private static final String styleTextClear="text_clear";
	
	/** Переучет
	 * @param pointKod - код точки, по которой создается переучет
	 */
	public Rediscount(int pointKod){
		this.pointKod=pointKod;
		this.initComponents();
	}
	
	
	/** инициализация компонентов при ошибке обмена с сервером - только кнопка закрыть */
	private void initComponentsError(){
		VBoxLayout layout=new VBoxLayout();
		layout.setVBoxLayoutAlign(VBoxLayoutAlign.CENTER);
		this.setLayout(layout);
		this.setWidth(RootComposite.getWindowWidth());
		this.setHeight(RootComposite.getWindowHeight());
		
		Button buttonClose=new Button(this.constants.buttonClose());
		this.add(buttonClose);
		buttonClose.addClickHandler(new ClickHandler(){
			@Override
			public void onClick(ClickEvent event) {
				onButtonClose();
			}
		});
		this.add(new Label("temp"));
		
	}
	
	/** инициализация визуальных компонентов */
	private void initComponents(){
		HBoxLayout layout=new HBoxLayout();
		layout.setHBoxLayoutAlign(HBoxLayoutAlign.TOP);
		this.setLayout(layout);
		this.setWidth(RootComposite.getWindowWidth());
		this.setHeight(RootComposite.getWindowHeight());
		
		// fill panel Left
		ContentPanel panelLeft=new ContentPanel();
		VBoxLayout leftLayout=new VBoxLayout();
		leftLayout.setVBoxLayoutAlign(VBoxLayoutAlign.CENTER);
		panelLeft.setLayout(leftLayout);
		this.add(panelLeft);
		textBarCode.setTitle(constants.barcodeTitle());
		textBarCode.addKeyListener(new KeyListener(){
			@Override
			public void componentKeyUp(ComponentEvent event) {
				if(event.getKeyCode()==13){
					onButtonFixed();
				}
			}
		});
		panelLeft.add(textBarCode);
		Button buttonFixed=new Button(constants.buttonClose());
		buttonFixed.addClickHandler(new ClickHandler(){
			@Override
			public void onClick(ClickEvent event) {
				onButtonFixed();
			}
		});
		
		// fill panel Right
		ContentPanel panelRight=new ContentPanel();
		VBoxLayout rightLayout=new VBoxLayout();
		leftLayout.setVBoxLayoutAlign(VBoxLayoutAlign.CENTER);
		panelRight.setLayout(rightLayout);
		this.add(panelRight);
	}
	
	private AsyncCallback<Boolean> saveBarCode=new AsyncCallback<Boolean>(){
		@Override
		public void onFailure(Throwable caught) {
			saveBarCodeFailure();
		}
		@Override
		public void onSuccess(Boolean result) {
			saveBarCodeSuccess(result);
		}
	};
	
	/** отправка кода на сервер не удалась  */
	private void saveBarCodeFailure(){
		this.waitMessageBox.close();
		MessageBox.alert("Error", "Server does not response", null);
	}
	
	private void saveBarCodeSuccess(Boolean result){
		if(result==false){
			this.waitMessageBox.close();
			MessageBox.alert("", this.constants.checkBarCodeError(), null);
			this.textBarCode.setStyleName(styleTextError);
		}else{
			// FIXME место записи в лог последний записанных элементов
			this.textBarCode.setValue(null);
			if(this.textBarCode.getStyleName().equals(styleTextError))
			this.textBarCode.setStyleName(styleTextClear);
		}
	}
	
	/** реакция нажатия на кнопку "зафиксировать товар" */
	private void onButtonFixed(){
		String value=this.textBarCode.getValue();
		if((value==null)||(value.trim().equals(""))){
			// nothing
		}else{
			this.waitMessageBox=MessageBox.wait("", "Server exchange", "wait...");
			// проверить
			manager.saveBarCode(this.pointKod, this.removeFirstZero(value), saveBarCode);
			// where.append("rupper(SERIAL.NUMBER) like '%"+barCode.toUpperCase()+"%'\n ");
		}
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
	
	
	private void onButtonClose(){
		RootComposite.showView(new ChoiceTradePoint());
	}
}
