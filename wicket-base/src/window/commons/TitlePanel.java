package window.commons;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;

public class TitlePanel extends Panel{
	private final static long serialVersionUID=1L;
	private Model<String> modelMessage=new Model<String>();
	/**
	 * @param id - уникальный номер
	 * @param title - сообщение 
	 */
	public TitlePanel(String id, 
					  String title){
		super(id);
		initComponents(title);
	}

	/** инициализация компонентов */
	private void initComponents(String message){
		this.modelMessage.setObject(message);
		this.add(new Label("label",this.modelMessage));
	}

	/** установить заголовок в панель отображения */
	public void setTitle(String title){
		this.modelMessage.setObject(title);
	}
}
