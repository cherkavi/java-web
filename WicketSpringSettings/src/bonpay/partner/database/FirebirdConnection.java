package bonpay.partner.database;
import java.sql.Connection;
import java.sql.SQLException;
import java.io.File;
/** �����, ������� ���������� ��������� � ����� ������ �� ��������� ���������� */
public class FirebirdConnection implements IConnector{
	/** ���� � �����-���� ������ */
	private File file=null;
	/** URL � ���� ������*/
	private String url=null;
	/** ������� ���������� � ����� ������ */
	private Connection currentConnection;
	/** UserName*/
	private String userName="SYSDBA";
	/** password*/
	private String password="masterkey";
	/** �������� ���������� � ����� ������ Firebird 
	 * @param file - ���� � �����-���� ������ 
	 * */
	public FirebirdConnection(File file){
		this.file=file;
	}
	
	public FirebirdConnection(String url){
		this.url=url;
	}
	
	/** ���������� ���������� � ����� ������ �� ��������� Connection 
	 * @return Connection ���� null, ���� �� ���������� �������� Connection
	 * */
	private Connection getConnection(File file){
		if(this.currentConnection==null){
			// ������� ����������� � ����� ������ 
			if(this.getExtension(file).equalsIgnoreCase("gdb")||this.getExtension(file).equalsIgnoreCase("fdb")){
				String path=file.getAbsolutePath().replace('\\', '/');
				this.currentConnection=this.getConnectionToFirebird(this.getUrl("", path, 0), 
															        this.userName, 
															        this.password);
				return this.currentConnection;
			}else{
				// �� ������� �������� ���������� 
				return null;
			}
		}else{
			// ������� ��� ������� ���������� 
			return this.currentConnection;
		}
	}
	/** ���������� ���������� � ����� ������ �� ��������� Connection 
	 * @return Connection ���� null, ���� �� ���������� �������� Connection
	 * */
	private Connection getConnection(String url){
		if(this.currentConnection==null){
			// ������� ����������� � ����� ������ 
			String path=url.replace('\\', '/');
			this.currentConnection=this.getConnectionToFirebird(path, 
														        this.userName, 
														        this.password);
			return this.currentConnection;
		}else{
			// ������� ��� ������� ���������� 
			return this.currentConnection;
		}
		
	}
	
	/** ���������� ���������� � ����� ������ �� ��������� Connection */
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
	
    /** �������� ����������� � ����� ������ Firebird 
	 * @param url - URL
	 * @param user - ������������ 
	 * @param password - ������ ������������ 
	 * @return
	 */
	private Connection getConnectionToFirebird(String databaseURL, 
    										   String user, 
    										   String password) {
		// java.sql.Driver driver=null;
		java.sql.Connection connection = null;
		String driverName = "org.firebirdsql.jdbc.FBDriver";
		try {
			// System.out.println("������� ��������� �������");
			Class.forName(driverName);
			// System.out.println("������� �����������="+databaseURL);
			connection = java.sql.DriverManager.getConnection(databaseURL,
					user, password);
		} catch (SQLException sqlexception) {
			System.out.println("�� ������� ������������ � ���� ������");
		} catch (ClassNotFoundException classnotfoundexception) {
			System.out.println("�� ������ �����");
		}
		return connection;
	}
	
    
	/** �������� ���������� ����� */
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
