package wicket_utility.ajax;

import org.apache.wicket.ajax.IAjaxIndicatorAware;
import org.apache.wicket.ajax.form.AjaxFormSubmitBehavior;

/** поведение отсылки значений формы по событию и идентификация на странице о происходящем действии  */
public abstract class AjaxFormSubmitIndicatorBehavior extends AjaxFormSubmitBehavior implements IAjaxIndicatorAware{
	private final static long serialVersionUID=1L;
	private final String ajaxIndicatorId;
	/** поведение отсылки значений формы по событию и идентификация на странице о происходящем действии  
	 * @param event - имя события 
	 * @param ajaxIndicatorId - ID на странице идентификатора ожидания реакции от сервера 
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
