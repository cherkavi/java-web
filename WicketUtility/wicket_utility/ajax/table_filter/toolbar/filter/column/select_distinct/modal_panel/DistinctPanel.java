package wicket_utility.ajax.table_filter.toolbar.filter.column.select_distinct.modal_panel;

import java.util.ArrayList;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.behavior.SimpleAttributeModifier;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;

import wicket_utility.ajax.IAjaxAction;

/** панель отображающая выбор из DropDownChoice  */
public class DistinctPanel extends Panel{
	private final static long serialVersionUID=1L;
	private DropDownChoice<String> select;
	private AjaxButton buttonOk;
	private AjaxButton buttonCancel;
	private Model<ArrayList<String>> choices=new Model<ArrayList<String>>();
	private Model<String> modelChoice=new Model<String>();
	private IAjaxAction action;
	
	/** 
	 * панель отображающая выбор из DropDownChoice  
	 * @param id - уникальный идентификатор панели 
	 * @param action - executor выполняющий команды
	 */
	public DistinctPanel(String id,
						 IAjaxAction action){
		super(id);
		this.action=action;
		initComponent();
	}
	
	/** инициализация сервиса  */
	private void initComponent(){
		Form<?> formMain=new Form<Object>("form_main");
		this.add(formMain);
		
		select=new DropDownChoice<String>("select", 
								  		  modelChoice, 	   
								  		  choices);
		formMain.add(select);
		
		buttonOk=new AjaxButton("button_ok"){
			private final static long serialVersionUID=1L;
			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				onButtonOk(target);
			}
		};
		formMain.add(buttonOk);
		buttonOk.add(new SimpleAttributeModifier("value",
												 this.getString("caption_button_ok")));
		
		buttonCancel=new AjaxButton("button_cancel"){
			private final static long serialVersionUID=1L;
			protected void onSubmit(AjaxRequestTarget target, Form<?> form){
				onButtonCancel(target);
			}
		};
		formMain.add(buttonCancel);
		buttonCancel.add(new SimpleAttributeModifier("value",
													 this.getString("caption_button_cancel")));
	}
	
	/** установить в качестве 
	 * @param list - список допустимых вариантов выбора
	 * @param selectedIndex - индекс выбора значения 
	 */
	public void setModelValue(ArrayList<String> list, 
							  int selectedIndex){
		this.choices.setObject(list);
		if(list.size()>0){
			this.modelChoice.setObject(list.get(selectedIndex));
		}else{
			this.modelChoice.setObject(null);
		}
	}
	
	/** реакция на нажатие клавиши Ok */
	private void onButtonOk(AjaxRequestTarget target){
		// получить выбранный в данный момент
		this.action.action(target, 
						   IAjaxAction.action_modal_ok,
						   this.modelChoice.getObject());
	}
	
	/** реакция на нажатие клавиши Cancel */	
	private void onButtonCancel(AjaxRequestTarget target){
		this.action.action(target, IAjaxAction.action_modal_cancel);
	}
}
