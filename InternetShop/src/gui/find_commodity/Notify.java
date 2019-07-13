package gui.find_commodity;

import org.apache.wicket.Component;

public interface Notify {
	/** реализующий данный интерфейс объект принимает сообщения о необходимости отправки пользователю очередной порции данных ( страницы и/или Ajax комопнента) 
	 * @param - компонент (панель) который передал данный запрос 
	 * */
	public void notifySubmit(Component sender);
	/** реализующий данный интерфейс объект принимает сообщения о необходимости отправки пользователю сообщения об ошибочных действиях 
	 * @param - компонент (панель) который передал данный запрос 
	 * */
	public void notifyError(Component sender);
}
