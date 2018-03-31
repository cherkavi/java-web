package utility;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import database.Utility;
import database.wrap.Assortment;
import database.wrap.ClientOrder;

/** trolley of commodity - тележка покупок */
public class Trolley {
	HashMap<String,Integer> field_data;
	
	/** trolley of commodity - тележка покупок */
	public Trolley(){
		field_data=new HashMap<String, Integer>();
	}
	
	/** добавить указанный товар в корзину */
	public void addCommodity(String assortment_kod, int quantity){
		if(field_data.containsKey(assortment_kod)){
			// kod present - add to commodity
			field_data.put(assortment_kod, field_data.get(assortment_kod).intValue()+quantity);
		}else{
			// kod not present
			field_data.put(assortment_kod, new Integer(quantity));
		}
	}
	/** удалить указанный товар из корзины */
	public void removeCommodity(String assortment_kod){
		field_data.remove(assortment_kod);
	}
	
	/** обновить кол-во в корзине */
	public void refreshQuantity(String assortment_kod, int quantity){
		if(quantity==0){
			field_data.remove(assortment_kod);
		}else{
			field_data.put(assortment_kod, quantity);
		}
	}
	
	/** полность очистить тележку для покупок */
	public void clear(){
		this.field_data.clear();
	}
	
	/** проверить есть ли в корзине элементы */
	public boolean isEmpty(){
		return this.field_data.isEmpty();
	}
	
	/** оформить заказ 
	 * @param kod_client - уникальный код клиента в контексте базы 
	 * */
	public boolean fixedOrder(int kod_client){
		boolean return_value=true;
		ArrayList<ClientOrder> data_list=new ArrayList<ClientOrder>();
		List<TrolleyElement> element_list=this.getAllElements();
		Date current_date_write=new Date();
		for(int counter=0;counter<element_list.size();counter++){
			data_list.add(new ClientOrder(kod_client,
										  element_list.get(counter).getAssortmentKod(),
										  element_list.get(counter).getQuantity(),
										  (float)element_list.get(counter).getPrice(),
										  current_date_write
										  )
						  );
		}
		return_value=Utility.saveOrder(data_list.toArray(new ClientOrder[]{}));
		return return_value;
	}
	
	
	public List<TrolleyElement> getAllElements(){
		ArrayList<TrolleyElement> list=new ArrayList<TrolleyElement>();

		Set<String> keys=field_data.keySet();
		Iterator<String> iterator=keys.iterator();
		while(iterator.hasNext()){
			String key=iterator.next();
			Assortment assortment=Utility.getAssortmentByKod(key);
			TrolleyElement element=new TrolleyElement();
			if(assortment!=null){
				element.setAssortmentName(assortment.getName());
				element.setAssortmentKod(assortment.getKod());
			};
			element.setQuantity(field_data.get(key));
			element.setPrice( ((BigDecimal)Utility.getObjectFromTableFromField("PRICE", "KOD", new Integer(assortment.getPrice_kod()),"PRICE")).doubleValue() ) ;
			element.setAmount(element.getPrice()*element.getQuantity());
			list.add(element);
		}
		return list;
	}
}
