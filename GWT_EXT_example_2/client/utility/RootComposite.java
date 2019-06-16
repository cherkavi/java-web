package com.cherkashin.vitaliy.computer_shop.client.utility;

import com.google.gwt.user.client.ui.Composite;


import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class RootComposite extends Composite{
	private static RootComposite self;
	private static int windowWidth=800;
	private static int windowHeight=600;
	
	/** �������� ������ ���� */
	public static int getWindowWidth(){
		return windowWidth;
	}
	
	/** �������� ������ ���� */
	public static int getWindowHeight(){
		return windowHeight;
	}
	
	private VerticalPanel panel;

	/** ���������� ������ ������� ( ��� �������� ������� ) � �������� ��������� ��������  
	 * @param rootPanelName - ��� ��������� �������� � ������ ������������ 
	 */
	public static void setMain(String rootPanelName){
		new RootComposite(rootPanelName);
	}
	
	
	private RootComposite(String rootPanelName){
		panel=new VerticalPanel();
		// INFO ���������. �������� ������ � ����������� GridPanel
		//panel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);  
		//panel.setVerticalAlignment(HasVerticalAlignment.ALIGN_TOP);
		//panel.setWidth("100%");
		//panel.setHeight("100%");
		//panel.setSize("500px", "300px");
		initWidget(panel);
	
		self=this;
		RootPanel.get(rootPanelName).add(self);
	}
	
	/** ���������� Composite �� ��� Alias*/
	private void setWidget(Composite composite){
		panel.clear();
		// �������� ������ ������
		panel.add(composite);
	}
	

	/** ���������� ������, �������� ���������� � ��������� ��� ���� �������� */
	public static void showView(Composite composite){
		self.setWidget(composite);
	}
	
}
