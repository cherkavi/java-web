package database;

public class ConnectWrapFactory {
	private static Connector connector;
	
	static{
		try{
			connector=new Connector("parking_heart_beat","technik","technik");
		}catch(Exception ex){
			System.err.println("ConnectWrapFactory#initialization Exception: "+ex.getMessage());
		}
	}

	/** получить соединение с базой данных */
	public static ConnectWrap getConnectWrap(){
		synchronized (connector) {
			ConnectWrap returnValue=null;
			returnValue=connector.getConnector();
			return returnValue;
		}
	}
}
