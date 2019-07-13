package com.test.client;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.test.client.gui.common.SimpleObject;

@RemoteServiceRelativePath("get_simple_object")
public interface IGetSimpleObjectAsync {
	void getSimpleObject(int id, AsyncCallback<SimpleObject> callback);
}
