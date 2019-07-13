package database;

import java.sql.Connection;

/** содержит соединение с базой данных  */
public class Connector {

    private static String userName=null;
    private static String password=null;
    private static String databaseUrl=null;
    private static String driverName=null;
    private static ConnectionProvider provider=null;

    static{
            initConnector(
                                                                "org.firebirdsql.jdbc.FBDriver",
                                                                "jdbc:firebirdsql://localhost:3050/D:/eclipse_workspace/TempParser/database/shop_list_parse.gdb?sql_dialect=3",
                                                                "SYSDBA",
                                                                "masterkey");

    }

    public static boolean initConnector(String driverName,
                                            String databaseUrl,
                                            String userName,
					    String password
                                            ){
            try{
                Connector.databaseUrl=databaseUrl;
                Connector.userName=userName;
                Connector.password=password;
                Connector.driverName=driverName;
                provider=new ConnectionProvider(Connector.driverName,
                                                Connector.databaseUrl,
                                                Connector.userName,
                                                Connector.password);
                return true;
            }catch(Exception ex){
                System.out.println("File settings.xml is not found: "+ex.getMessage());
                return false;
            }
	}
	
	/** получить соединение с базой данных  */
        public static Connection getConnection(){
		while(true){
			try{
                            return provider.connection();
			}catch(Exception ex){
                            System.err.println("Reconnect:"+ex.getMessage());
                            try{
                                Thread.sleep(10000);
                            }catch(Exception ex2){
                            }
                            initConnector(Connector.driverName, Connector.databaseUrl,Connector.userName, Connector.password);
			}
		}
	}
	
}
