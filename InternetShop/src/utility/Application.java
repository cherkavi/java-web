package utility;
import org.apache.wicket.Request;
import org.apache.wicket.Response;
import org.apache.wicket.Session;
import org.apache.wicket.protocol.http.WebApplication;

import gui.find_commodity.FindCommodity;


public class Application extends WebApplication{
	public Application(){
		
	}

	@Override
	public Class<?> getHomePage() {
		return FindCommodity.class;
	}
	
	public Session newSession(Request request, Response response){
		return new ShopSession(request);
	}
	
	public String getConfigurationType(){
		//return Application.DEPLOYMENT;
		return Application.DEVELOPMENT; 
	}
	
}
