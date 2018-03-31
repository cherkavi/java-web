package wicket_extension;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Properties;
import org.apache.wicket.Request;
import org.apache.wicket.Response;
import org.apache.wicket.Session;
import org.apache.wicket.protocol.http.WebApplication;
import database.ConnectWrap;

public abstract class UserApplication extends WebApplication{
	private Properties properties=new Properties();
	
	@Override
	public Session newSession(Request request, Response response) {
		return new UserSession(request);
	}
	
	public void setProperties(String key, String value){
		this.properties.setProperty(key, value);
	}
	
	public String getPropertis(String key){
		return this.properties.getProperty(key);
	}
	
	public abstract ConnectWrap getConnector();

	
}
