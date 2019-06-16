package database;
import java.sql.Connection;

public interface IConnector {
	/** получить соединение из Pool */
	public Connection getConnection();
	/** получить соединение из Pool, по UserName и по Password*/
	public Connection getConnection(String user, String password);
}
