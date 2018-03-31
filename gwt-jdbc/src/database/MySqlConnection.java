package database;
import java.sql.Connection;

/** �����, ������� ���������� ��������� � ����� ������ �� ��������� ���������� */
public class MySqlConnection implements IConnector{
	/** UserName*/
	private String userName="root";
	/** password*/
	private String password="";
	/** ������� Pool ���������� � ����� ������ */
	private ConnectorPool connectorPool;
	/** ��� �������� JDBC Firebird */
	private static String driverName = "org.gjt.mm.mysql.Driver";
	
	/** ��������� � ���� ������ Firebird, 
	 * @param host (nullable 127.0.0.1)
	 * @param port (nullable 3306)
	 * @param databaseName 
	 * @param userName (nullable root)
	 * @param password (nullable "")
	 * @throws Exception - ���� �� ������� ������� ���������� � ����� ������ 
	 */
	public MySqlConnection(String host, 
						   Integer port, 
						   String databaseName, 
						   String userName, 
						   String password) throws Exception {
		this.userName=(userName==null)?this.userName:userName;
		this.password=(password==null)?this.password:password;
		// jdbc:mysql://hostname/dbname?user=username&password=passwordstring&useUnicode=true&characterEncoding=Cp1251
		String url="jdbc:mysql://"+
		           ((host==null)?"127.0.0.1":host)+":"+
		           ((port==null)?(3306):port)+"/"+
		           databaseName;
		url=url+"?user="+userName+"&password="+password+"&useUnicode=true&characterEncoding=Cp1251";
		
		/*Properties connInfo = new Properties();

		connInfo.put("user",user);
		connInfo.put("password",pass);

		connInfo.put("useUnicode","true");
		connInfo.put("characterEncoding","Cp1251");
		this.connectorPool=new ConnectorPool(driverName, url, connInfo);
		*/

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
