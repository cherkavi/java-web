package database_reflect.database;

import java.sql.Connection;

import org.hibernate.Session;

/** класс-обёртка для соединения с базой данных */
public class ConnectWrap {
	private Connection connection;
	private Session session;

	/** класс-обёртка для соединения с базой данных */
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
	
	/** Закрыть все соединения с базой данных */
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
		System.out.println("ConnectWrap finalize");
		// если оставить, то getConnectWrap().getConnection() - вызовет finalize при сборке мусора
		this.close();
	}
}
