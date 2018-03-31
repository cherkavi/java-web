package com.cherkashin.vitaliy.computer_shop.client.view.main_menu.course;

import com.cherkashin.vitaliy.computer_shop.client.utility.RootComposite;
import com.cherkashin.vitaliy.computer_shop.client.view.main_menu.MainMenu;
import com.extjs.gxt.ui.client.Style.HorizontalAlignment;
import com.extjs.gxt.ui.client.widget.Label;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.MessageBox;
import com.extjs.gxt.ui.client.widget.VerticalPanel;
import com.extjs.gxt.ui.client.widget.form.NumberField;
import com.extjs.gxt.ui.client.widget.layout.HBoxLayout;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;

public class Course extends VerticalPanel{
	private NumberField editCourse;
	private CourseConstants constants=GWT.create(CourseConstants.class);
	private ICourseManagerAsync courseManager=GWT.create(ICourseManager.class);
	/** шаг, на которые следует увеличивать или уменьшать значение  */
	private float step=0.05f;
	
	public Course(){
		VerticalPanel panelMain=new VerticalPanel();
		panelMain.setHorizontalAlign(HorizontalAlignment.CENTER);
		panelMain.setBorders(true);
		panelMain.add(new Label(constants.title()));
		// panelMain.setLayout(new VBoxLayout());
		// panelMain.setBodyBorder(true);
		
		// элемент для редактирования 
		editCourse=new NumberField();
		editCourse.setAllowNegative(false);
		editCourse.setAllowDecimals(true);
		editCourse.setWidth(50);
		
		/** increase */
		Button buttonUp=new Button("+");
		// buttonUp.setHeight("15px");
		buttonUp.addClickHandler(new ClickHandler(){
			@Override
			public void onClick(ClickEvent event) {
				onButtonUp();
			}
		});
		
		/** descrease */
		Button buttonDown=new Button("-");
		//buttonDown.setHeight("15px");
		buttonDown.addClickHandler(new ClickHandler(){
			@Override
			public void onClick(ClickEvent event) {
				onButtonDown();
			}
		});
		
		LayoutContainer panelEdit=new LayoutContainer();
		panelEdit.setHeight(30);
		panelEdit.setWidth(100);
		panelMain.add(panelEdit);
		panelEdit.setLayout(new HBoxLayout());
		panelEdit.add(editCourse);
		panelEdit.add(buttonUp);
		panelEdit.add(buttonDown);
		// кнопка сохранения 
		Button buttonSave=new Button(constants.buttonSave());
		buttonSave.addClickHandler(new ClickHandler(){
			@Override
			public void onClick(ClickEvent event) {
				onButtonSave();
			}
		});
		// кнопка отмены 
		Button buttonCancel=new Button(constants.buttonCancel());
		buttonCancel.addClickHandler(new ClickHandler(){
			@Override
			public void onClick(ClickEvent event){
				onButtonCancel();
			}
		});
		// панель управления 
		LayoutContainer panelManager=new LayoutContainer();
		panelManager.setHeight(30);
		panelManager.setWidth(200);
		panelMain.add(panelManager);
		panelManager.setLayout(new HBoxLayout());
		panelManager.add(buttonSave);
		panelManager.add(buttonCancel);
		// panelManager.setBodyBorder(false);
		
		this.add(panelMain);
		
		this.setSize("200px", RootComposite.getWindowHeight()+"100px");
		panelMain.setSize(200, 100);
		this.getCurrencyValue();
	}
	
	private  void onButtonCancel(){
		RootComposite.showView(new MainMenu());
	}
	
	private void onButtonSave(){
		// проверить указанное значение на валидность
		// сохранить курс валюты
		try{
			float value=this.editCourse.getValue().floatValue();
			this.setCurrentValue(value);
		}catch(Exception ex){
			MessageBox.alert("", "Check value", null);
		}
	}

	/** нажатие на кнопку повышения цены */
	private void onButtonUp(){
		try{
			float value=this.editCourse.getValue().floatValue();
			value+=step;
			this.editCourse.setValue(value);
		}catch(Exception ex){}
	}
	
	/** нажатие на кнопку понижения цены  */
	private void onButtonDown(){
		try{
			float value=this.editCourse.getValue().floatValue();
			value-=step;
			this.editCourse.setValue(value);
		}catch(Exception ex){}
	}

	/** получить текущее значение курса  */
	private void getCurrencyValue(){
		final MessageBox waitMessage=new MessageBox();
		waitMessage.setProgressText("Server Exchange");
		waitMessage.show();
		courseManager.getCurrencyValue(new AsyncCallback<Float>(){
			@Override
			public void onFailure(Throwable caught) {
				try{
					waitMessage.close();
				}catch(Exception ex){};
				
				serverExchangeError();
			}
			@Override
			public void onSuccess(Float result) {
				waitMessage.close();
				if(result==null){
					serverExchangeError();
				}else{
					editCourse.setValue(result);
				}
			}
		});
	}
	
	private void serverExchangeError(){
		MessageBox.alert("", "Server Exchange Error", null);
	}
	
	/** сохранить указанное значение курса  */
	private void setCurrentValue(float value){
		this.courseManager.setCurrencyValue(value, new AsyncCallback<Boolean>(){
			@Override
			public void onFailure(Throwable caught) {
				MessageBox.alert("", "Save Failure", null);
			}
			@Override
			public void onSuccess(Boolean result) {
				if(result==null){
					serverExchangeError();
				}
				if(result==true){
					// данные успешно сохранены 
					RootComposite.showView(new MainMenu());
				}else{
					MessageBox.alert("", "Save Error", null);
				}
			}
		});
	}
}	
