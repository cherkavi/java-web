package database;

public class StaticConnector {
	private static Connector connector;
	/** полное имя драйвера  */
	public static DatabaseDrivers driver;
	/** полный путь к базе данных */
	public static String url;
	/** имя пользователя */
	public static String userName;
	/** пароль */
	public static String password;
	
	/** получить соединение с базой данных  */
	public static ConnectWrap getConnectWrap(){
		if(connector==null){
			try{
				connector=new Connector(driver, url, userName, password);
			}catch(Exception ex){
				System.err.println("StaticConnector init Exception: "+ex.getMessage());
				System.exit(1);
			}
		}
		return connector.getConnector();
	}
}
