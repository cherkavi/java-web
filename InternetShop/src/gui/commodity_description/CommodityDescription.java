package gui.commodity_description;

import gui.order_product.OrderProduct;
import gui.place_order.PlaceOrder;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;

import utility.ShopSession;

/** страница описания товара */
public class CommodityDescription extends WebPage{
	private String field_assortment_kod;
	/** страница описания товара и возможности его заказа, добавления в корзину 
	 * @param assortment_id уникальный идентификатор ассортимента 
	 */
	public CommodityDescription(String assortment_kod){
		field_assortment_kod=assortment_kod;
		initComponents();
	}

	/** create and init Component's */
	private void initComponents(){
		Form form_choose=new Form("form_choose");
		form_choose.add(new Button("button_add_to_trolley"){
			private static final long serialVersionUID = 1L;
			@Override
			public void onSubmit(){
				((ShopSession)getSession()).getTrolley().addCommodity(CommodityDescription.this.field_assortment_kod, 1);
				setResponsePage(new OrderProduct());
			}
		});
		form_choose.add(new Button("button_order"){
			private static final long serialVersionUID = 1L;
			@Override
			public void onSubmit(){
				((ShopSession)getSession()).getTrolley().addCommodity(CommodityDescription.this.field_assortment_kod, 1);
				setResponsePage(new PlaceOrder());
			}
		});
		form_choose.add(new Button("button_return"){
			private static final long serialVersionUID = 1L;
			@Override
			public void onSubmit(){
				setResponsePage(  ((ShopSession)getSession()).popWebPage() );
			}
		});
		
		this.add(new PanelDescriptionFull("panel_description_full",field_assortment_kod));
		this.add(form_choose);
	}
	
}
