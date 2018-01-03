package database;

import java.sql.Connection;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class Database {

	private DataSource dataSource;
	
	private Database(String jndiname) {
		try {
			dataSource = (DataSource) new InitialContext().lookup("java:comp/env/" + jndiname);
		} catch (NamingException e) {
			// Handle error that it's not configured in JNDI.
			System.err.println("JNDI Exception: "+e.getMessage());
		}
	}
	
	/** 
	 * @return Получить соединение с базой данных 
	 */
	public Connection getConnection(){
		try {
			return this.dataSource.getConnection();
		} catch (Exception e) {
			System.err.println("Get connection with database Exception:"+e.getMessage());
			return null;
		}
	}
	
	private static Database instance=null;
	
	/** получить соединение с базой данных  */
	public static Database getInstance(){
		if(instance==null){
			synchronized(Database.class){
				if(instance==null){
					instance=new Database("jdbc/db");
				}
			}
		}
		return instance;
	}
}
