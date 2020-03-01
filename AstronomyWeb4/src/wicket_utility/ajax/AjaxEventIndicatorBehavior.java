package wicket_utility.ajax;

import org.apache.wicket.ajax.AjaxEventBehavior;
import org.apache.wicket.ajax.IAjaxIndicatorAware;

/** Ajax ��������� � ����������� �������� ������  �� �������  */
public abstract class AjaxEventIndicatorBehavior extends AjaxEventBehavior implements IAjaxIndicatorAware{
	private final static long serialVersionUID=1L;
	private String httpMarker=null;
	
	/** Ajax ��������� � ����������� �������� ������  �� �������  
	 * @param event - �������, �� �������� ���������� ������������ 
	 * @param httpMarker - ������, ������� ����� ������������ �� ����� �������� ������  �� ������� 
	 */
	public AjaxEventIndicatorBehavior(String event, String httpMarker){
		super(event);
		this.httpMarker=httpMarker;
	}

	/** Ajax ��������� � ����������� �������� ������  �� ������� */
	public AjaxEventIndicatorBehavior(String event){
		super(event);
		this.httpMarker=null;
	}
	
	@Override
	public String getAjaxIndicatorMarkupId() {
		return httpMarker;
	}

}
