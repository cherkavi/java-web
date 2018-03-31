package database;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;

public class StaticConnector {
	/** соединение с базой данных */
	static Connector connector;
	static{
		setLogger("com.cherkashin.vitaliy.computer_shop");
		// setLogger("org.hibernate");
	}
	
	private static void setLogger(String packageName){
		Logger.getLogger(packageName).setLevel(Level.DEBUG);
		Logger.getLogger(packageName).addAppender(new ConsoleAppender(new PatternLayout()));
	}

	@SuppressWarnings("unused")
	private static void setLogger(String packageName, Level level){
		Logger.getLogger(packageName).setLevel(level);
		Logger.getLogger(packageName).addAppender(new ConsoleAppender(new PatternLayout()));
	}
	 
	static{
		try {
			connector=new Connector();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**  
	 * @return получить соединение с базой данных ({@link ConnectWrap}) 
	 * */
	public static ConnectWrap getConnectWrap(){
		return connector.getConnector();
	}
}
