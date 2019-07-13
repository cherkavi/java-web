package com.cherkashin.vitaliy.computer_shop.client.utility;

import com.google.gwt.user.client.ui.Composite;


import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class RootComposite extends Composite{
	private static RootComposite self;
	private static int windowWidth=800;
	private static int windowHeight=600;
	
	/** получить ширину окна */
	public static int getWindowWidth(){
		return windowWidth;
	}
	
	/** получить высоту окна */
	public static int getWindowHeight(){
		return windowHeight;
	}
	
	private VerticalPanel panel;

	/** установить данный элемент ( имя которого указано ) в качестве корневого элемента  
	 * @param rootPanelName - имя корневого элемента с панели инструментов 
	 */
	public static void setMain(String rootPanelName){
		new RootComposite(rootPanelName);
	}
	
	
	private RootComposite(String rootPanelName){
		panel=new VerticalPanel();
		// INFO контролер. вызывает ошибку в отображении GridPanel
		//panel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);  
		//panel.setVerticalAlignment(HasVerticalAlignment.ALIGN_TOP);
		//panel.setWidth("100%");
		//panel.setHeight("100%");
		//panel.setSize("500px", "300px");
		initWidget(panel);
	
		self=this;
		RootPanel.get(rootPanelName).add(self);
	}
	
	/** установить Composite по его Alias*/
	private void setWidget(Composite composite){
		panel.clear();
		// получить объект класса
		panel.add(composite);
	}
	

	/** отобразить модуль, согласно псевдонима и сохранить для него параметр */
	public static void showView(Composite composite){
		self.setWidget(composite);
	}
	
}
