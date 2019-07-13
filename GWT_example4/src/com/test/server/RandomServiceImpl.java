package com.test.server;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.test.client.RandomService;

public class RandomServiceImpl extends RemoteServiceServlet implements RandomService{
	private final static long serialVersionUID=1L;
	
	private SimpleDateFormat sdf=new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
	
	@Override
	public String getRandomString() {
		return sdf.format(new Date());
	}
	
}
