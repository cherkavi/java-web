package com.cherkashin.vitaliy.computer_shop.client.view.main_menu.rediscount;

import java.util.HashMap;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("get_points")
public interface IGetPoints extends RemoteService{
	/** get all trade points list */
	public HashMap<Integer,String> getPoints();
}
