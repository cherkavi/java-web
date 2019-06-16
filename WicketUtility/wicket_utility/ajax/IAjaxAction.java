package wicket_utility.ajax;

import org.apache.wicket.ajax.AjaxRequestTarget;

/** оповещение о действии */
public interface IAjaxAction {
	/** действие вернуло положительный результат обработки  */
	public static final int result_ok=(0);
	/** действие вернуло отрицательный результат обработки */
	public static final int result_error=(-1);
	/** сигнал отобразить  */
	public static final String action_show="SHOW";
	
	/** сигнал сохранить */
	public static final String action_save="SAVE";
	
	/** сигнал отменить */
	public static final String action_cancel="CANCEL";
	
	/** модальное окно вернуло положительный результат */
	public static final String action_modal_ok="MODAL_OK";
	
	/** модальное окно вернуло отрицательный результат */
	public static final String action_modal_cancel="MODAL_CANCEL";
	
	/** оповещение о действии
	 * @param target - AjaxTarget для передачи данных удаленному клиенту 
	 * @param name - уникальное имя действия {@link #SHOW}
	 * @param parameters - параметры, которые могут отсутствовать
	 */
	public int action(AjaxRequestTarget target, 
					   String name, 
					   Object ... parameters);
}
