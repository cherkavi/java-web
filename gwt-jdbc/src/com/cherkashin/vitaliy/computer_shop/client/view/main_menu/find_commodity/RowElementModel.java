package com.cherkashin.vitaliy.computer_shop.client.view.main_menu.find_commodity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.extjs.gxt.ui.client.data.*;

/** модель для таблицы данных */
public class RowElementModel implements ModelData{
	private RowElement element;
	
	/** модель для таблицы данных */
	public RowElementModel(RowElement rowElement){
		this.element=rowElement;
	}
	
	/** получить {@link RowElement} */
	public RowElement getRowElement(){
		return this.element;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <X> X get(String property) {
		if(property!=null){
			if(property.equals("point")){
				return (X)this.element.getPoint();
			}else if(property.equals("name")){
				return (X)this.element.getName();
			}else if(property.equals("barCode")){
				return (X)this.element.getBarCode();
			}else if(property.equals("serialNumber")){
				return (X)this.element.getSerialNumber();
			}else if(property.equals("price")){
				return (X)new Float(this.element.getPrice());
			}else{
				return null;
			}
		}else{
			return null;
		}
	}

	@Override
	public Map<String, Object> getProperties() {
		Map<String, Object> properties = new HashMap<String, Object>();
	    for(String property : getPropertyNames()){
	        properties.put(property, get(property));
	    }
	    return properties;	
	}

	@Override
	public Collection<String> getPropertyNames() {
		Collection<String> names = new ArrayList<String>();
        names.add("point");
        names.add("name");
        names.add("barCode");
        names.add("serialNumber");
        names.add("price");
        return names;	
    }

	@Override
	public <X> X remove(String property) {
		return this.set(property, null);
	}

	@Override
	public <X> X set(String property, X value) {
		if(property!=null){
			if(property.equals("point")){
				if(value instanceof String){
					this.element.setPoint((String)value);
					return this.get(property);
				}else{
					return this.get(property);
				}
			}else if(property.equals("name")){
				if(value instanceof String){
					this.element.setName((String)value);
					return this.get(property);
				}else{
					return this.get(property);
				}
			}else if(property.equals("barCode")){
				if(value instanceof String){
					this.element.setBarCode((String)value);
					return this.get(property);
				}else{
					return this.get(property);
				}
			}else if(property.equals("serialNumber")){
				if(value instanceof String){
					this.element.setSerialNumber((String)value);
					return this.get(property);
				}else{
					return this.get(property);
				}
			}else if(property.equals("price")){
				if(value instanceof Float){
					this.element.setPrice((Float)value);
					return this.get(property);
				}else{
					return this.get(property);
				}
			}else{
				return null;
			}
		}else{
			return null;
		}
	}
}
