package window.commons;

import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;

import wicket_extension.action.ActionExecutor;

/** ������, ������� �������� ������ ��� �������� �������� � ��������� ���������� */
public class PanelCommand extends Panel{
	private final static long serialVersionUID=1L;
	
	/**
	 * @param id - ���������� ������������� ������ 
	 * @param idRecord - ���������� ������������� ������ 
	 * @param actionExecutor - �����������, �������� ����� �������� ���������� � ������ ������� 
	 * @param actionName - ��� �������, ������� ����� �������� 
	 * @param captionButton - ��������� ������ �� ������ 
	 */
	public PanelCommand(String id, 
					   final Integer idRecord, 
					   final ActionExecutor actionExecutor,
					   final String actionName,
					   String captionButton){
		super(id);
		Form<Object> form=new Form<Object>("form");
		this.add(form);
		
		Button button=new Button("button", new Model<String>(captionButton)){
			private final static long serialVersionUID=1L;
			public void onSubmit() {
				actionExecutor.action(actionName, idRecord);
			};
		};
		form.add(button);
	}
}
