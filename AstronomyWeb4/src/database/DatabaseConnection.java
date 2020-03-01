package database;

import java.sql.Connection;

/** �����, ������� ���������� ��������� � ����� ������ �� ��������� ���������� */
public class DatabaseConnection implements IConnector{
	// UserName
	// private String userName="SYSDBA";
	// password
	// private String password="masterkey";
	// ������� Pool ���������� � ����� ������ 
	private ConnectorPool connectorPool;
	// ��� �������� JDBC Firebird 
	// private static String driverName = "org.firebirdsql.jdbc.FBDriver";
	
	/** ��������� � ���� ������ Firebird,
	 * @param driverName - (org.firebirdsql.jdbc.FBDriver)
	 * @param url - ������ ���� � ���� ������ (jdbc:firebirdsql://)
	 * @param userName - ������ ��� 
	 * @param password - ������
	 * @throws Exception - ���� �� ������� ������� ���������� � ����� ������ 
	 */
	public DatabaseConnection(String driverName, String url, String userName, String password) throws Exception {
		this.connectorPool=new ConnectorPool(driverName, url, userName, password);
	}
	
	/* public DatabaseConnection(File file) throws Exception{
		this.connectorPool=new ConnectorPool(driverName, this.getUrlFromFile(null, file.getAbsolutePath().replace('\\', '/'),null), userName, password);
	}*/
	
	/* �������� �� ����� ������ URL ��� ���� ������ Firebird 
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
        return database_protocol+database_server+":"+database_port+"/"+path+database_dialect;
	}
	*/

	@Override
	public Connection getConnection() {
		return this.connectorPool.getConnection();
	}

	@Override
	public Connection getConnection(String user, String password) {
		return this.connectorPool.getConnection(user,password);
	}
	
}
