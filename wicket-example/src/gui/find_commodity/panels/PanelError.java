package gui.find_commodity.panels;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;

public class PanelError extends Panel{
	private static final long serialVersionUID=1L;
	private Label field_label_message_error;
	
	/** Панель, которая отображает ошибочное сообщение 
	 * @param id- уникальный идентификатор объекта 
	 * */
	public PanelError(String id){
		super(id);
		initComponents();
	}
	/** Панель, которая отображает ошибочное сообещение 
	 * @param id - уникальный идентификатор объекта
	 * @param message_for_output - сообщение, которое необходимо вывести на экран 
	 * */
	public PanelError(String id, String message_for_output){
		super(id);
		initComponents();
		this.setMessageErrorObject(message_for_output);
	}
	
	private void initComponents(){
		this.field_label_message_error=new Label("message_error",new Model("Error"));
		this.add(field_label_message_error);
	}
	
	/** установить новое сообщение для панели, которая выводит сообщение об ошибке*/
	public void setMessageErrorObject(String value){
		this.field_label_message_error.setModelObject(value);
	}
	
}
