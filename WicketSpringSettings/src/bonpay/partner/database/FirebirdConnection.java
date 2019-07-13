package bonpay.partner.database;
import java.sql.Connection;
import java.sql.SQLException;
import java.io.File;
/** класс, который возвращает соедиение с базой данных на основании расширения */
public class FirebirdConnection implements IConnector{
	/** путь к файлу-базе данных */
	private File file=null;
	/** URL к базе данных*/
	private String url=null;
	/** текущее соединение с базой данных */
	private Connection currentConnection;
	/** UserName*/
	private String userName="SYSDBA";
	/** password*/
	private String password="masterkey";
	/** получить соединение с базой данных Firebird 
	 * @param file - путь к файлу-базе данных 
	 * */
	public FirebirdConnection(File file){
		this.file=file;
	}
	
	public FirebirdConnection(String url){
		this.url=url;
	}
	
	/** возвращает соединение с базой данных на основании Connection 
	 * @return Connection либо null, если не получилось получить Connection
	 * */
	private Connection getConnection(File file){
		if(this.currentConnection==null){
			// попытка соединиться с базой данных 
			if(this.getExtension(file).equalsIgnoreCase("gdb")||this.getExtension(file).equalsIgnoreCase("fdb")){
				String path=file.getAbsolutePath().replace('\\', '/');
				this.currentConnection=this.getConnectionToFirebird(this.getUrl("", path, 0), 
															        this.userName, 
															        this.password);
				return this.currentConnection;
			}else{
				// не удалось получить соединение 
				return null;
			}
		}else{
			// вернуть уже готовое соединение 
			return this.currentConnection;
		}
	}
	/** возвращает соединение с базой данных на основании Connection 
	 * @return Connection либо null, если не получилось получить Connection
	 * */
	private Connection getConnection(String url){
		if(this.currentConnection==null){
			// попытка соединиться с базой данных 
			String path=url.replace('\\', '/');
			this.currentConnection=this.getConnectionToFirebird(path, 
														        this.userName, 
														        this.password);
			return this.currentConnection;
		}else{
			// вернуть уже готовое соединение 
			return this.currentConnection;
		}
		
	}
	
	/** возвращает соединение с базой данных на основании Connection */
	@Override
	public Connection getConnection(){
		if(this.file!=null){
			return getConnection(this.file);
		}
		if(this.url!=null){
			return getConnection(this.url);
		}
		return null;
	}
	
	private String getUrl(String path_to_server, 
    					  String path_to_database, 
    					  Integer port){
		
		String database_protocol = "jdbc:firebirdsql://";
		String database_dialect = "?sql_dialect=3";
		String database_server = null;
		String database_port = null;
		// String databaseURL =
		// "jdbc:firebirdsql://localhost:3050/d:/work/sadik/sadik.gdb?sql_dialect=3"
		// ;
		if ((path_to_server == "") || (path_to_server == null)) {
			database_server = "localhost";
		} else {
			database_server = path_to_server;
		}
		if (port == 0) {
			database_port = "3050";
		} else {
			database_port = Integer.toString(port);
		}
		return database_protocol + database_server + ":"
				+ database_port + "/" + path_to_database + database_dialect;
		
	}
	
    /** получить соедиенение с базой данных Firebird 
	 * @param url - URL
	 * @param user - пользователь 
	 * @param password - пароль пользователя 
	 * @return
	 */
	private Connection getConnectionToFirebird(String databaseURL, 
    										   String user, 
    										   String password) {
		// java.sql.Driver driver=null;
		java.sql.Connection connection = null;
		String driverName = "org.firebirdsql.jdbc.FBDriver";
		try {
			// System.out.println("Попытка загрузить драйвер");
			Class.forName(driverName);
			// System.out.println("Попытка соеднинения="+databaseURL);
			connection = java.sql.DriverManager.getConnection(databaseURL,
					user, password);
		} catch (SQLException sqlexception) {
			System.out.println("не удалось подключиться к базе данных");
		} catch (ClassNotFoundException classnotfoundexception) {
			System.out.println("не найден класс");
		}
		return connection;
	}
	
    
	/** получить расширение файла */
	private String getExtension(File file){
		String fileName=file.getName();
		int dotPosition=fileName.lastIndexOf(".");
		if(dotPosition>0){
			return fileName.substring(dotPosition+1);
		}else{
			return "";
		}
	}
    
}
