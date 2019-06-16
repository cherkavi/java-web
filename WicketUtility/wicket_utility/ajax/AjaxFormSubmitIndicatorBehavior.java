package wicket_utility.ajax;

import org.apache.wicket.ajax.IAjaxIndicatorAware;
import org.apache.wicket.ajax.form.AjaxFormSubmitBehavior;

/** ��������� ������� �������� ����� �� ������� � ������������� �� �������� � ������������ ��������  */
public abstract class AjaxFormSubmitIndicatorBehavior extends AjaxFormSubmitBehavior implements IAjaxIndicatorAware{
	private final static long serialVersionUID=1L;
	private final String ajaxIndicatorId;
	/** ��������� ������� �������� ����� �� ������� � ������������� �� �������� � ������������ ��������  
	 * @param event - ��� ������� 
	 * @param ajaxIndicatorId - ID �� �������� �������������� �������� ������� �� ������� 
	 */
	public AjaxFormSubmitIndicatorBehavior(String event, 
										   String ajaxIndicatorId) {
		super(event);
		this.ajaxIndicatorId=ajaxIndicatorId;
	}

	@Override
	public String getAjaxIndicatorMarkupId() {
		return this.ajaxIndicatorId;
	}

}
