package com.cherkashin.vitaliy.computer_shop.client.view.main_menu.rediscount.trade_point;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface IRediscountAsync {

	/** проверить наличие созданного переучета по указанной точке 
	 * @param pointKod - код торговой точки
	 * @return 
	 * <ul>
	 * 	<li> <b>true</b> - переучет создан </li>
	 * 	<li> <b>false</b> - нет переучета по текущему дню </li>
	 * </ul>
	 */
	void isRediscountExists(int pointKod, AsyncCallback<Boolean> callback);

	/** создать стартовую точку для переучета ( загрузить товар наличия всех элементов в таблицу ) 
	 * @param pointKod - код торговой точки
	 * @return 
	 * <ul>
	 * 	<li> <b>true</b> - переучет создан </li>
	 * 	<li> <b>false</b> - ошибка создания переучета </li>
	 * </ul>
	 */
	void createRediscount(int pointKod, AsyncCallback<Boolean> callback);

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
	void saveBarCode(int pointKod, String readedCod, AsyncCallback<Boolean> callback);

}
