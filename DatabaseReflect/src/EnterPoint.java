import java.sql.Connection;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;

import database_reflect.database.ConnectWrap;
import database_reflect.database.Connector;
import database_reflect.database.IConnectorAware;
import database_reflect.finder.RecordFinder;
import database_reflect.sender.Sender;
import database_reflect.wrapper.TableWrap;
import database_reflect.wrapper.wrap.WrapCustomer;


public class EnterPoint {
	public static void main(String[] args){
		System.out.println("begin");
		Logger.getLogger("org.hibernate").setLevel(Level.ERROR);
		Logger.getLogger("org.hibernate").addAppender(new ConsoleAppender(new PatternLayout()));
		Logger.getLogger("org.codehaus.xfire").setLevel(Level.ERROR);
		Logger.getLogger("org.codehaus.xfire").addAppender(new ConsoleAppender(new PatternLayout()));
		
		// создать connection
		ConnectionAware connectionAware=null;
		try{
			Connector connector=null;
			connector=new Connector("computer_shop_cartridge");
			connectionAware=new ConnectionAware(connector);
		}catch(Exception ex){};
		
		
		// создать Finder
		TableWrap customer=new WrapCustomer();
		RecordFinder recordFinder=new RecordFinder(customer);
		
		// создать Sender
		Sender sender=new Sender("http://localhost:8080/DatabaseReflectServer");
		ConnectWrap connectWrap=connectionAware.getConnector();
		Connection connection=connectWrap.getConnection();
		// получить очередную запись
		Object objectForSend=recordFinder.getNextFindRecord(connection);
		if(objectForSend!=null){
			// послать запись			
			String response=sender.sendData(objectForSend);
			if(response==null){
				System.out.println("Server not response ");
			}else{
				if(response.equals("OK")){
					if(recordFinder.lastRecordSetAsSended(connection)){
						System.out.println("Record was updated ");
					}else{
						System.out.println("ERROR update record ");
					}
				}else{
					System.out.println("Record send error");
				}
			}
		}
			
		System.out.println("end");
	}
	
}

class ConnectionAware implements IConnectorAware{
	private Connector connector;
	public ConnectionAware (Connector connector){
		this.connector=connector;
	}
	@Override
	public ConnectWrap getConnector() {
		return this.connector.getConnector();
	}
}
