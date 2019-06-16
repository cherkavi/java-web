package com.test.client.main_menu;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DecoratorPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.test.client.modules.Modules;


public class MainMenu extends Composite{
	/** имя корневого элемента для отображения */
	private String rootName;
	
	public MainMenu(String rootName){
		// create component's
		Button button=new Button("click");

		// add listener's
		button.addClickHandler(new ClickHandler(){
			@Override
			public void onClick(ClickEvent event) {
				onButtonMenu();
			}
		});
		// placing component's
		DecoratorPanel decorator=new DecoratorPanel();
		VerticalPanel vertical=new VerticalPanel();
		vertical.setTitle("Title");
		vertical.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		vertical.add(new HTML("<b>MainMenu</b>"));
		vertical.add(new HTML("<hr width=\"20\">"));
		vertical.add(button);
		
		decorator.add(vertical);
		this.initWidget(decorator);
		RootPanel.get(rootName).add(this);
	}
	
	private void onButtonMenu(){
		System.out.println("MainMenu#onButtonMenu");
		RootPanel.get(this.rootName).remove(this);
		RootPanel.get(this.rootName).clear();
		new Modules(this.rootName);
	}
}
