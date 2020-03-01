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

/** ������ ������������ ����� �� DropDownChoice  */
public class DateBetweenPanel extends Panel{
	private final static long serialVersionUID=1L;
	/** ������ ���������� ��������� ������� */
	private AjaxButton buttonOk;
	/** ������ ������  */
	private AjaxButton buttonCancel;
	/** ������ ��������� */
	private IAjaxActionExecutor action;
	/** ������ ���� ������  */
	private Model<Date> modelDateBegin=new Model<Date>();
	/** ������ ���� ��������� */
	private Model<Date> modelDateEnd=new Model<Date>();
	
	/** 
	 * ������ ������������ ����� �� DropDownChoice  
	 * @param id - ���������� ������������� ������ 
	 * @param action - executor ����������� �������
	 */
	public DateBetweenPanel(String id,
						 IAjaxActionExecutor action){
		super(id);
		this.action=action;
		initComponent();
	}
	
	/** ������������� �������  */
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
		
		// ��������� ���������� ����������� ��� ���� 
		DateField dateBegin=new DateField("date_begin", this.modelDateBegin);
		formMain.add(dateBegin);
		
		DateField dateEnd=new DateField("date_end", this.modelDateEnd);
		formMain.add(dateEnd);
	}
	
	/** ���������� � �������� 
	 * @param dateBegin - ���� ������ 
	 * @param dateEnd - ���� ���������  
	 */
	public void setModelValue(Date dateBegin, 
							  Date dateEnd){
		this.modelDateBegin.setObject(dateBegin);
		this.modelDateEnd.setObject(dateEnd);
	}
	
	/** ������� �� ������� ������� Ok */
	private void onButtonOk(AjaxRequestTarget target){
		// TODO �������� �� ���������� ����� ������ 
		// �������� ��������� � ������ ������
		this.action.action(target, 
						   IAjaxActionExecutor.action_modal_ok,
						   this.modelDateBegin.getObject(),
						   this.modelDateEnd.getObject());
	}
	
	/** ������� �� ������� ������� Cancel */	
	private void onButtonCancel(AjaxRequestTarget target){
		this.action.action(target, IAjaxActionExecutor.action_modal_cancel);
	}
}
