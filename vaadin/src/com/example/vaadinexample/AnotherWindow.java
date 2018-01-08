package com.example.vaadinexample;

import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Window;

public class AnotherWindow extends Window{
	private final static long serialVersionUID=1L;
	
	public AnotherWindow(){
		super("This is Another window");
		com.vaadin.ui.Label label=new Label("Метка для фреймворка");
		HorizontalLayout layout=new HorizontalLayout();
		layout.addComponent(label);
		Button button=new Button("Переход на главную страницу",this, "onClickButton");
		layout.addComponent(button);
		this.addComponent(layout);
		this.setWidth("400px");
		this.setHeight("200px");
	}
	
	
	public void onClickButton(){
		System.out.println("Click Button");
		this.close();
	}
}
