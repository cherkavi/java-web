package gui.commodity_description;

import java.math.BigDecimal;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.IModel;
import database.Utility;
import database.wrap.Assortment;

/** панель, которая отображает полную информацию по товару */
public class PanelDescriptionFull extends Panel{
	private static final long serialVersionUID=1L;
	
	String field_assortment_kod;
	/** панель, которая отображает полную информацию по товару */
	public PanelDescriptionFull(String id, String assortment_kod){
		super(id);
		this.field_assortment_kod=assortment_kod;
		initComponents();
	}
	
	/** первоначальная инициализация компонентов */
	private void initComponents(){
		Assortment assortment=Utility.getAssortmentByKod(field_assortment_kod);
		if(assortment==null){
			this.add(new Label("class_name",getReadOnlyModel("")));
			this.add(new Label("assortment_name",getReadOnlyModel("")));
			this.add(new Label("assortment_note",getReadOnlyModel("")));
			this.add(new Label("warranty_month",getReadOnlyModel( "")));
			this.add(new Label("price_price",getReadOnlyModel("")));
		}else{
			this.add(new Label("class_name",getReadOnlyModel( Utility.getObjectFromTableFromField("CLASS", "KOD",new Integer(assortment.getClass_kod()),"NAME").toString())));
			this.add(new Label("assortment_name",getReadOnlyModel(assortment.getName())));
			this.add(new Label("assortment_note",getReadOnlyModel(assortment.getNote())));
			this.add(new Label("warranty_month",getReadOnlyModel( Integer.toString(assortment.getWarranty_month()))));
			BigDecimal price=null;
			try{
				price=(BigDecimal)Utility.getObjectFromTableFromField("PRICE", "KOD", new Integer(assortment.getPrice_kod()) , "PRICE");
				this.add(new Label("price_price",getReadOnlyModel( price.toString() )));
			}catch(Exception ex){
				this.add(new Label("price_price",getReadOnlyModel( "")));
			}
		}
	}

	private IModel getReadOnlyModel(final String value){
		return new AbstractReadOnlyModel(){
			private static final long serialVersionUID=1L;
			@Override
			public Object getObject() {
				return value;
			}
		};
	}
	
}
