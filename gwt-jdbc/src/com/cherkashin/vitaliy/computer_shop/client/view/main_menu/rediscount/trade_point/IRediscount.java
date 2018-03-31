package com.cherkashin.vitaliy.computer_shop.client.view.main_menu.rediscount.trade_point;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath(value="rediscount")
public interface IRediscount extends RemoteService{
	/** проверить наличие созданного переучета по указанной точке 
	 * @param pointKod - код торговой точки
	 * @return 
	 * <ul>
	 * 	<li> <b>true</b> - переучет создан </li>
	 * 	<li> <b>false</b> - нет переучета по текущему дню </li>
	 * </ul>
	 */
	public boolean isRediscountExists(int pointKod);
	
	/** создать стартовую точку для переучета ( загрузить товар наличия всех элементов в таблицу )<br />
	 *  удалить данные предыдущих переучетов, если они были
	 * @param pointKod - код торговой точки
	 * @return 
	 * <ul>
	 * 	<li> <b>true</b> - переучет создан </li>
	 * 	<li> <b>false</b> - ошибка создания переучета </li>
	 * </ul>
	 */
	public boolean createRediscount(int pointKod);
	
	
	/**
	 * Сохранить прочитанный код ( сосчитанный сканером штрих-кодов )
	 * @param pointKod - код торговой точки 
	 * @param readedKod - прочитанный код 
	 * @return
	 * <ul>
	 * 	<li> <b>true</b> - код опознан и сохранен </li>
	 * 	<li> <b>false</b> - код не опознан </li>
	 * </ul>
	 */
	public boolean saveBarCode(int pointKod, String readedCod);
}
