package wicket_utility.panel_download_link;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;

import wicket_utility.IActionExecutor;

public class PanelDownloadLink extends Panel{
	private final static long serialVersionUID=1L;
	private final IActionExecutor action;
	/** ������, ������� �������� Ajax ������  
	 * @param id - ���������� ������������� 
	 * @param title - ��������� ��� ������ 
	 * @param action - ������, �������� ������� �������� �������� 
	 * @param actionName - ��� ��������, ������� ������� ���������� � action 
	 * @param actionValue - �������� ��������, ������� ������� ���������� � action
	 * @param ajaxIndicatorHtmlId - ���������� HTML.id �������������� 
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
	
	/** ������� �� ������� �� ������  
	 * @param target - Ajax ��������� 
	 * @param actionName - action Name
	 * @param actionValue - action Value
	 * @param title - ��� �����, ������� ����� ���������� ������������  
	 */
	private void onLinkClick(String actionName, 
						 	 String actionValue,
						 	 String title){
		this.action.action(actionName, new String[]{actionValue, title});
	}
}