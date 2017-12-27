package com.cherkashin.vitaliy.computer_shop.client.view.main_menu.password_window;

import com.cherkashin.vitaliy.computer_shop.client.view.main_menu.MainMenu;
import com.extjs.gxt.ui.client.Style.Orientation;
import com.extjs.gxt.ui.client.widget.Info;
import com.extjs.gxt.ui.client.widget.Label;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.MessageBox;
import com.extjs.gxt.ui.client.widget.Window;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.Field;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.form.Validator;
import com.extjs.gxt.ui.client.widget.layout.FillLayout;
import com.extjs.gxt.ui.client.widget.layout.RowLayout;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;

/** */
public class PasswordWindow extends Window{
	private TextField<String> passwordField;
	private MainMenu manager;
	private IPasswordManagerAsync server=GWT.create(IPasswordManager.class);
	private MessageBox wait;
	
	public PasswordWindow(MainMenu manager){
		this.manager=manager;
		initComponents();
		this.setModal(true);
		this.setAutoHeight(true);
		this.setAutoWidth(true);
		this.show();
	}
	
	private void initComponents(){
		this.setLayout(new RowLayout(Orientation.VERTICAL));
		this.add(new Label("Enter Password"));
		passwordField=new TextField<String>();
		passwordField.setValidator(new Validator(){
			@Override
			public String validate(Field<?> field, String value) {
				if(value==null){
					return "Input value";
				};
				if(value.length()>25){
					return "is too big";
				}
				return null;
			}
		});
		passwordField.setPassword(true);
		this.add(passwordField);
		
		LayoutContainer container=new LayoutContainer(new FillLayout(Orientation.HORIZONTAL));
		
		Button buttonSave=new Button("Enter"){
			protected void onClick(com.extjs.gxt.ui.client.event.ComponentEvent ce) {
				onButtonOk();
			};
		};
		container.add(buttonSave);
		
		Button buttonCancel=new Button("Cancel"){
			protected void onClick(com.extjs.gxt.ui.client.event.ComponentEvent ce) {
				onButtonCancel();
			};
		};
		container.add(buttonCancel);
		
		this.add(container);
	}
	
	/** button OK */
	private void onButtonOk(){
		if(this.passwordField.validate()==true){
			this.wait=MessageBox.wait("", "Server Exchange", "wait");
			this.server.checkPassword(this.passwordField.getValue(), new AsyncCallback<Boolean>(){
				@Override
				public void onFailure(Throwable caught) {
					wait.close();
					MessageBox.alert("Error", "Server does not response", null);
				}

				@Override
				public void onSuccess(Boolean result) {
					wait.close();
					if(result==null){
						MessageBox.alert("Error", "Server does not response", null);
					}else{
						if(result==true){
							PasswordWindow.this.hide();
							manager.enterPasswordOk();
						}else{
							PasswordWindow.this.hide();
							Info.display("Notify", "Password Error");
							manager.enterPasswordError();
						}
					}
				}
			});
		}
	}
	
	/** button Cancel */
	private void onButtonCancel(){
		this.hide();
	}
	
}
