package com.example.vaadinexample;

import com.vaadin.Application;
import com.vaadin.ui.Window;

/** пример использования приложения на базе фреймворка Vaadin*/
public class VaadinexampleApplication extends Application {
	private final static long serialVersionUID=1L;
	private WindowFirst windowFirst;
	private AnotherWindow anotherWindow;
	
	@Override
	public void init() {
		MainWindow mainWindow=new MainWindow();
		mainWindow.setName("MainWindow");
		setMainWindow(mainWindow);
		//WindowFirst first=new WindowFirst();
		//this.addWindow(first);
		setTheme("vaadinexampletheme");
		
		windowFirst=new WindowFirst();
		windowFirst.setName("WindowFirst");
		this.addWindow(windowFirst);
		
		anotherWindow=new AnotherWindow();
		anotherWindow.setName("AnotherWindow");
		this.addWindow(anotherWindow);
	}
	
	@Override
	public Window getWindow(String name) {
		System.out.println("Application#getWindow:"+name);
		if(name.equals("WindowFirst")){
			return windowFirst;
		}
		if(name.equals("AnotherWindow")){
			return anotherWindow;
		}
		return super.getWindow(name);
	}
}
