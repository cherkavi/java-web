package test;

import java.util.Properties;

import javax.jms.Connection;
import javax.jms.TopicConnection;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NameClassPair;
import javax.naming.NamingEnumeration;

import org.apache.activemq.ActiveMQConnection;

// http://activemq.apache.org/jndi-support.html
public class EnterPoint {
	public static void main(String[] args){
		System.out.println("begin");
		Properties props = new Properties();
		
		props.setProperty(Context.INITIAL_CONTEXT_FACTORY,
		  "org.apache.activemq.jndi.ActiveMQInitialContextFactory");
		props.setProperty(Context.PROVIDER_URL, "tcp://localhost:61616");
		
		// props.setProperty("java.naming.security.principal", "system");
		// props.setProperty("java.naming.security.credentials", "manager");
		Context context = null;
		try{
			// Class.forName("org.activemq.jndi.ActiveMQInitialContextFactory");
			context = new InitialContext(props);
			
			System.out.println("Context getted:"+context.getClass().getName());
			
			// System.out.println("FullName into Context: "+context.getNameInNamespace());
			
			// contextList(initContext);
			
			// putObject(context, "java:comp/edsanv/shared",new SharedObject("this is temp value "));
			javax.jms.TopicConnectionFactory factory = (javax.jms.TopicConnectionFactory)context.lookup("ConnectionFactory");
			System.out.println("Factory: "+factory);
			TopicConnection connection = (TopicConnection)factory.createConnection();
			
			System.out.println("Connection: "+connection.getClass().getName());
			// System.out.println(getObject(context, "shared_object"));
			context.close();
		}catch(Exception ex){
			System.err.println("Context Exception: "+ex.getMessage());
			ex.printStackTrace(System.out);
		}finally{
			try{
				context.close();
			}catch(Exception ex){};
		}
		System.out.println("-end-");
	}
	
	private static void putObject(Context context, String path, SharedObject object) throws Exception{
		context.bind(path, object);
	}
	
	private static SharedObject getObject(Context context, String path) throws Exception{
		Object returnValue=context.lookup(path);
		System.out.println("Context getted: "+returnValue);
		return (SharedObject)returnValue;
	}
	
	@SuppressWarnings("unused")
	private static void contextList(Context context) throws Exception {
		NamingEnumeration<NameClassPair> enumerration = context.list("");
		while (enumerration.hasMore())
		  {
			NameClassPair o = enumerration.next();
		    System.out.println("ClassName: "+o.getClassName()+"   Name: "+o.getName()+"    Name in Namespace: "+o.getNameInNamespace());
		  }
	}
	
}
