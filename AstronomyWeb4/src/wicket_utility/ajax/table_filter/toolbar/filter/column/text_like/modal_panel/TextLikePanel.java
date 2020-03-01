package wicket_utility.ajax.table_filter.toolbar.filter.column.text_like.modal_panel;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.behavior.SimpleAttributeModifier;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;

import wicket_utility.ajax.IAjaxActionExecutor;

/** ������ ������ */
public class TextLikePanel extends Panel{
	private final static long serialVersionUID=1L;
	 
	private AjaxButton buttonOk;
	private AjaxButton buttonCancel;
	private IAjaxActionExecutor action;
	
	private Model<Boolean> modelLeftVariant=new Model<Boolean>(false);
	private Model<String> modelValue=new Model<String>("");
	private Model<Boolean> modelRightVariant=new Model<Boolean>(false);
	
	/** 
	 * ������ ������������ ������ ������   
	 * @param id - ���������� ������������� ������ 
	 * @param action - executor ����������� �������
	 */
	public TextLikePanel(String id,
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
		// values
		formMain.add(new CheckBox("left_variant", this.modelLeftVariant));
		formMain.add(new TextField<String>("text_value",this.modelValue));
		formMain.add(new CheckBox("right_variant", this.modelRightVariant));
	}
	
	/** ���������� � �������� 
	 * @param list - ������ ���������� ��������� ������
	 * @param selectedIndex - ������ ������ �������� 
	 */
	public void setModelValue(boolean leftVariant,
							  String value,
							  boolean rightVariant){
		this.modelLeftVariant.setObject(leftVariant);
		this.modelValue.setObject(value);
		this.modelRightVariant.setObject(rightVariant);
	}
	
	/** ������� �� ������� ������� Ok */
	private void onButtonOk(AjaxRequestTarget target){
		// �������� ��������� � ������ ������
		this.action.action(target, 
						   IAjaxActionExecutor.action_modal_ok,
						   this.modelLeftVariant.getObject(),
						   ((this.modelValue.getObject()==null)?"":this.modelValue.getObject()),
						   this.modelRightVariant.getObject()
						   );
	}
	
	/** ������� �� ������� ������� Cancel */	
	private void onButtonCancel(AjaxRequestTarget target){
		this.action.action(target, IAjaxActionExecutor.action_modal_cancel);
	}
	
	/** �������� �������� ������ % */
	public Boolean getFilterLeftVariant(){
		return this.modelLeftVariant.getObject();
	}
	
	/** �������� �������� ������� % */
	public Boolean getFilterRightVariant(){
		return this.modelRightVariant.getObject();
	}
	
	/** �������� �������� ��������� ���������� */
	public String getFilterValue(){
		return this.modelValue.getObject();
	}
}
