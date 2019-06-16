package bonpay.partner.database;
import java.io.File;
import java.io.IOException;
import org.hibernate.Session;
import java.sql.Connection;
import bonpay.partner.database.wrap.*;

/** класс, который производит соединение с базой данных, посредстом Hibernate и Connection */
public class Connector {
	// INFO - место присоединения всех классов для активации отображения объектов базы данных на объекты программы
	private Class<?>[] classOfDatabase=new Class[]{Satellite.class,
												   SatelliteClients.class,
												   SatelliteClientsParameters.class,
												   SatelliteClientState.class};
	private IConnector connector=null;
	private HibernateConnection hibernateConnection;
	
	public Connector() throws Exception{
		this(new File("D:\\eclipse_workspace\\BonPay\\DataBase\\bonpay.gdb"));
	}
	
	/** произвести соединение с базой данных посредством файла*/
	public Connector(File file) throws Exception {
		while(true){
			if(file.exists()==false){
				throw new IOException("file is not exists:"+file.getAbsolutePath());
			}
			// попытка определения Firebird 
			if(file.getName().toUpperCase().endsWith(".GDB")){
				System.out.println("Connector created:");
				this.connector=new FirebirdConnection(file);
				hibernateConnection=new HibernateConnection(this.connector.getConnection(),"org.hibernate.dialect.FirebirdDialect",classOfDatabase);
				break;
			}
			throw new Exception("algorithm is not found");
		}
	}
	
	/** получить Hibernate Session */
	public Session openSession(){
		return this.hibernateConnection.openSession();
	}
	
	/** получить соединение с базой данных */
	public Connection getConnection(){
		return this.connector.getConnection();
	}
}
