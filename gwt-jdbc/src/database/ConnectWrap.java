package database;

import java.sql.Connection;

import org.hibernate.Session;

/** �����-������ ��� ���������� � ����� ������ */
public class ConnectWrap {
	private Connection connection;
	private Session session;

	/** �����-������ ��� ���������� � ����� ������ */
	public ConnectWrap(Connection connection, Session session){
		this.connection=connection;
		this.session=session;
	}
	
	public Connection getConnection(){
		return this.connection;
	}
	
	public Session getSession(){
		return this.session;
	}
	
	/** ������� ��� ���������� � ����� ������ */
	public void close(){
		try{
			this.session.close();
		}catch(Exception ex){};
		try{
			this.connection.close();
		}catch(Exception ex){};
	}
	

	@Override
	public void finalize(){
		this.close();
	}
}
