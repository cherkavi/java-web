package wicket_extension;

import org.apache.wicket.Request;
import org.apache.wicket.protocol.http.WebSession;

public class UserSession extends WebSession{
	private final static long serialVersionUID=1L;
	private User user;
	
	public UserSession(Request request){
		super(request);
	}

	/**
	 * @return the user
	 */
	public User getUser() {
		return user;
	}

	/**
	 * @param user the user to set
	 */
	public void setUser(User user) {
		this.user = user;
	}
}
