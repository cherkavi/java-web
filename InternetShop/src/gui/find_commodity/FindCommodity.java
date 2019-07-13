package gui.find_commodity;

import org.apache.wicket.Component;
import org.apache.wicket.markup.html.WebPage;

import gui.find_commodity.panels.PanelData;
import gui.find_commodity.panels.PanelError;
import gui.find_commodity.panels.PanelFilter;

/** класс для отображения поиска товара на сайте*/
public class FindCommodity extends WebPage implements Notify{
	
	PanelFilter field_panel_filter;
	PanelData field_panel_data;
	PanelError field_panel_error;
	
	public FindCommodity(){
		super();
		initComponents();
	}
	
	private void initComponents(){
		field_panel_filter=new PanelFilter("panel_filter",(Notify)this);
		field_panel_data=new PanelData(this,"panel_data");
		field_panel_error=new PanelError("panel_data");
		this.add(field_panel_filter);
		field_panel_error.setMessageErrorObject("");
		this.add(field_panel_error);
	}

	@Override
	public void notifySubmit(Component sender) {
		// check show PanelData 
		if(this.get("panel_data")==field_panel_error){
			this.remove(field_panel_error);
			this.add(field_panel_data);
		}

		if(sender==field_panel_data){
			// sender is PanelData
		}
		if(sender==field_panel_filter){
			// sender is PanelFilter
			//System.out.println("notifySubmit: setFilter:"+field_panel_filter.getFilter());
			try{
				field_panel_data.setFilter(field_panel_filter.getFilter());
			}catch(Exception ex){
				// List size has 0
				this.remove(field_panel_data);
				field_panel_error.setMessageErrorObject("Нет данных для отображения");
				this.add(field_panel_error);
			}
			
		}
		
	}

	@Override
	public void notifyError(Component sender) {
		String message_error="Error";
		if(sender==field_panel_data){
			// PanelData
		}
		if(sender==field_panel_filter){
			// PanelFilter
		}
		if(sender==field_panel_error){
			// PanelError
		}
		if(this.get("panel_data")==field_panel_data){
			this.remove(field_panel_data);
			this.add(field_panel_error);
		}
		this.field_panel_error.setMessageErrorObject(message_error);
	}
}
