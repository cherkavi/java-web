package com.cherkashin.vitaliy.computer_shop.client.view.main_menu.find_commodity;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface IFindCommodityAsync {
	/** послать запрос на сервер с целью получения данных о найденных товарах */
	void findCommodity(String name, String barCode, String serial,
			AsyncCallback<RowElement[]> callback);

}
