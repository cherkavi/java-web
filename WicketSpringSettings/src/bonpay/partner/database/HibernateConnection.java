package bonpay.partner.database;
import java.sql.Connection;
import java.util.Properties;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;

/** класс, который создает Hibernate.Session на основании Connection */
public class HibernateConnection {
	private SessionFactory sessionFactory=null;
	private Connection connection=null;
	
	/** создать на базе Connection Hibernate надстройку 
	 * @param hibernateDialect - "org.hibernate.dialect.FirebirdDialect"
	 * ( example for HSQLDB - org.hsqldb.jdbcDriver)
	 * @throws выбрасывает исключение в случае, когда не удалось создать Hibernate 
	 * */
	public HibernateConnection(String hibernateDialect,Class<?> ... classes) throws Exception{
		sessionFactory=getAnnotationConfiguration(hibernateDialect,classes).buildSessionFactory();		
	}

	/** создать на базе Connection Hibernate надстройку 
	 * @param hibernateDialect - "org.hibernate.dialect.FirebirdDialect"
	 * @throws выбрасывает исключение в случае, когда не удалось создать Hibernate 
	 * */
	public HibernateConnection(Connection connection, String hibernateDialect,Class<?> ... classes) throws Exception{
		this.connection=connection;
		sessionFactory=getAnnotationConfiguration(hibernateDialect,classes).buildSessionFactory();		
	}
	
	private AnnotationConfiguration getAnnotationConfiguration(String hibernateDialect,Class<?> ... classes){
		AnnotationConfiguration aconf = new AnnotationConfiguration();
		Properties properties=new Properties();
		properties.put("hibernate.dialect", hibernateDialect);
		//properties.put("hibernate.connection.driver_class", "org.firebirdsql.jdbc.FBDriver");
		//properties.put("hibernate.connection.url", "jdbc:hsqldb:mem:baseball");
		//properties.put("hibernate.connection.username", "sa");
		//properties.put("hibernate.connection.password", "");
		properties.put("hibernate.connection.pool_size", "10");
		properties.put("hibernate.connection.autocommit", "false");
		//properties.put("hibernate.cache.provider_class", "org.hibernate.cache.HashtableCacheProvider");
		//properties.put("hibernate.hbm2ddl.auto", "create-drop");
		//properties.put("hibernate.show_sql", "true");
		aconf.setProperties(properties);
		for(int counter=0;counter<classes.length;counter++){
			aconf.addAnnotatedClass(classes[counter]);
		}
		return aconf;
	}
	/** get Hibernate Session */
	public Session getSession(Connection connection){
		return sessionFactory.openSession(connection);
	}
	
	/** get Hibernate Session */
	public Session openSession(){
		try{
			return sessionFactory.openSession(this.connection);
		}catch(Exception ex){
			System.err.println("openSession Exception: "+ex.getMessage());
			return null;
		}
	}
	
	/** get Connection */
	public Connection getConnection(){
		return this.connection;
	}
	
	public void close(){
		System.out.println("close");
		this.sessionFactory.close();
		this.connection=null;
	}
	
	public void finalize(){
		this.close();
	}
}
