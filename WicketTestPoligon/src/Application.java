import org.apache.wicket.protocol.http.WebApplication;


public class Application extends WebApplication{
	public Application(){
		
	}

	@Override
	public Class<?> getHomePage() {
		return WebTest.class;
	}
}
