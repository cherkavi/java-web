package wicket_utility.ajax.table_filter.toolbar.filter.column.date_between.modal_panel;

import java.util.Date;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.behavior.SimpleAttributeModifier;
import org.apache.wicket.extensions.yui.calendar.DateField;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;

import wicket_utility.ajax.IAjaxActionExecutor;

/** панель отображающая выбор из DropDownChoice  */
public class DateBetweenPanel extends Panel{
	private final static long serialVersionUID=1L;
	/** кнопка соглашения установки фильтра */
	private AjaxButton buttonOk;
	/** кнопка отмены  */
	private AjaxButton buttonCancel;
	/** модель активации */
	private IAjaxActionExecutor action;
	/** модель даты начала  */
	private Model<Date> modelDateBegin=new Model<Date>();
	/** модель даты окончания */
	private Model<Date> modelDateEnd=new Model<Date>();
	
	/** 
	 * панель отображающая выбор из DropDownChoice  
	 * @param id - уникальный идентификатор панели 
	 * @param action - executor выполняющий команды
	 */
	public DateBetweenPanel(String id,
						 IAjaxActionExecutor action){
		super(id);
		this.action=action;
		initComponent();
	}
	
	/** инициализация сервиса  */
	private void initComponent(){
		Form<?> formMain=new Form<Object>("form_main");
		this.add(formMain);
		
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
		
		// установка визуальных компонентов для Даты 
		DateField dateBegin=new DateField("date_begin", this.modelDateBegin);
		formMain.add(dateBegin);
		
		DateField dateEnd=new DateField("date_end", this.modelDateEnd);
		formMain.add(dateEnd);
	}
	
	/** установить в качестве 
	 * @param dateBegin - дата начала 
	 * @param dateEnd - дата окончания  
	 */
	public void setModelValue(Date dateBegin, 
							  Date dateEnd){
		this.modelDateBegin.setObject(dateBegin);
		this.modelDateEnd.setObject(dateEnd);
	}
	
	/** реакция на нажатие клавиши Ok */
	private void onButtonOk(AjaxRequestTarget target){
		// TODO проверка на валидность ввода данных 
		// получить выбранный в данный момент
		this.action.action(target, 
						   IAjaxActionExecutor.action_modal_ok,
						   this.modelDateBegin.getObject(),
						   this.modelDateEnd.getObject());
	}
	
	/** реакция на нажатие клавиши Cancel */	
	private void onButtonCancel(AjaxRequestTarget target){
		this.action.action(target, IAjaxActionExecutor.action_modal_cancel);
	}
}
