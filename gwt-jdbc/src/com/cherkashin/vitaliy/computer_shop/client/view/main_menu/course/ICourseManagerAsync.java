package com.cherkashin.vitaliy.computer_shop.client.view.main_menu.course;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface ICourseManagerAsync {
	/** �������� ���� �����  */
	void getCurrencyValue(AsyncCallback<Float> callback);

	/** ���������� ���� ����� */
	void setCurrencyValue(float value, AsyncCallback<Boolean> callback);

}
