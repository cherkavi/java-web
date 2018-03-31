package com.example.vaadinexample;

import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.Layout;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

/** example of first window */
public class WindowFirst extends Window{
	private final static long serialVersionUID=1L;
	
	/** example of first window */
	public WindowFirst(){
		initComponents();
	}
	
	private void initComponents(){
		Layout layout=new VerticalLayout();
		
		Label label=new Label();
		layout.addComponent(label);
		
		Button button=new Button("show MainWindow");
		button.addListener(new ClickListener() {
			private final static long serialVersionUID=1L;
			public void buttonClick(ClickEvent event) {
				WindowFirst.this.open(null,"MainWindow");
			}
		});
		
		this.addComponent(layout);
	}
}
