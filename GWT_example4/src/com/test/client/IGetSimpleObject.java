package com.test.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.test.client.gui.common.SimpleObject;

@RemoteServiceRelativePath("get_simple_object")
public interface IGetSimpleObject extends RemoteService{
	public SimpleObject getSimpleObject(int id);
}
