package com.cherkashin.vitaliy.computer_shop.client.view.main_menu.find_commodity;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface IFindCommodityAsync {
	/** ������� ������ �� ������ � ����� ��������� ������ � ��������� ������� */
	void findCommodity(String name, String barCode, String serial,
			AsyncCallback<RowElement[]> callback);

}
