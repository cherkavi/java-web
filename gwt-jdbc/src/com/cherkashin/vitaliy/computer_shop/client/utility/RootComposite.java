package com.cherkashin.vitaliy.computer_shop.client.utility;

import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.google.gwt.user.client.ui.Composite;


import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class RootComposite extends Composite{
	private static RootComposite self;
	private static int windowWidth=800;
	private static int windowHeight=600;
	// private String rootPanelName;
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
		// this.rootPanelName=rootPanelName;
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
		// panel.clear();
		try{
			panel.clear();
			/*int elementSize=panel.getWidgetCount();
			for(int counter=0;counter<elementSize;counter++){
				panel.remove(0);
			}*/
		}catch(Exception ex){
			System.err.println("RootComposite#setWidget Exception:"+ex.getMessage());
		};
		// �������� ������ ������
		panel.add(composite);
	}
	
	/** ���������� ��������� � �������� ��������� */
	private void setWidget(LayoutContainer container){
		try{
			panel.clear();
		}catch(Exception ex){
			System.err.println("RootComposite#setWidget Exception");
		}
		// container.setSize(windowWidth, windowHeight);
		panel.add(container);
	}
	

	/** ���������� ������, �������� ���������� � ��������� ��� ���� �������� */
	public static void showView(Composite composite){
		self.setWidget(composite);
	}

	/** ���������� ������, �������� ���������� � ��������� ��� ���� �������� */
	public static void showView(LayoutContainer container){
		self.setWidget(container);
	}
}
