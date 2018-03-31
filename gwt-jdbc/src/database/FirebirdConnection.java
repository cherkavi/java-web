package database;
import java.io.File;
import java.sql.Connection;

/** класс, который возвращает соедиение с базой данных на основании расширения */
public class FirebirdConnection implements IConnector{
	/** UserName*/
	private String userName="SYSDBA";
	/** password*/
	private String password="masterkey";
	/** текущий Pool соединений с базой данных */
	private ConnectorPool connectorPool;
	/** имя драйвера JDBC Firebird */
	private static String driverName = "org.firebirdsql.jdbc.FBDriver";
	
	/** коннектор к базе данных Firebird, 
	 * @param url - полный путь к базе данных 
	 * @throws Exception - если не удалось создать соединение с базой данных 
	 */
	public FirebirdConnection(String url) throws Exception {
		this.connectorPool=new ConnectorPool(driverName, this.getUrlFromFile(null, url, null), userName, password);
	}

	/** коннектор к базе данных Firebird, 
	 * @param url - полный путь к базе данных 
	 * @param userName - имя пользователя 
	 * @param userPassword - пароль пользователя 
	 * @throws Exception - если не удалось создать соединение с базой данных 
	 */
	public FirebirdConnection(String url, String userName, String userPassword) throws Exception {
		this.userName=userName;
		this.password=userPassword;
		this.connectorPool=new ConnectorPool(driverName, this.getUrlFromFile(null, url, null), userName, userPassword);
	}
	
	public FirebirdConnection(File file) throws Exception{
		this.connectorPool=new ConnectorPool(driverName, this.getUrlFromFile(null, file.getAbsolutePath().replace('\\', '/'),null), userName, password);
	}
	
	/** получить из файла полный URL для базы данных Firebird */
	private String getUrlFromFile(String path_to_server, String path,Integer port) {
        String database_protocol="jdbc:firebirdsql://";
        String database_dialect="?sql_dialect=3";
        String database_server=null;
        String database_port=null;
        if((path_to_server==null)||(path_to_server.equals(""))){
            database_server="localhost";
        }else{
            database_server=path_to_server;
        }
        if(port==null){
            database_port="3050";
        }else{
            database_port=Integer.toString(port);
        }
        String returnValue=database_protocol+database_server+":"+database_port+"/"+path+database_dialect;
        // System.out.println(returnValue);
        return returnValue;
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
