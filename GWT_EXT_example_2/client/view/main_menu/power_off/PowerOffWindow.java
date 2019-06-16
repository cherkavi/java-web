package com.cherkashin.vitaliy.computer_shop.client.view.main_menu.power_off;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.gwtext.client.core.RegionPosition;
import com.gwtext.client.widgets.MessageBox;
import com.gwtext.client.widgets.MessageBoxConfig;
import com.gwtext.client.widgets.WaitConfig;
import com.gwtext.client.widgets.Window;
import com.gwtext.client.widgets.form.TextField;
import com.gwtext.client.widgets.layout.BorderLayout;
import com.gwtext.client.widgets.layout.BorderLayoutData;

public class PowerOffWindow extends Window{
	private IPowerOffAsync manager=GWT.create(IPowerOff.class);
	private PowerOffConstants constants=GWT.create(PowerOffConstants.class);
	private TextField textPassword=null;
	
	public PowerOffWindow(){
        this.setClosable(true);  
        this.setWidth(300);  
        this.setHeight(150);  
        this.setPlain(true);  
        this.setLayout(new BorderLayout());
        BorderLayoutData centerData = new BorderLayoutData(RegionPosition.CENTER);  
        // centerData.setMargins(3, 0, 3, 3);
        VerticalPanel panelPassword=new VerticalPanel();
        Label label=new Label(constants.question());
        panelPassword.add(label);
        textPassword=new TextField();
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
        
        HorizontalPanel panelManager=new HorizontalPanel();
        panelManager.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
        panelManager.setWidth("100%");
        panelManager.add(buttonOk);
        panelManager.add(buttonCancel);
        
        this.add(panelPassword, centerData);
        this.add(panelManager,new BorderLayoutData(RegionPosition.SOUTH));
        this.setCloseAction(Window.HIDE);
        this.setModal(true);
        this.show();
	}
	
	private void onButtonOk(){
		String password=this.textPassword.getText();
		if((password!=null)&&(password.length()<20)){
			MessageBox.show(new MessageBoxConfig(){
				{
					this.setWait(true);
					this.setProgressText("Server Exchange");
					this.setWidth(200);
					this.setWaitConfig(new WaitConfig(){
						{
							this.setInterval(200);
						}
					});
				}
			});
			this.manager.reboot(password, new AsyncCallback<String>(){
				@Override
				public void onFailure(Throwable caught) {
					MessageBox.hide();
					MessageBox.alert("Server exchange ERROR");
				}
				@Override
				public void onSuccess(String result) {
					MessageBox.hide();
					if(result==null){
						MessageBox.alert(PowerOffWindow.this.constants.passwordError());
					}else{
						MessageBox.alert(PowerOffWindow.this.constants.reboot());
						PowerOffWindow.this.close();
					}
				}
			});
		}
	}
	
	private void onButtonCancel(){
		this.close();
	}
}
