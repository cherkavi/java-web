package com.cherkashin.vitaliy.gwt_example.client;

import com.google.gwt.core.client.EntryPoint;

import com.google.gwt.core.client.GWT;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;


/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class GWT_example3 implements EntryPoint {
	private VerticalPanel mainPanel = new VerticalPanel();
	private FlexTable stocksFlexTable = new FlexTable();
	private HorizontalPanel addPanel = new HorizontalPanel();
	private TextBox newSymbolTextBox = new TextBox();
	private Button addButton = new Button("Add");
	private Label lastUpdatedLabel = new Label();
	
	/**
	 * Create a remote service proxy to talk to the server-side Greeting service.
	 */
	private final GreetingServiceAsync greetingService = GWT
			.create(GreetingService.class);

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		// Настраиваем заголовок таблицы цен
		stocksFlexTable.setText(0, 0, "Symbol");
		stocksFlexTable.setText(0, 1, "Price");
		stocksFlexTable.setText(0, 2, "Change");
		stocksFlexTable.setText(0, 3, "Remove");
		// Панель добавления новой акции
		addPanel.add(newSymbolTextBox);
		addPanel.add(addButton);
		// Главная панель
		mainPanel.add(stocksFlexTable);
		mainPanel.add(addPanel);
		mainPanel.add(lastUpdatedLabel);
		// Добавляем главную панель к HTML элементу с ID "stockList"
		RootPanel.get("stockList").add(mainPanel);
		// Переводим фокус на текстовое поле
		newSymbolTextBox.setFocus(true);
		
		addButton.addClickHandler(new ClickHandler(){
			@Override
			public void onClick(ClickEvent event) {
				GWT_example3.this.onButtonClick();
			}
		});
	}
	
	private void onButtonClick(){
		System.out.println("On button click");
	}
}
