package wicket_utility.ajax.table_filter.toolbar.filter.modal_window.select_distinct;

import java.util.ArrayList;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.model.Model;

import wicket_utility.ajax.IAjaxAction;
import wicket_utility.ajax.table_filter.toolbar.filter.modal_window.EditFilterPanel;

/** панель для редактирования DropDownChoice элементов */
public class EditPanelSelect extends EditFilterPanel{
	private static final long serialVersionUID=1L;
	private Model<String> selectedValue=new Model<String>(null);
	private IAjaxAction action;
	/**
	 * Панель для редактирования выпадающего списка
	 * @param id - wicket:id
	 * @param caption - заголовок для редактирования  
	 * @param elements - элементы, среди которых следует делать выбор
	 * @param action - 
	 * <ul>
	 * 	 <li><b>Save - </b> <table border="1"><tr><td> {@link IAjaxAction#action_save}</td><td> selected_value </td></tr></table></li>
	 * 	 <li><b>Cancel - </b>  <table border="1"><tr><td> {@link IAjaxAction#action_cancel} </td><td> null </td></tr></table></li>
	 * </ul>
	 */
	public EditPanelSelect(String id,
						   String caption,
						   String[] elements,
						   IAjaxAction action) {
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
		action.action(target, IAjaxAction.action_cancel);
	}

	@Override
	protected void reactionButtonSave(AjaxRequestTarget target) {
		action.action(target, IAjaxAction.action_save, this.selectedValue.getObject());
	}
	
}
