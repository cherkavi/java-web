package wicket_utility.panel_with_button;

import org.apache.wicket.behavior.SimpleAttributeModifier;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;

import wicket_utility.IActionExecutor;

/** ������ ��������� � �������  */
public class PanelWithButton extends Panel{
	private final static long serialVersionUID=1L;
	/** ������-�����������, �������� ������� �������� ���������� */
	private IActionExecutor executor;
	/** ��������, ������� ������ ���� ������� �������-����������� */
	private String executorParameterName;
	/** ��������, ������� ������ ���� ������� �������-����������� ��� ��������*/
	private Object executorParameterObject;

	/** ������ ��������� � �������   
	 * @param panelId - ���������� ������������� ��� ��������
	 * @param executor - ������, �������� ������� ���������� ���������� ����� ��������� ������������ �����������
	 * @param executorParameterName ��������-�������� ��� �������� � ActionExecutor
	 * @param executorParameterObject ��������-�������� ��� �������� � ActionExecutor
	 * @param title (allow HTML tag) - ��������� ��� ������� ���������
	 * @param message (allow HTML tag ) - ���� ���������
	 * @param buttonTitle - ��������� ��� ������ 
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

	
	/** ������ ��������� � �������   
	 * @param panelId - ���������� ������������� ��� ��������
	 * @param executor - ������, �������� ������� ���������� ���������� ����� ��������� ������������ �����������
	 * @param executorParameterName ��������-�������� ��� �������� � ActionExecutor
	 * @param executorParameterObject ��������-�������� ��� �������� � ActionExecutor
	 * @param title (allow HTML tag) - ��������� ��� ������� ���������
	 * @param message (allow HTML tag ) - ���� ���������
	 * @param buttonTitle - ��������� ��� ������ 
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
