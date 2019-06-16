package com.test.client;

import com.google.gwt.core.client.EntryPoint;


import com.google.gwt.user.client.ui.RootPanel;
import com.gwtext.client.widgets.CycleButton;
import com.test.client.gui.MainPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class GWT_example4 implements EntryPoint {
	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		
/*		Panel panel = new Panel();  
        panel.setPaddings(5);  
        panel.setBorder(false);  
  
        //create a CycleButton  
        CycleButton button = new CycleButton();  
        button.setShowText(true);  
        button.setPrependText("View as ");  
  
        //add CheckItem's to the CycleButton  
        button.addItem(new CheckItem("text only", true));  
        button.addItem(new CheckItem("HTML", false));  
  
        //log check item changes  
        button.addListener(new CycleButtonListenerAdapter() {  
            public void onChange(CycleButton self, CheckItem item) {  
                System.out.println(item.getText() + " selected.");  
            }  
        });  
        panel.add(button);  
  
        RootPanel.get().add(panel);
        */
		CycleButton button = new CycleButton();
		RootPanel.get("main_panel").add(button);
    }
}
