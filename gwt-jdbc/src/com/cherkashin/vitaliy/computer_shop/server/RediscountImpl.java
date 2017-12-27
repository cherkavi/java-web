package com.cherkashin.vitaliy.computer_shop.server;

import com.cherkashin.vitaliy.computer_shop.client.view.main_menu.rediscount.trade_point.IRediscount;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import database.StaticConnector;

public class RediscountImpl extends RemoteServiceServlet implements IRediscount{
	private final static long serialVersionUID=1L;
	
	@Override
	public boolean createRediscount(int pointKod) {
		// если существует переучет - удалить 
		// получить все товары по указанной торговой точке
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isRediscountExists(int pointKod) {
		boolean returnValue=false;
		ConnectWrap connector=StaticConnector.getConnectWrap();
		try{
			
		}catch(Exception ex){
			System.err.println("RediscountImpl#isRediscountExists Exception:"+ex.getMessage());
		}finally{
			try{
				connector.close();
			}catch(Exception ex){};
		}
		return returnValue;
	}

	@Override
	public boolean saveBarCode(int kodPoint, String readedCod) {
		// TODO Auto-generated method stub
		return false;
	}

}
