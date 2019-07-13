package com.test.server;

import java.util.Date;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.test.client.IGetSimpleObject;
import com.test.client.gui.common.SimpleObject;

public class GetSimpleObjectImpl extends RemoteServiceServlet implements IGetSimpleObject{
	private static final long serialVersionUID = 1L;

	@Override
	public SimpleObject getSimpleObject(int id) {
		return new SimpleObject(id, Long.toString((new Date()).getTime()));
	}

}
