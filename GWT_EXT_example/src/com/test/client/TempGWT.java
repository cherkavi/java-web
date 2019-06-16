package com.test.client;

import com.google.gwt.core.client.EntryPoint;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.RootPanel;
 
import com.gwtext.client.widgets.Panel;  
import com.gwtext.client.widgets.Resizable;
import com.gwtext.client.widgets.ResizableConfig;
import com.gwtext.client.widgets.event.ResizableListenerAdapter;
/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class TempGWT implements EntryPoint {

	/**
	 * Create a remote service proxy to talk to the server-side Greeting service.
	 */
	private final GreetingServiceAsync greetingService = GWT
			.create(GreetingService.class);

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		Panel collapsiblePanel = new Panel();  
        collapsiblePanel.setTitle("Collapsible Panel");  
        collapsiblePanel.setWidth(200);  
        collapsiblePanel.setCollapsible(true);  
        collapsiblePanel.setHtml("this is temp from ");
        RootPanel.get("main").add(collapsiblePanel);
        
        Panel panel = new Panel();  
        panel.setBorder(false);  
        panel.setPaddings(15);  
  
        final Panel resizablePanel = new Panel();  
        resizablePanel.setTitle("Ressizable Panel");  
        resizablePanel.setIconCls("paste-icon");  
        resizablePanel.setWidth(200);  
        resizablePanel.setCollapsible(true);  
        resizablePanel.setHtml("this is text into <b>resizeble panel</b> ");  
  
        ResizableConfig config = new ResizableConfig();  
        config.setHandles(Resizable.SOUTH_EAST);  
  
        final Resizable resizable = new Resizable(resizablePanel, config);  
        resizable.addListener(new ResizableListenerAdapter() {  
            public void onResize(Resizable self, int width, int height) {  
                resizablePanel.setWidth(width);  
                resizablePanel.setHeight(height);  
            }  
        });  
  
        panel.add(resizablePanel);  
  
        RootPanel.get("main").add(panel);          
	}
}
