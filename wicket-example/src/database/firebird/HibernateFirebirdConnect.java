package database.firebird;

import java.util.Properties;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;

import database.wrap.*;
import database.wrap.Class;



/** �����, ������� ������������ ����������� � ���� ������, ��������� Hibernate*/
public class HibernateFirebirdConnect {
	private static SessionFactory field_session_factory=null;
	// ��� ����������� ������������� Singelton � ���� ������������ ����� ������ �� Tomcat ( ����� Garbage Collector ������ )
	@SuppressWarnings("unused")
	private static HibernateFirebirdConnect instance=null;
	
	
	/** �������� Properties ��� ���������������� AnnotationConfiguration 
	 * @param path_to_gdb ���� � �����, ��������� ����������� "/"
	 * @param login �����
	 * @param password ������
	 * @param pool_count ������ ���� ���������� 
	 * */
	private Properties getPropertiesConfiguration(String path_to_gdb,
												  String login, 
												  String password, 
												  Integer pool_count){
        Properties properties=new Properties();
        properties.setProperty("hibernate.connection.driver_class", "org.firebirdsql.jdbc.FBDriver");
        String path_to_database="jdbc:firebirdsql://localhost:3050/"+path_to_gdb+"?sql_dialect=3";
        properties.setProperty("hibernate.connection.url",path_to_database);
        properties.setProperty("hibernate.connection.username", login);
        properties.setProperty("hibernate.connection.password", password);
        //properties.setProperty("hibernate.connection.pool_size", pool_count.toString());
        properties.setProperty("hibernate.dialect", "org.hibernate.dialect.FirebirdDialect");
        properties.setProperty("hibernate.show_sql", "false");
        //properties.setProperty("hibernate.cache.provider_class", "org.hibernate.cache.NoCacheProvider");
        //properties.setProperty("hbm2ddl.auto", "update");
        //properties.setProperty("current_session_context_class", "thread");
        return properties;
	}
	
	/** ������, ������� �������� ������
	 * @param login �����
	 * @param password ������
	 * @param pool_size ������ ����
	 * */
	private HibernateFirebirdConnect(String path_to_gdb,
									String login, 
									String password, 
									int pool_size){
        try{
            AnnotationConfiguration aconf = new AnnotationConfiguration();
            // ����� ������� Properties
            Integer poolSize;
            if(pool_size<0){
            	poolSize=new Integer(0);
            }else{
            	poolSize=new Integer(pool_size);
            }
            aconf.setProperties(getPropertiesConfiguration(path_to_gdb,
            											   "SYSDBA",
            											   "masterkey",
            											   poolSize));
            // �������� ��� POJO ������� 
            //aconf.addAnnotatedClass(Users.class);
            aconf.addAnnotatedClass(Assortment.class);
            aconf.addAnnotatedClass(Class.class);
            aconf.addAnnotatedClass(Commodity.class);
            aconf.addAnnotatedClass(Money.class);
            aconf.addAnnotatedClass(Price.class);
            aconf.addAnnotatedClass(Clients.class);
            aconf.addAnnotatedClass(ClientOrder.class);
            field_session_factory=aconf.buildSessionFactory();
        }catch(Exception ex){
            System.out.println("Except:"+ex.getMessage());
        }
	}
	
	public static void open(String path_to_gdb,
							String login, 
							String password, 
							int pool_size){
		close();
		instance=new HibernateFirebirdConnect(path_to_gdb, login, password, pool_size);
	}
	
	public static Session getSession(){
		try{
			//System.out.println("HibernateFirebirdConnect: Factory:"+field_session_factory);
			return field_session_factory.openSession();
		}catch(Exception ex){
			System.out.println("HibernateFirebirdConnect: openSession: "+ex.getMessage());
			return null;
		}
	}

	/** ������� ���������� � Hibernate*/
	public static void close(){
		if(field_session_factory!=null){
			field_session_factory.close();
			field_session_factory=null;
		}
	}
	
	public void finalize(){
		System.out.println("HibertnateFirebirdConnect cleaned Garbage Collector");
		close();
	}
}
