package com.cherkashin.vitaliy.computer_shop.client.view.main_menu.course;

import com.cherkashin.vitaliy.computer_shop.client.utility.RootComposite;
import com.cherkashin.vitaliy.computer_shop.client.view.main_menu.MainMenu;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.gwtext.client.widgets.MessageBox;
import com.gwtext.client.widgets.MessageBoxConfig;
import com.gwtext.client.widgets.Panel;
import com.gwtext.client.widgets.WaitConfig;
import com.gwtext.client.widgets.form.NumberField;
import com.gwtext.client.widgets.layout.HorizontalLayout;
import com.gwtext.client.widgets.layout.VerticalLayout;

public class Course extends Composite{
	private NumberField editCourse;
	private CourseConstants constants=GWT.create(CourseConstants.class);
	private ICourseManagerAsync courseManager=GWT.create(ICourseManager.class);
	/** шаг, на которые следует увеличивать или уменьшать значение  */
	private float step=0.05f;
	
	public Course(){
		Panel panelMain=new Panel();
		panelMain.setTitle(constants.title());
		panelMain.setLayout(new VerticalLayout(5));
		panelMain.setBorder(true);
		
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
		
		Panel panelEdit=new Panel();
		panelEdit.setHeight(25);
		panelMain.add(panelEdit);
		panelEdit.setLayout(new HorizontalLayout(5));
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
		Panel panelManager=new Panel();
		panelMain.add(panelManager);
		panelManager.setLayout(new HorizontalLayout(5));
		panelManager.add(buttonSave);
		panelManager.add(buttonCancel);
		panelManager.setBorder(false);
		
		initWidget(panelMain);
		
		
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
			MessageBox.show(new MessageBoxConfig(){
				{
					this.setWait(false);
					this.setModal(true);
					this.setClosable(true);
					this.setMsg("Check value");
				}
			});
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
		MessageBox.show(new MessageBoxConfig(){
			{
				this.setModal(true);
				this.setWait(true);
				this.setWaitConfig(new WaitConfig(){
					{
						this.setInterval(200);
					}
				});
				this.setProgressText("server exchange");
			}
		});
		courseManager.getCurrencyValue(new AsyncCallback<Float>(){
			@Override
			public void onFailure(Throwable caught) {
				MessageBox.hide();
				serverExchangeError();
			}
			@Override
			public void onSuccess(Float result) {
				MessageBox.hide();
				if(result==null){
					serverExchangeError();
				}else{
					editCourse.setValue(result);
				}
			}
		});
	}
	
	private void serverExchangeError(){
		MessageBox.show(new MessageBoxConfig(){
			{
				this.setWait(false);
				this.setModal(true);
				this.setClosable(true);
				this.setMsg("Server exchange Error");
			}
		});
	}
	
	/** сохранить указанное значение курса  */
	private void setCurrentValue(float value){
		this.courseManager.setCurrencyValue(value, new AsyncCallback<Boolean>(){
			@Override
			public void onFailure(Throwable caught) {
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
					MessageBox.show(new MessageBoxConfig(){
						{
							this.setWait(false);
							this.setModal(true);
							this.setClosable(true);
							this.setMsg("Save error");
						}
					});
				}
			}
		});
	}
}	
