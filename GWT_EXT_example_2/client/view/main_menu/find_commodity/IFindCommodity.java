package com.cherkashin.vitaliy.computer_shop.client.view.main_menu.find_commodity;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("find_commodity")
public interface IFindCommodity extends RemoteService{
	/** послать запрос на поиск товара */
	public RowElement[] findCommodity(String name, String barCode, String serial);
}
