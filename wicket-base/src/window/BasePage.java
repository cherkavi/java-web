package window;

import org.apache.wicket.MarkupContainer;

import org.apache.wicket.behavior.SimpleAttributeModifier;
import org.apache.wicket.markup.html.WebPage;

public abstract class BasePage extends WebPage {
	
	public BasePage(){
		MarkupContainer container=new MarkupContainer("css_type"){
			private final static long serialVersionUID=1L;
		};
		
		container.add(new SimpleAttributeModifier("href","css_page.css"));
		this.add(container);
	}
	
}
