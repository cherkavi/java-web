package wicket_extension.action;

import java.io.Serializable;

/** Данный интерфейс предназначен для исполнительных элементов, 
 * которые принимают оповещения о действиях, в основном это могут быть страницы(WebPage),
 * которые содержат панели, и уже панели оповещают о выполнении каких-либо действий   
 */
public interface ActionExecutor {
	/** оповещение о необходимости выполнения некоторого действия:
	 * @param actionName - имя действия
	 * @param argument - аргумент, который передается для выполнения действия  
	 * */
	public void action(String actionName, Serializable argument);
}
