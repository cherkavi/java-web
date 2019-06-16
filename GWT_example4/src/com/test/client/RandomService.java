package com.test.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("random_service")
public interface RandomService extends RemoteService{
	public String getRandomString();
}
