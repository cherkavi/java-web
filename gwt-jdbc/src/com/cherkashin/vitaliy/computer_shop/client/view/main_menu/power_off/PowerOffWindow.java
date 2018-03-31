package com.cherkashin.vitaliy.computer_shop.client.view.main_menu.power_off;

import com.extjs.gxt.ui.client.Style.HorizontalAlignment;

import com.extjs.gxt.ui.client.widget.HorizontalPanel;
import com.extjs.gxt.ui.client.widget.MessageBox;
import com.extjs.gxt.ui.client.widget.VerticalPanel;
import com.extjs.gxt.ui.client.widget.Viewport;
import com.extjs.gxt.ui.client.widget.Window;
import com.extjs.gxt.ui.client.widget.MessageBox.MessageBoxType;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.extjs.gxt.ui.client.widget.layout.VBoxLayout;
import com.extjs.gxt.ui.client.widget.layout.VBoxLayoutData;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;

public class PowerOffWindow extends Window{
	private IPowerOffAsync manager=GWT.create(IPowerOff.class);
	private PowerOffConstants constants=GWT.create(PowerOffConstants.class);
	private TextField<String> textPassword=null;
	
	public PowerOffWindow(){
        // panel password
        VerticalPanel panelPassword=new VerticalPanel();
        Label label=new Label(constants.question());
        panelPassword.setWidth("100%");
        panelPassword.setHeight("40px");
        panelPassword.add(label);
        textPassword=new TextField<String>();
        textPassword.setPassword(true);
        panelPassword.add(textPassword);
        
        Button buttonOk=new Button(constants.buttonOk());
        buttonOk.addClickHandler(new ClickHandler(){
			@Override
			public void onClick(ClickEvent event) {
				onButtonOk();
			}
        });
        
        Button buttonCancel=new Button(constants.buttonCancel());
        buttonCancel.addClickHandler(new ClickHandler(){
			@Override
			public void onClick(ClickEvent event) {
				onButtonCancel();
			}
        });
        
        // panel manager
        HorizontalPanel panelManager=new HorizontalPanel();
        panelManager.setHorizontalAlign(HorizontalAlignment.CENTER);
        panelManager.setWidth("100%");
        panelManager.setHeight("40px");
        panelManager.add(buttonOk);
        panelManager.add(buttonCancel);
        
        this.setClosable(true);  
        this.setWidth(250);  
        this.setHeight(120);  
        this.setPlain(true);  
        this.setLayout(new FitLayout());
        this.setResizable(true);
        this.setClosable(true);
        this.setModal(true);
        
        Viewport viewport=new Viewport();
        viewport.setAutoWidth(true);
        viewport.setLayout(new VBoxLayout());
        viewport.add(panelPassword, new VBoxLayoutData());
        viewport.add(panelManager,new VBoxLayoutData());
        this.add(viewport);
        this.show();
	}
	
	private void onButtonOk(){
		String password=this.textPassword.getValue();
		if((password!=null)&&(password.length()<20)){
			final MessageBox windowWait=new MessageBox();
			// windowWait.setTitle(title);
			// windowWait.setMessage(msg);
			windowWait.setProgressText("ServerExchange");
			windowWait.setType(MessageBoxType.WAIT);
			windowWait.show();
			
			this.manager.reboot(password, new AsyncCallback<String>(){
				@Override
				public void onFailure(Throwable caught) {
					windowWait.close();
					MessageBox.alert("", "Server exchange ERROR", null);
				}
				@Override
				public void onSuccess(String result) {
					windowWait.close();
					if(result==null){
						MessageBox.alert("", PowerOffWindow.this.constants.passwordError(), null);
					}else{
						MessageBox.alert("", PowerOffWindow.this.constants.reboot(), null);
						PowerOffWindow.this.hide();
					}
				}
			});
		}
	}
	
	private void onButtonCancel(){
		PowerOffWindow.this.hide();
	}
}
