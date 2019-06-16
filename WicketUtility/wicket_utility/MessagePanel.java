package wicket_utility;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;

/** панель, которая отображает текстовое сообщение сообщения */
public class MessagePanel extends Panel{
	private static final long serialVersionUID = 1L;

	/** панель, которая отображает текстовое сообщение сообщения */
	public MessagePanel(String id, String message){
		super(id);
		this.add(new Label("label_message",message));
	}
	
}
