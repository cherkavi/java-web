package window.commons;

import java.io.Serializable;

import org.apache.wicket.behavior.SimpleAttributeModifier;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.Panel;
import wicket_extension.action.ActionExecutor;

public class ConfirmYesNo extends Panel{
	private final static long serialVersionUID=1L;
	private ActionExecutor actionExecutor;
	private String actionNameYes;
	private String actionNameNo;
	private Serializable argument; 
	
	/**
	 * @param id - уникальный номер
	 * @param message - сообщение 
	 * @param actionExecutor - исполнитель, который должен получить управление 
	 * @param actionNameYes - имя команды при положительном ответе 
	 * @param actionNameNo - имя команды при отрицательном ответе
	 * @param captionYes - заголовок для кнопки Yes
	 * @param captionNo - заголовок для кнопки No
	 * @param argument - аргумент для передачи
	 */
	public ConfirmYesNo(String id, 
						String message,
						ActionExecutor actionExecutor, 
						String actionNameYes, 
						String actionNameNo, 
						String captionYes,
						String captionNo,
						Serializable argument){
		super(id);
		this.actionExecutor=actionExecutor;
		this.actionNameYes=actionNameYes;
		this.actionNameNo=actionNameNo;
		this.argument=argument;
		initComponents(message, captionYes, captionNo);
	}

	/** инициализация компонентов */
	private void initComponents(String message, String captionYes, String captionNo){
		Form<Object> formMain=new Form<Object>("form_main");
		this.add(formMain);
		
		formMain.add(new Label("label",message));
		
		Button buttonYes=new Button("button_yes"){
			private final static long serialVersionUID=1L;
			public void onSubmit() {
				onButtonYes();
			};
		};
		buttonYes.add(new SimpleAttributeModifier("value",captionYes));
		formMain.add(buttonYes);
		
		Button buttonNo=new Button("button_no"){
			private final static long serialVersionUID=1L;
			public void onSubmit() {
				onButtonNo();
			};
		};
		buttonNo.add(new SimpleAttributeModifier("value",captionNo));
		formMain.add(buttonNo);
	}

	/** button yes */
	private void onButtonYes(){
		this.actionExecutor.action(actionNameYes, argument);
	}
	
	/** button no */
	private void onButtonNo(){
		this.actionExecutor.action(actionNameNo, argument);
	}
}
