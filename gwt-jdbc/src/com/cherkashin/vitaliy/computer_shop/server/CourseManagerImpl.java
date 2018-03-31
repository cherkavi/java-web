package com.cherkashin.vitaliy.computer_shop.server;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Date;

import com.cherkashin.vitaliy.computer_shop.client.view.main_menu.course.ICourseManager;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import database.ConnectWrap;
import database.StaticConnector;

public class CourseManagerImpl extends RemoteServiceServlet implements ICourseManager{
	private final static long serialVersionUID=1L;
	
	@Override
	public Float getCurrencyValue() {
		ConnectWrap connector=StaticConnector.getConnectWrap();
		try{
			Connection connection=connector.getConnection();
			// получить максимальное значение 
			ResultSet rs=connection.createStatement().executeQuery("select first 1 skip 0 * from course order by kod desc ");
			rs.next();
			return rs.getFloat("CURRENCY_VALUE");
		}catch(Exception ex){
			System.err.println("CourseManager#getCurrencyValue Exception: "+ex.getMessage());
			return null;
		}finally{
			try{
				connector.close();
			}catch(Exception ex){};
		}
	}

	@Override
	public boolean setCurrencyValue(float value) {
		ConnectWrap connector=StaticConnector.getConnectWrap();
		try{
			Connection connection=connector.getConnection();
			// получить максимальное значение 
			ResultSet rs=connection.createStatement().executeQuery("select max(kod) from course ");
			rs.next();
			int kod=rs.getInt(1)+1;
			PreparedStatement st=connection.prepareStatement("insert into course(kod, currency_value, date_set, date_write) values(?,?,?,?)");
			st.setInt(1, kod);
			st.setFloat(2, value);
			long date=(new Date()).getTime();
			st.setDate(3, new java.sql.Date(date));
			st.setTimestamp(4, new Timestamp(date));
			st.executeUpdate();
			connection.commit();
			return true;
		}catch(Exception ex){
			System.err.println("CourseManager#getCurrencyValue Save Exception: "+ex.getMessage());
			return false;
		}finally{
			try{
				connector.close();
			}catch(Exception ex){};
		}
	}

}
