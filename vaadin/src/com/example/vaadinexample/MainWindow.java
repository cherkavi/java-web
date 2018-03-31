package com.example.vaadinexample;

import java.util.Date;

import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.terminal.ExternalResource;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.DateField;
import com.vaadin.ui.Label;
import com.vaadin.ui.Link;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Window;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

public class MainWindow extends Window{
	private final static long serialVersionUID=1L;
	
	public MainWindow(){
		super("Пример использования Vaadin Framework");
		this.setName("MainWindow");
		//this.setCaption("window caption");
		this.setBorder(Window.BORDER_DEFAULT);
		// Label 
		final Label label = new Label("Hello Vaadin user");
		label.setCaption("This is new caption");
		label.setDescription("description this element");
		this.addComponent(label);
		
		// Button
		final Button button=new Button("go to another window");
		button.addListener(new Button.ClickListener() {
			private final static long serialVersionUID=1L;
			public void buttonClick(ClickEvent event) {
				System.out.println("button is clicked");
				/*AnotherWindow window=new AnotherWindow();
				MainWindow.this.addWindow(window);
				window.setModal(true);*/
				MainWindow.this.open(new ExternalResource("WindowFirst"));
			}
		});
		this.addComponent(button);
		// Link
		Link link=new Link("link to google", new ExternalResource("http://google.com.ua"));
		this.addComponent(link);
		
		Link linkToFirst=new Link("goto WindowFirst",new ExternalResource("WindowFirst"));
		linkToFirst.setTargetName("WindowFirst");
		linkToFirst.setTargetBorder(Link.TARGET_BORDER_DEFAULT);
		this.addComponent(linkToFirst);
		
		
		// TextField 
		TextField textField=new TextField("input values");
		textField.setInputPrompt("must be not empty");
		this.addComponent(textField);
		
		// DateField
		DateField dateField=new DateField();
		dateField.setCaption("Данные для ввода ");
		dateField.setValue(new Date());
		dateField.setDateFormat("dd.MM.yyyy");
		this.addComponent(dateField);
		
		// CheckBox see OptionGroup
		CheckBox checkbox=new CheckBox("checkbox example");
		checkbox.addListener(new ClickListener(){
			private final static long serialVersionUID=1L;
			public void buttonClick(ClickEvent event) {
				System.out.println("checkbox clicked ");
				label.setCaption("ClickListener: checkbox is clicked");
			}
		});
		checkbox.addListener(new Component.Listener() {
			private final static long serialVersionUID=1L;
			public void componentEvent(Event event) {
				System.out.println("Class name:"+event.getSource().getClass().getName());
				label.setCaption("Component.Listener: checkbox is clicked");
			}
		});

			
		checkbox.addListener(new Property.ValueChangeListener(){
			private final static long serialVersionUID=1L;
			public void valueChange(ValueChangeEvent event) {
				System.out.println("Property: "+event.getProperty().getType().getName());
				System.out.println("Name: "+event.getProperty().getValue());
				label.setCaption("ValueChangeListener: checkbox is clicked");
			}
		});
		checkbox.addListener(new Button.ClickListener(){
			private final static long serialVersionUID=1L;
			public void buttonClick(ClickEvent event) {
				System.out.println("checkbox clicked: ");
			}
		});
		// !!!!!
		checkbox.setImmediate(true);
		this.addComponent(checkbox);

		final Button button2=new Button("push me");
		button2.addListener(new Button.ClickListener() {
			private final static long serialVersionUID=1L;
			
			public void buttonClick(ClickEvent event) {
				System.out.println("button2 is clicked");
			}
		});
		button2.setSwitchMode(true);
		this.addComponent(button2);
	}
}
