package database;

import java.sql.Connection;

import org.hibernate.Session;

/** класс-обЄртка дл€ соединени€ с базой данных */
public class ConnectWrap {
	private Connection connection;
	private Session session;

	/** класс-обЄртка дл€ соединени€ с базой данных */
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
	
	/** «акрыть все соединени€ с базой данных */
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
