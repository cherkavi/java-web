package database;

import org.hibernate.Session;

import utility.Loader;

import database.firebird.HibernateFirebirdConnect;

/** класс, который выдает Session */
public class Connector {
	static{
		try{
			Loader loader=new Loader("c:\\settings.xml");
			HibernateFirebirdConnect.open(loader.getString("//SETTINGS/PATH_TO_DATABASE", "").trim(), "SYSDBA", "masterkey", 50);
			//HibernateFirebirdConnect.open("D:\\eclipse_workspace\\InternetShop\\Information\\DataBase\\server_data.GDB", "SYSDBA", "masterkey", 50);
		}catch(Exception ex){
			System.out.println("File settings.xml is not found: "+ex.getMessage());
		}
	}
	
	public static Session getSession(){
		return HibernateFirebirdConnect.getSession();
	}
	
	public static void close(){
		HibernateFirebirdConnect.close();
	}
}
