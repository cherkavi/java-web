package wicket_utility.ajax.table_filter.toolbar.filter.column.number_value.modal_panel;

import org.apache.wicket.ajax.AjaxRequestTarget;

import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.behavior.SimpleAttributeModifier;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.Radio;
import org.apache.wicket.markup.html.form.RadioGroup;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;

import wicket_utility.ajax.IAjaxAction;

/** фильтр текста как целого значения */
public class NumberPanel extends Panel{
	private final static long serialVersionUID=1L;
	private AjaxButton buttonOk;
	private AjaxButton buttonCancel;
	private IAjaxAction action;
	
	private Model<String> modelValue=new Model<String>("");
	private Model<Boolean> modelGt=new Model<Boolean>(true);
	private Model<Boolean> modelGe=new Model<Boolean>(false);
	private Model<Boolean> modelLt=new Model<Boolean>(false);
	private Model<Boolean> modelLe=new Model<Boolean>(false);
	private Model<Boolean> modelEq=new Model<Boolean>(false);
	private Model<Boolean> modelNe=new Model<Boolean>(false);
	
	/** 
	 * фильтр текста как целого значения    
	 * @param id - уникальный идентификатор панели 
	 * @param action - executor выполняющий команды
	 */
	public NumberPanel(String id,
					   IAjaxAction action){
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
		// values
		RadioGroup<Boolean> radioGroup=new RadioGroup<Boolean>("radio_group", new Model<Boolean>());
		radioGroup.setRequired(false);
		formMain.add(radioGroup);
		radioGroup.add(new Radio<Boolean>("checked_gt", modelGt));
		radioGroup.add(new Radio<Boolean>("checked_ge", modelGe));
		radioGroup.add(new Radio<Boolean>("checked_lt", modelLt));
		radioGroup.add(new Radio<Boolean>("checked_le", modelLe));
		radioGroup.add(new Radio<Boolean>("checked_eq", modelEq));
		radioGroup.add(new Radio<Boolean>("checked_ne", modelNe));

		TextField<String> textNumber=new TextField<String>("text_number",this.modelValue);
		textNumber.setRequired(false);
		formMain.add(textNumber);
	}
	
	/** установить в качестве
	 * @param gt - больше 
	 * @param ge - больше либо равно
	 * @param lt - меньше
	 * @param le - меньше либо равно
	 * @param eq - равно
	 * @param ne - не равно
	 * @param value - значение 
	 */
	public void setModelValue(boolean gt,
							  boolean ge,
							  boolean lt,
							  boolean le,
							  boolean eq,
							  boolean ne,
							  String value){
		this.modelGt.setObject(gt);
		this.modelGe.setObject(ge);
		this.modelLt.setObject(lt);
		this.modelLe.setObject(le);
		this.modelEq.setObject(eq);
		this.modelNe.setObject(ne);
		this.modelValue.setObject(value);
	}
	
	/** реакция на нажатие клавиши Ok */
	private void onButtonOk(AjaxRequestTarget target){
		// получить выбранный в данный момент
		this.action.action(target, 
						   IAjaxAction.action_modal_ok,
						   this.modelGt.getObject(),
						   this.modelGe.getObject(),
						   this.modelLt.getObject(),
						   this.modelLe.getObject(),
						   this.modelEq.getObject(),
						   this.modelNe.getObject(),
						   ((this.modelValue.getObject()==null)?"":this.modelValue.getObject())
						   );
	}
	
	/** реакция на нажатие клавиши Cancel */	
	private void onButtonCancel(AjaxRequestTarget target){
		this.action.action(target, IAjaxAction.action_modal_cancel);
	}
	
}
