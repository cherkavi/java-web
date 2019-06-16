package com.test.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface RandomServiceAsync {
	public void getRandomString(AsyncCallback<String> returnValue);
}
