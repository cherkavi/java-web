package com.cherkashin.vitaliy.computer_shop.client.view.main_menu.course;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface ICourseManagerAsync {
	/** получить курс валют  */
	void getCurrencyValue(AsyncCallback<Float> callback);

	/** установить курс валют */
	void setCurrencyValue(float value, AsyncCallback<Boolean> callback);

}
