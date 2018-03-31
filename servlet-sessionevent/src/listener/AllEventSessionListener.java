package listener;
import javax.servlet.http.HttpSession;

import org.apache.catalina.SessionEvent;
import org.apache.catalina.SessionListener;
public class AllEventSessionListener implements SessionListener{

	@Override
	public void sessionEvent(SessionEvent event) {
		System.out.println("--------------------------");
		Object data=event.getData();
		System.out.println("Data: "+data.getClass().getName());
		Object source=event.getSource();
		System.out.println("Source: "+source.getClass().getName());
		Object type=event.getType();
		System.out.println("Type: "+type.getClass().getName());
		Object session=event.getSession();
		System.out.println("Session: "+session.getClass().getName());
		System.out.println("--------------------------\n");
	}

}
