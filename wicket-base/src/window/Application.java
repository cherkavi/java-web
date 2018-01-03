package window;

import java.sql.Connection;
import java.sql.PreparedStatement;


import org.apache.wicket.Page;
import database.ConnectWrap;
import database.Connector;
import wicket_extension.UserApplication;
import window.main.Main;

public class Application extends UserApplication{
	private Connector connector;

	@Override
	protected void init() {
		
	}
	
	public Application(){
		/** положить путь к сформированным чекам в Properties*/
		this.setProperties("path_to_recipient", "/temp");
		this.setProperties("path_to_icon", "/avtozvuk_icon/");
		try{
			connector=new Connector("avtozvuk","technik","technik");
		}catch(Exception ex){
			System.err.println("Application#constructor create Connector Error: "+ex.getMessage());
		}
		
	}
	
	@Override
	public Class<? extends Page> getHomePage() {
		return Main.class;
	}

	@Override
	public ConnectWrap getConnector() {
			return connector.getConnector();
	}

	
}
