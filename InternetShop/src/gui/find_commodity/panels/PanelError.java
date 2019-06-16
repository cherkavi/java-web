package gui.find_commodity.panels;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;

public class PanelError extends Panel{
	private static final long serialVersionUID=1L;
	private Label field_label_message_error;
	
	/** ������, ������� ���������� ��������� ��������� 
	 * @param id- ���������� ������������� ������� 
	 * */
	public PanelError(String id){
		super(id);
		initComponents();
	}
	/** ������, ������� ���������� ��������� ���������� 
	 * @param id - ���������� ������������� �������
	 * @param message_for_output - ���������, ������� ���������� ������� �� ����� 
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
	
	/** ���������� ����� ��������� ��� ������, ������� ������� ��������� �� ������*/
	public void setMessageErrorObject(String value){
		this.field_label_message_error.setModelObject(value);
	}
	
}
