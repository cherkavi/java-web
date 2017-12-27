package database;
import org.hibernate.Session;
import java.sql.Connection;


import database.wrap.*;

/** класс, который производит соединение с базой данных, посредстом Hibernate и Connection */
@SuppressWarnings("unused")
public class Connector {
	// INFO контролер.место присоединения всех классов для активации отображения объектов базы данных на объекты программы
	private Class<?>[] classesOfDatabase=new Class[]{
												   Price.class,
												   Assortment.class
												   /*Module.class,
												   ModuleAlarmCheckerWrap.class,
												   ModuleAlarmWrap.class,
												   ModuleHeartBeat.class,
												   ModuleInformationCheckerWrap.class,
												   ModuleInformationWrap.class,
												   ModuleTaskWrap.class,
												   ModuleSettings.class,
												   ModuleSettingsSection.class,
												   ModuleSettingsParameter.class,
												   ModuleSensor.class,
												   SensorType.class,
												   ModuleStorage.class,
												   ModuleRestart.class*/
												   };
	private IConnector connector=null;
	private HibernateConnection hibernateConnection;
	
	public Connector() throws Exception{
		this("server","SYSDBA","masterkey");
	}
	
	
	/** произвести соединение с базой данных посредством файла
	public Connector(File file) throws Exception {
		while(true){
			if(file.exists()==false){
				throw new IOException("file is not exists:"+file.getAbsolutePath());
			}
			// попытка определения Firebird 
			if(file.getName().toUpperCase().endsWith(".GDB")){
				System.out.println("Connector created:");
				try{
					this.connector=new FirebirdConnection(file);
				}catch(Exception ex){
					
				}
				hibernateConnection=new HibernateConnection("org.hibernate.dialect.FirebirdDialect",classOfDatabase);
				break;
			}
			throw new Exception("algorithm is not found");
		}
	}

	произвести соединение с базой данных посредством файла
	public Connector(String path) throws Exception {
		System.out.println("Connector created:");
		this.connector=new FirebirdConnection(path);
		hibernateConnection=new HibernateConnection("org.hibernate.dialect.FirebirdDialect",classOfDatabase);
	}*/
	
	/** произвести соединение с базой данных посредством файла*/
	private Connector(String path) throws Exception {
		System.out.println("Connector created:");
		this.connector=new FirebirdConnection(path);
		hibernateConnection=new HibernateConnection("org.hibernate.dialect.FirebirdDialect",classesOfDatabase);
		
		//this.connector=new MySqlConnection(null,null,path,null,null);
		//org.hibernate.dialect.MySQLDialect
		//org.hibernate.dialect.MySQL5Dialect
		//hibernateConnection=new HibernateConnection("org.hibernate.dialect.MySQL5Dialect", classesOfDatabase);
	}

	/** произвести соединение с базой данных посредством файла*/
	private Connector(String path, String userName, String password) throws Exception {
		System.out.println("Connector created:");
		this.connector=new FirebirdConnection(path,userName, password);
		hibernateConnection=new HibernateConnection("org.hibernate.dialect.FirebirdDialect",classesOfDatabase);
		
		//this.connector=new MySqlConnection(null,null,path,userName,password);
		//Connection testConnection=this.connector.getConnection();
		//System.out.println("TestConnection:"+testConnection);
		//org.hibernate.dialect.MySQLDialect
		//org.hibernate.dialect.MySQL5Dialect
		//hibernateConnection=new HibernateConnection("org.hibernate.dialect.MySQL5Dialect", classesOfDatabase);
	}
	
	
	/** получить Hibernate Session */
	private Session openSession(Connection connection){
		return this.hibernateConnection.openSession(connection);
	}
	
	/** получить соединение с базой данных */
	private Connection getConnection(){
		return this.connector.getConnection();
	}
	
	public ConnectWrap getConnector(){
		Connection connection=this.getConnection();
		Session session=this.openSession(connection);
		return new ConnectWrap(connection,session);
	}
	
	/*
	public static void main(String[] args) throws Exception {
		System.out.println("-- begin --");
		Connector connector=new Connector("fenomen","root","root");
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
	}*/
}
