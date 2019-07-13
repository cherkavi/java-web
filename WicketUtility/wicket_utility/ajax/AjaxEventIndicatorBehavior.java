package wicket_utility.ajax;

import org.apache.wicket.ajax.AjaxEventBehavior;
import org.apache.wicket.ajax.IAjaxIndicatorAware;

/** Ajax поведение с индикатором ожидания ответа  от сервера  */
public abstract class AjaxEventIndicatorBehavior extends AjaxEventBehavior implements IAjaxIndicatorAware{
	private final static long serialVersionUID=1L;
	private String httpMarker=null;
	
	/** Ajax поведение с индикатором ожидания ответа  от сервера  
	 * @param event - событие, по которому происходит срабатывание 
	 * @param httpMarker - маркер, который будет отображаться во время ожидания ответа  от сервера 
	 */
	public AjaxEventIndicatorBehavior(String event, String httpMarker){
		super(event);
		this.httpMarker=httpMarker;
	}

	/** Ajax поведение с индикатором ожидания ответа  от сервера */
	public AjaxEventIndicatorBehavior(String event){
		super(event);
		this.httpMarker=null;
	}
	
	@Override
	public String getAjaxIndicatorMarkupId() {
		return httpMarker;
	}

}
