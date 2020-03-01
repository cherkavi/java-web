package database;

public class StaticConnector {
	private static Connector connector;
	/** ������ ��� ��������  */
	public static DatabaseDrivers driver;
	/** ������ ���� � ���� ������ */
	public static String url;
	/** ��� ������������ */
	public static String userName;
	/** ������ */
	public static String password;
	
	/** �������� ���������� � ����� ������  */
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
