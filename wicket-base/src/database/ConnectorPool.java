package database;

import java.sql.Connection;
import java.io.PrintStream;
import org.apache.commons.dbcp.ConnectionFactory;
import org.apache.commons.dbcp.DriverManagerConnectionFactory;
import org.apache.commons.dbcp.PoolableConnectionFactory;
import org.apache.commons.dbcp.PoolingDataSource;
import org.apache.commons.pool.impl.GenericObjectPool;

public class ConnectorPool implements IConnector{
	private String driverName;
	private String fullPath;
	private String user;
	private String password;
	PoolingDataSource poolDataSource;
	private PrintStream outError;
	
	public void setOutError(PrintStream out){
		this.outError=out;
	}
	
	private void outError(Object information){
		PrintStream currentOut=(this.outError==null)?System.out: this.outError;
		currentOut.print("ConnectionPool");
		currentOut.print(" ERROR ");
		currentOut.println(information);
	}
	
	
	/** создать объект, который содержит POOL соединений с базой данных 
	 * @param driverName - имя драйвера для соединения с JDBC
	 * @param fullPath - полный URL к базе данных 
	 * @param userName - User Name
	 * @param password - Password
	 * @throws Exception - если произошла какая-либо ошибка во время попытки соединения с базой данных
	 * */
	public ConnectorPool(String driverName, String fullPath, String userName, String password) throws Exception {
		this.driverName=driverName;
		this.fullPath=fullPath;
		this.user=userName;
		this.password=password;
		if(initPool()==false){
			throw new Exception("init Error");
		}
	}
	
	private boolean initPool() throws ClassNotFoundException {
		Class.forName(driverName);
		String connectionString = fullPath;
		GenericObjectPool connectionPool = new GenericObjectPool(null);
		connectionPool.setMaxIdle(15);
		connectionPool.setMaxActive(25);
		connectionPool.setMinEvictableIdleTimeMillis(20000);
		ConnectionFactory connectionFactory = new DriverManagerConnectionFactory(connectionString, user, password);
		PoolableConnectionFactory factory=new PoolableConnectionFactory(connectionFactory, 
									  connectionPool, 
									  null,
							          null, 
							          false, 
							          false);
		poolDataSource= new PoolingDataSource(connectionPool);
		return true;
	}
	
	@Override
	public Connection getConnection() {
		try{
			return poolDataSource.getConnection();
		}catch(Exception ex){
			outError("getConnection Exception:"+ex.getMessage());
			return null;
		}
		 
	}

	@Override
	public Connection getConnection(String user, String password){
		try{
			return poolDataSource.getConnection(user,password);
		}catch(Exception ex){
			outError("getConnection Exception:"+ex.getMessage());
			return null;
		}
	}
	 
}

