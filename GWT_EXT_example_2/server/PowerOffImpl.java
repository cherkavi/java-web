package com.cherkashin.vitaliy.computer_shop.server;

import java.sql.Connection;
import java.sql.ResultSet;

import com.cherkashin.vitaliy.computer_shop.client.view.main_menu.power_off.IPowerOff;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import database.ConnectWrap;
import database.StaticConnector;

public class PowerOffImpl extends RemoteServiceServlet implements IPowerOff{
	private final static long serialVersionUID=1L;

	@Override
	public String reboot(String password) {
		String returnValue=null;
		ConnectWrap connector=StaticConnector.getConnectWrap();
		try{
			Connection connection=connector.getConnection();
			ResultSet rs=connection.createStatement().executeQuery("select * from people where people.kod=5 and KEY_KOD='"+password+"'");
			if(rs.next()){
				rs=connection.createStatement().executeQuery("select * from PARAMETERS ");
				rs.next();
				String command=rs.getString("OFF_COMMAND");
				Runtime.getRuntime().exec(command);
				returnValue="REBOOT";
			}
			rs.getStatement().close();
		}catch(Exception ex){
			System.err.println("PowerOffImpl Exception:"+ex.getMessage());
		}finally{
			connector.close();
		}
		return returnValue;
	}
	
	

}
