package gui.find_commodity.panels;

import gui.commodity_description.CommodityDescription;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;

import utility.ShopSession;

/** Панель для отображения линка перехода на страницу отображения товара */
public class PanelDataLink extends Panel{
	private static final long serialVersionUID=1L;
	
	/** Панель для отображения линка перехода на страницу отображения товара
	 *  
	 * @param id - уникальный идентификатор в масштабе родительской страницы   
	 * @param commodity_id - уникальный идентификатор товара, для перехода на страницу данного товара
	 * @param caption - наименование данного товара 
	 */
	public PanelDataLink(final WebPage return_page, String id, String commodity_id, String caption){
		super(id);
		Link link=new Link("link", new Model(commodity_id)){
			private final static long serialVersionUID=1L;
			@Override
			public void onClick() {
				((ShopSession)this.getSession()).pushWebPage(return_page);
				this.setResponsePage(new CommodityDescription(((String)this.getModelObject())));
			}
		};
		link.add(new Label("caption",new Model(caption)));
		this.add(link);
	}
}
