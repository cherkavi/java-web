package database;
import java.sql.Connection;

/** класс, который возвращает соедиение с базой данных на основании расширения */
public class MySqlConnection implements IConnector{
	/** UserName*/
	private String userName="root";
	/** password*/
	private String password="";
	/** текущий Pool соединений с базой данных */
	private ConnectorPool connectorPool;
	/** имя драйвера JDBC Firebird */
	private static String driverName = "org.gjt.mm.mysql.Driver";
	
	/** коннектор к базе данных Firebird, 
	 * @param host (nullable 127.0.0.1)
	 * @param port (nullable 3306)
	 * @param databaseName 
	 * @param userName (nullable root)
	 * @param password (nullable "")
	 * @throws Exception - если не удалось создать соединение с базой данных 
	 */
	public MySqlConnection(String host, 
						   Integer port, 
						   String databaseName, 
						   String userName, 
						   String password) throws Exception {
		this.userName=(userName==null)?this.userName:userName;
		this.password=(password==null)?this.password:password;
		String url="jdbc:mysql://"+
		           ((host==null)?"127.0.0.1":host)+":"+
		           ((port==null)?(3306):port)+"/"+
		           databaseName;
		this.connectorPool=new ConnectorPool(driverName, url, this.userName, this.password);
	}
	

	@Override
	public Connection getConnection() {
		return this.connectorPool.getConnection();
	}

	@Override
	public Connection getConnection(String user, String password) {
		return this.connectorPool.getConnection(user,password);
	}
	
}
