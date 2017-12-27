package com.cherkashin.vitaliy.computer_shop.server;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.cherkashin.vitaliy.computer_shop.client.view.main_menu.password_window.IPasswordManager;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import database.ConnectWrap;
import database.StaticConnector;

/** серверная сторона управления доступом  */
public class PasswordManager extends RemoteServiceServlet implements IPasswordManager{
	private final static long serialVersionUID=1L;
	
	@Override
	public boolean checkPassword(String password) {
		boolean returnValue=false;
		ConnectWrap connector=StaticConnector.getConnectWrap();
		try{
			PreparedStatement ps=connector.getConnection().prepareStatement("select * from people where people.kod=5 and people.key_kod=?");
			ps.setString(1, password);
			ResultSet rs=ps.executeQuery();
			returnValue=rs.next();
		}catch(Exception ex){
			System.err.println("PasswordManager#checkPassword Exception:"+ex.getMessage());
		}finally{
			try{
				connector.close();
			}catch(Exception ex){};
		}
		return returnValue;
	}

}
