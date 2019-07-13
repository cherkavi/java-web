package wicket_utility.ajax.table_filter.toolbar.filter.modal_window;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.behavior.SimpleAttributeModifier;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.Panel;

/** модальное окно для редактирования */
public abstract class EditFilterPanel extends Panel{
	private final static long serialVersionUID=1L;

	/** модальное окно для редактирования 
	 * @param id - wicket:id
	 * */
	public EditFilterPanel(String id) {
		super(id);
		initComponents();
	}
	
	private void initComponents(){
		Form<?> formMain=new Form<Object>("form_main");
		this.add(formMain);
	
		AjaxButton buttonSave=new AjaxButton("button_save"){
			private final static long serialVersionUID=1L;

			@Override
			protected void onSubmit(AjaxRequestTarget target, 
									Form<?> form) {
				reactionButtonSave(target);
			}
		};
		formMain.add(buttonSave);
		buttonSave.add(new SimpleAttributeModifier("value",this.getString("caption_button_save")));
		
		
		AjaxButton buttonCancel=new AjaxButton("button_cancel"){
			private final static long serialVersionUID=1L;

			@Override
			protected void onSubmit(AjaxRequestTarget target, 
									Form<?> form) {
				reactionButtonCancel(target);
			}
		};
		formMain.add(buttonCancel);
		buttonCancel.add(new SimpleAttributeModifier("value",this.getString("caption_button_cancel")));
	}
	
	protected abstract void reactionButtonSave(AjaxRequestTarget target);
	protected abstract void reactionButtonCancel(AjaxRequestTarget target);
}
