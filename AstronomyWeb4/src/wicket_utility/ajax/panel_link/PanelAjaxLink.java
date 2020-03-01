package wicket_utility.ajax.panel_link;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.extensions.ajax.markup.html.IndicatingAjaxLink;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;

import wicket_utility.ajax.IAjaxActionExecutor;


/** ������, ������� �������� Ajax ������  */
public class PanelAjaxLink extends Panel{
	private final static long serialVersionUID=1L;
	private final IAjaxActionExecutor action;
	/** ������, ������� �������� Ajax ������  
	 * @param id - ���������� ������������� 
	 * @param title - ��������� ��� ������ 
	 * @param action - ������, �������� ������� �������� �������� 
	 * @param actionName - ��� ��������, ������� ������� ���������� � action 
	 * @param actionValue - �������� ��������, ������� ������� ���������� � action
	 * @param ajaxIndicatorHtmlId - ���������� HTML.id �������������� 
	 */
	public PanelAjaxLink(String id, 
						 String title, 
						 IAjaxActionExecutor action, 
						 final String actionName, 
						 final String actionValue,
						 final String ajaxIndicatorHtmlId){
		super(id);
		this.action=action;
		IndicatingAjaxLink<?> link=new IndicatingAjaxLink<Object>("link"){
			private static final long serialVersionUID=1L;
			@Override
			public void onClick(AjaxRequestTarget target) {
				onAjaxClick(target, actionName, actionValue);
			}
			@Override
			public String getAjaxIndicatorMarkupId() {
				return ajaxIndicatorHtmlId;
			}
		};
		this.add(link);
		
		link.add(new Label("label", title));
		
		
	}
	
	/** ������� �� ������� �� ������  
	 * @param target - Ajax ��������� 
	 * @param actionName - action Name
	 * @param actionValue - action Value
	 */
	private void onAjaxClick(AjaxRequestTarget target, 
						 String actionName, 
						 String actionValue){
		this.action.action(target, actionName, actionValue);
	}
}
