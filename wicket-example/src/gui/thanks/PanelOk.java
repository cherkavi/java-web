package gui.thanks;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.IModel;

/** ������, ������� ���������� ������������� ��������� ���������� ������ �� ������ */
public class PanelOk extends Panel{
	private static final long serialVersionUID=1L;
	
	/** ������, ������� ���������� ������������� ��������� ���������� ������ �� ������ */
	public PanelOk(String id,String message){
		super(id);
		this.add(new Label("label_information",getReadOnlyModel(message)));
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
