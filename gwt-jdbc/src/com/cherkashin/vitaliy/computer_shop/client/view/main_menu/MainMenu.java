package com.cherkashin.vitaliy.computer_shop.client.view.main_menu;

import com.cherkashin.vitaliy.computer_shop.client.utility.RootComposite;


import com.cherkashin.vitaliy.computer_shop.client.view.main_menu.commodity_assortment.CommodityAssortment;
import com.cherkashin.vitaliy.computer_shop.client.view.main_menu.course.Course;
import com.cherkashin.vitaliy.computer_shop.client.view.main_menu.find_commodity.FindCommodity;
import com.cherkashin.vitaliy.computer_shop.client.view.main_menu.password_window.PasswordWindow;
import com.cherkashin.vitaliy.computer_shop.client.view.main_menu.power_off.PowerOffWindow;
import com.cherkashin.vitaliy.computer_shop.client.view.main_menu.rediscount.ChoiceTradePoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.VerticalPanel;

public class MainMenu extends Composite{

	private MainMenuConstants constants=GWT.create(MainMenuConstants.class);
	
	public MainMenu(){
		Button buttonFindCommodity=new Button(constants.buttonFindCommodity());
		//buttonFindCommodity.setWidth("200px");
		//buttonFindCommodity.setHeight("50px");
		buttonFindCommodity.addClickHandler(new ClickHandler(){
			@Override
			public void onClick(ClickEvent event) {
				onButtonFindCommodity();
			}
		});
		
		Button buttonPowerOff=new Button(constants.buttonPowerOff());
		buttonPowerOff.addClickHandler(new ClickHandler(){
			@Override
			public void onClick(ClickEvent event) {
				onButtonPowerOff();
			}
		});
		
		Button buttonCourse=new Button(constants.buttonCourse());
		buttonCourse.addClickHandler(new ClickHandler(){
			@Override
			public void onClick(ClickEvent event) {
				onButtonCourse();
			}
		});
		
		Button buttonCommodityAssortment=new Button(constants.commodityAssortment());
		buttonCommodityAssortment.addClickHandler(new ClickHandler(){
			@Override
			public void onClick(ClickEvent event){
				onButtonCommodityAssortment();
			}
		});
		
		Button buttonRediscount=new Button(constants.captionRediscount());
		buttonRediscount.addClickHandler(new ClickHandler(){
			@Override
			public void onClick(ClickEvent event) {
				onButtonRediscount();
			}
		});
		
		VerticalPanel panel=new VerticalPanel();
		panel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		panel.setSpacing(10);
		panel.setTitle(constants.titlePanel());
		panel.add(buttonFindCommodity);
		panel.add(buttonPowerOff);
		panel.add(buttonCourse);
		panel.add(buttonCommodityAssortment);
		panel.add(buttonRediscount);
		this.initWidget(panel);
	}

	private void onButtonRediscount(){
		RootComposite.showView(new ChoiceTradePoint());
		showModalVariant=Menu.choice_trade_point;
	}
	
	private void onButtonFindCommodity(){
		showModalVariant=Menu.find_commodity;
		RootComposite.showView(new FindCommodity());
	}

	private Menu showModalVariant=null;
	
	private void onButtonPowerOff(){
		showModalVariant=Menu.power_off;
		new PasswordWindow(this);
	}
	
	private void onButtonCourse(){
		showModalVariant=Menu.course;
		new PasswordWindow(this);
	}
	
	private void onButtonCommodityAssortment(){
		showModalVariant=Menu.commodity_assortment;
		new PasswordWindow(this);
	}
	
	// private void onButtonTemp(){RootComposite.showView(new TempPanel());}
	public void enterPasswordOk(){
		if(showModalVariant!=null){
			switch(showModalVariant){
				case power_off: RootComposite.showView(new PowerOffWindow());break;
				case course: RootComposite.showView(new Course()); break;
				case commodity_assortment: RootComposite.showView(new CommodityAssortment()); break;
				default: break;
			}
		}
	}
	
	public void enterPasswordError(){
		showModalVariant=null;
	}
}

enum Menu{
	power_off, 
	course, 
	commodity_assortment,
	find_commodity,
	choice_trade_point;
}
