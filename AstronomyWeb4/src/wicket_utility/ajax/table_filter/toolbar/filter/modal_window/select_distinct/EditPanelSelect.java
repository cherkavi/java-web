package wicket_utility.ajax.table_filter.toolbar.filter.modal_window.select_distinct;

import java.util.ArrayList;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.model.Model;

import wicket_utility.ajax.IAjaxActionExecutor;
import wicket_utility.ajax.table_filter.toolbar.filter.modal_window.EditFilterPanel;

/** ������ ��� �������������� DropDownChoice ��������� */
public class EditPanelSelect extends EditFilterPanel{
	private static final long serialVersionUID=1L;
	private Model<String> selectedValue=new Model<String>(null);
	private IAjaxActionExecutor action;
	/**
	 * ������ ��� �������������� ����������� ������
	 * @param id - wicket:id
	 * @param caption - ��������� ��� ��������������  
	 * @param elements - ��������, ����� ������� ������� ������ �����
	 * @param action - 
	 * <ul>
	 * 	 <li><b>Save - </b> <table border="1"><tr><td> {@link IAjaxActionExecutor#action_save}</td><td> selected_value </td></tr></table></li>
	 * 	 <li><b>Cancel - </b>  <table border="1"><tr><td> {@link IAjaxActionExecutor#action_cancel} </td><td> null </td></tr></table></li>
	 * </ul>
	 */
	public EditPanelSelect(String id,
						   String caption,
						   String[] elements,
						   IAjaxActionExecutor action) {
		super(id);
		this.action=action;
		
		this.add(new Label("title",caption));
		
		ArrayList<String> listOfElements=new ArrayList<String>();
		if((elements==null)||(elements.length==0)){
			listOfElements.add(" ");
		}else{
			for(int counter=0;counter<elements.length;counter++){
				listOfElements.add(elements[counter]);
			}
		}
		this.selectedValue.setObject(listOfElements.get(0));
		this.add(new DropDownChoice<String>("select_element",
										    this.selectedValue, 
										    new Model<ArrayList<String>>(listOfElements)));
	}

	@Override
	protected void reactionButtonCancel(AjaxRequestTarget target) {
		action.action(target, IAjaxActionExecutor.action_cancel);
	}

	@Override
	protected void reactionButtonSave(AjaxRequestTarget target) {
		action.action(target, IAjaxActionExecutor.action_save, this.selectedValue.getObject());
	}
	
}
