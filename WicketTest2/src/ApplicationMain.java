import main.Main;

import org.apache.wicket.Page;
import org.apache.wicket.protocol.http.WebApplication;


public class ApplicationMain extends WebApplication{

	@Override
	public Class<? extends Page> getHomePage() {
		return Main.class;
	}

}
