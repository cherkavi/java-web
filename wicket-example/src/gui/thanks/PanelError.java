package gui.thanks;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.IModel;

/** ������, ������� ���������� ������������� ��������� ���������� ������ �� ������ */
public class PanelError extends Panel{
	private static final long serialVersionUID=1L;
	
	/** ������, ������� ���������� ������������� ��������� ���������� ������ �� ������ */
	public PanelError(String id,String message){
		super(id);
		this.add(new Label("label_information",getReadOnlyModel(message)));
	}
	
	/** �������� ������ ������ ��� ������ ������ 
	 * @param value - ������, ������� ������ ��� �������� ������ ������ ��� ������ ������
	 * */
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
