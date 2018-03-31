package com.cherkashin.vitaliy.computer_shop.client.view.main_menu.rediscount;

import java.util.HashMap;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface IGetPointsAsync {
	
	/** get all trade points list */
	void getPoints(AsyncCallback<HashMap<Integer,String>> callback);

}
