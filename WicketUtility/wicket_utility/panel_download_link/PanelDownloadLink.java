package wicket_utility.panel_download_link;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;

import wicket_utility.IActionExecutor;

public class PanelDownloadLink extends Panel{
	private final static long serialVersionUID=1L;
	private final IActionExecutor action;
	/** панель, которая содержит Ajax ссылку  
	 * @param id - уникальный идентификатор 
	 * @param title - заголовок для ссылки 
	 * @param action - объект, которому следует передать действие 
	 * @param actionName - имя действия, которое следует передавать в action 
	 * @param actionValue - значение действия, которое следует передавать в action
	 * @param ajaxIndicatorHtmlId - уникальный HTML.id идентификатора 
	 */
	public PanelDownloadLink(String id, 
						 	final String title, 
						 	IActionExecutor action, 
						 	final String actionName, 
						 	final String actionValue){
		super(id);
		this.action=action;
		Link<?> link=new Link<Object>("link"){
			private static final long serialVersionUID=1L;
			@Override
			public void onClick() {
				onLinkClick(actionName, actionValue, title);
			}
		};
		this.add(link);
		
		link.add(new Label("label", title));
	}
	
	/** реакция на нажатие на ссылку  
	 * @param target - Ajax транспорт 
	 * @param actionName - action Name
	 * @param actionValue - action Value
	 * @param title - имя файла, которое будет возвращего пользователю  
	 */
	private void onLinkClick(String actionName, 
						 	 String actionValue,
						 	 String title){
		this.action.action(actionName, new String[]{actionValue, title});
	}
}