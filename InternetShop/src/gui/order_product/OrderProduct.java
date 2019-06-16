package gui.order_product;

import gui.place_order.PlaceOrder;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.Model;

import utility.ShopSession;

/** Добавление выделенного товара в корзину */
public class OrderProduct extends WebPage{
	public OrderProduct(){
		initComponents();
	}
	
	/** инициализация компонентов */
	private void initComponents(){
		Form form_main=new Form("form_main");
		form_main.add(new PanelTrolley("panel_trolley"));
		form_main.add(new Button("button_continue"){
			private static final long serialVersionUID=1L;
			public void onSubmit(){
				setResponsePage( ((ShopSession)getSession()).popWebPage());
			}
		});
		form_main.add(new Button("button_order"){
			private static final long serialVersionUID=1L;
			public void onSubmit(){
				setResponsePage( new PlaceOrder());
			}
		});
		this.add(form_main);
	}
}
