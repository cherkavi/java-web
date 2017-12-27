package com.cherkashin.vitaliy.computer_shop.client.view.main_menu.temp;

import com.cherkashin.vitaliy.computer_shop.client.utility.RootComposite;
import com.cherkashin.vitaliy.computer_shop.client.view.main_menu.MainMenu;
import com.extjs.gxt.ui.client.Style.HorizontalAlignment;
import com.extjs.gxt.ui.client.event.ComponentEvent;
import com.extjs.gxt.ui.client.widget.Label;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.VerticalPanel;
import com.extjs.gxt.ui.client.widget.Viewport;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.layout.ColumnLayout;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.extjs.gxt.ui.client.widget.layout.VBoxLayout;

public class TempPanel extends VerticalPanel{
	
	public TempPanel(){
		this.setHorizontalAlign(HorizontalAlignment.CENTER);
		// LayoutContainer panelMain=new LayoutContainer();
		// panelMain.setLayout(new VBoxLayout());
		this.add(new Label("This is temp panel"));
		this.add(new Button("click for back"){
			@Override
			protected void onClick(ComponentEvent ce) {
				onButtonClick();
			}
		});
	}
	
	private void onButtonClick(){
		RootComposite.showView(new MainMenu());
	}
}
