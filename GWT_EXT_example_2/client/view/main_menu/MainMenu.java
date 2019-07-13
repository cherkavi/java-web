package com.cherkashin.vitaliy.computer_shop.client.view.main_menu;

import com.cherkashin.vitaliy.computer_shop.client.utility.RootComposite;
import com.cherkashin.vitaliy.computer_shop.client.view.main_menu.commodity_assortment.CommodityAssortment;
import com.cherkashin.vitaliy.computer_shop.client.view.main_menu.course.Course;
import com.cherkashin.vitaliy.computer_shop.client.view.main_menu.find_commodity.FindCommodity;
import com.cherkashin.vitaliy.computer_shop.client.view.main_menu.power_off.PowerOffWindow;
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
		
		VerticalPanel panel=new VerticalPanel();
		panel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		panel.setSpacing(10);
		panel.setTitle(constants.titlePanel());
		panel.add(buttonFindCommodity);
		panel.add(buttonPowerOff);
		panel.add(buttonCourse);
		panel.add(buttonCommodityAssortment);
		
		this.initWidget(panel);
	}

	
	private void onButtonFindCommodity(){
		RootComposite.showView(new FindCommodity());
	}
	
	private void onButtonPowerOff(){
		new PowerOffWindow();
	}
	
	private void onButtonCourse(){
		RootComposite.showView(new Course());
	}
	
	private void onButtonCommodityAssortment(){
		RootComposite.showView(new CommodityAssortment());
	}
}
