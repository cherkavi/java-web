package wicket_utility.panel_with_button;

import org.apache.wicket.behavior.SimpleAttributeModifier;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;

import wicket_utility.IActionExecutor;

/** ѕанель сообщени€ с кнопкой  */
public class PanelWithButton extends Panel{
	private final static long serialVersionUID=1L;
	/** объект-исполнитель, которому следует передать управление */
	private IActionExecutor executor;
	/** параметр, который должен быть передан объекту-исполнителю */
	private String executorParameterName;
	/** параметр, который должен быть передан объекту-исполнителю как аргумент*/
	private Object executorParameterObject;

	/** ѕанель сообщени€ с кнопкой   
	 * @param panelId - уникальный идентификатор дл€ страницы
	 * @param executor - объект, которому следует передавать управление после получени€ управл€ющего воздействи€
	 * @param executorParameterName аргумент-значение дл€ передачи в ActionExecutor
	 * @param executorParameterObject аргумент-параметр дл€ передачи в ActionExecutor
	 * @param title (allow HTML tag) - заголовок дл€ данного сообщени€
	 * @param message (allow HTML tag ) - само сообщение
	 * @param buttonTitle - заголовок дл€ кнопки 
	 */
	public PanelWithButton(String panelId, 
						   IActionExecutor executor,
						   String executorParameterName,
						   Object executorParamterObject,
						   String title,
						   String message,
						   String buttonTitle
						   ){
		super(panelId);
		this.executor=executor;
		this.executorParameterName=executorParameterName;
		this.executorParameterObject=executorParamterObject;
		initComponents(title, message, buttonTitle);
	}

	
	/** ѕанель сообщени€ с кнопкой   
	 * @param panelId - уникальный идентификатор дл€ страницы
	 * @param executor - объект, которому следует передавать управление после получени€ управл€ющего воздействи€
	 * @param executorParameterName аргумент-значение дл€ передачи в ActionExecutor
	 * @param executorParameterObject аргумент-параметр дл€ передачи в ActionExecutor
	 * @param title (allow HTML tag) - заголовок дл€ данного сообщени€
	 * @param message (allow HTML tag ) - само сообщение
	 * @param buttonTitle - заголовок дл€ кнопки 
	 */
	public PanelWithButton(String panelId, 
						   IActionExecutor executor,
						   String executorParameterName,
						   Object executorParamterObject,
						   String title,
						   IModel<?> model,
						   String buttonTitle
						   ){
		super(panelId);
		this.executor=executor;
		this.executorParameterName=executorParameterName;
		this.executorParameterObject=executorParamterObject;
		initComponents(title, model.getObject().toString(), buttonTitle);
	}
	
	private void initComponents(String title, String message, String buttonCaption){
		Label labelTitle=new Label("title",title);
		labelTitle.setEscapeModelStrings(false);
		this.add(labelTitle);
	
		Label labelMessage=new Label("message",message);
		labelMessage.setEscapeModelStrings(false);
		this.add(labelMessage);
		
		Form<?> formMain=new Form<Object>("form_main");
		this.add(formMain);
		
		Button button=new Button("button_ok"){
			private final static long serialVersionUID=1L;
			public void onSubmit() {
				onButtonOk();
			};
		};
		button.add(new SimpleAttributeModifier("value",buttonCaption));
		formMain.add(button);
	}
	
	private void onButtonOk(){
		this.executor.action(this.executorParameterName, this.executorParameterObject);
	}
}
