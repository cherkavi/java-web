package gui.order_product;

import java.util.Iterator;
import java.util.List;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.IModel;

import utility.ShopSession;
import utility.Trolley;
import utility.TrolleyElement;


public class PanelTrolley extends Panel{
	private static final long serialVersionUID=1L;
	
	public PanelTrolley(String id){
		super(id);
		initComponents();
	}
	
	private void initComponents(){
		Trolley trolley=((ShopSession)getSession()).getTrolley();
		List<TrolleyElement> list=trolley.getAllElements();
		ListView trolley_list=new ListView("trolley_list",list){
			private static final long serialVersionUID=1L;
			@Override
			protected void populateItem(ListItem item) {
				try{
					TrolleyElement current_element=(TrolleyElement)item.getModelObject();
					item.add(new Label("assortment_name",getReadOnlyModel(current_element.getAssortmentName())));
					item.add(new Label("quantity",getReadOnlyModel( Integer.toString(current_element.getQuantity()) )));
					item.add(new Label("price",getReadOnlyModel( Double.toString(current_element.getPrice()) )));
					item.add(new Label("amount",getReadOnlyModel( Double.toString(current_element.getAmount()) )));
				}catch(Exception ex){
					// если произошла ошибка - проверить на наличие в ListItem указанных элементов
					checkItemForElementExists(item, "assortment_name");
					checkItemForElementExists(item, "quantity");
					checkItemForElementExists(item, "price");
					checkItemForElementExists(item, "amount");
				}
			}
		};
		this.add(trolley_list);
		double amount_all=0;
		for(Iterator<TrolleyElement> iterator=list.iterator();iterator.hasNext();){
			amount_all+=iterator.next().getAmount();
		}
		this.add(new Label("amount_all",Double.toString(amount_all)));
	}
	
	/** проверить Item на наличие в ней указанного элемента, и если его нет - добавить пустое значение */
	private void checkItemForElementExists(ListItem item, String element_name){
		if(item.get(element_name)==null){
			item.add(new Label(element_name,getReadOnlyModel("")));
		}
	}
	
	/** вернуть ReadOnlyModel для указанной String величины */
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
