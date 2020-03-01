package database;

import org.hibernate.Session;

import java.sql.*;

/** �����, ������� ���������� ���������� � ����� ������, ���������� Hibernate � Connection */
public class Connector {
	// INFO - ����� ������������� ���� ������� ��� ��������� ����������� �������� ���� ������ �� ������� ���������
	private Class<?>[] classOfDatabase=null;
	{
		/*if(StaticConnector.driver.equals(DatabaseDrivers.Firebird)){
			classOfDatabase=new Class[]{
					   // INFO Hibernate determinate the classes
					database.wrap_firebird.A_USER_ID.class,
					database.wrap_firebird.A_PLANET_DOUBLE.class,
					database.wrap_firebird.A_TIME_VALUE.class,
					database.wrap_firebird.A_CITY.class
					   };
		}*/
		classOfDatabase=new Class[]{
				   // INFO Hibernate determinate the classes
				database.wrap_mysql.A_USER_ID.class,
				database.wrap_mysql.A_PLANET_DOUBLE.class,
				database.wrap_mysql.A_TIME_VALUE.class,
				database.wrap_mysql.A_CITY.class
				   };
	}
	private IConnector connector=null;
	private HibernateConnection hibernateConnection;
	
	
	/* ���������� ���������� � ����� ������ ����������� �����
	public Connector(File file) throws Exception {
		while(true){
			if(file.exists()==false){
				throw new IOException("file is not exists:"+file.getAbsolutePath());
			}
			// ������� ����������� Firebird 
			if(file.getName().toUpperCase().endsWith(".GDB")){
				System.out.println("Connector created:");
				try{
					this.connector=new DatabaseConnection(file);
				}catch(Exception ex){
					
				}
				hibernateConnection=new HibernateConnection("org.hibernate.dialect.FirebirdDialect",classOfDatabase);
				break;
			}
			throw new Exception("algorithm is not found");
		}
	}
	*/

	/** ���������� ���������� � ����� ������ ����������� �����
	 * @param driver - 
	 * @param path - ( example: "jdbc:firebirdsql:[//<HOST>[:<PORT>]/]<DB>" )
	 * @param userName - ����� 
	 * @param password - ������ 
	 * @throws  - ���������� �� ����� ���������� � ����� ������ 
	 * */
	public Connector(DatabaseDrivers driver, String path, String userName, String password) throws Exception {
		System.out.println("Connector created:");
		this.connector=new DatabaseConnection(driver.getDriverName(), path, userName, password);
		hibernateConnection=new HibernateConnection(driver.getHibernateDialect(),classOfDatabase);
	}
	
	
	
	/** �������� Hibernate Session */
	private Session openSession(Connection connection){
		return this.hibernateConnection.openSession(connection);
	}
	
	/** �������� ���������� � ����� ������ */
	private Connection getConnection(){
		return this.connector.getConnection();
	}
	
	public ConnectWrap getConnector(){
		Connection connection=this.getConnection();
		Session session=this.openSession(connection);
		return new ConnectWrap(connection,session);
	}
	
	public static void main(String[] args) throws Exception {
		System.out.println("-- begin --");
		StaticConnector.driver=DatabaseDrivers.MySQL;
		StaticConnector.url=DatabaseDrivers.MySQL.getUrl("localhost", "computer_shop_resource");
		StaticConnector.userName="root";
		StaticConnector.password="root";
			
		/* Connector connector=new Connector(DatabaseDrivers.Firebird,
										  DatabaseDrivers.Firebird.getUrl("localhost", 3050, "astronomy_gwt") ,
										  "SYSDBA",
										  "masterkey"); */
		Connector connector=new Connector(DatabaseDrivers.MySQL,
										  DatabaseDrivers.MySQL.getUrl("localhost", "computer_shop_resource") ,
										  "root", 
										  "root");
		System.out.println("Connector: "+connector.getConnector());
		System.out.println("-- end --");
		Connection connection=connector.getConnection();
		DatabaseMetaData metaData=connection.getMetaData();
		ResultSet rs=metaData.getClientInfoProperties();
		
		int columnCount=rs.getMetaData().getColumnCount();
		while(rs.next()){
			for(int counter=0;counter<columnCount;counter++){
				System.out.print(counter+" : "+rs.getString(counter));
			}
			System.out.println();
		}
	}
}
