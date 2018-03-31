package listener;

import javax.servlet.http.HttpSession;
//import org.apache.catalina.session.StandardSessionFacade;
import javax.servlet.http.HttpSessionActivationListener;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;


public class SessionCreateDestroyListener implements HttpSessionListener, 
													 HttpSessionActivationListener, 
													 HttpSessionBindingListener,
													 HttpSessionAttributeListener{
	private Object userId=null;
	
	public SessionCreateDestroyListener(){
	}
	
	@Override
	public void sessionCreated(HttpSessionEvent event) {
		System.out.println("Session Created: "+event.getSession().getId());
		//System.out.println("Create Class Event: "+event.getSource().getClass().getName());
		//StandardSessionFacade facade=((StandardSessionFacade)event.getSource());
		if(userId!=null){
			event.getSession().setAttribute("userId", userId);
		}
		userId=null;
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent event) {
		System.out.println("Session Destroyed: "+event.getSession().getId());
		//System.out.println("Destroy Class Event: "+event.getSource().getClass().getName());
	}

	@Override
	public void sessionDidActivate(HttpSessionEvent event) {
		System.out.println("sessionDidActivate: "+event.getSession().getId());
		
	}

	@Override
	public void sessionWillPassivate(HttpSessionEvent event) {
		System.out.println("sessionWillPassivate: "+event.getSession().getId());
	}

	@Override
	public void valueBound(HttpSessionBindingEvent event) {
		System.out.println("valueBound: "+event.getSession().getId());
	}

	@Override
	public void valueUnbound(HttpSessionBindingEvent event) {
		System.out.println("valueUnbound: "+event.getSession().getId());
	}

	@Override
	public void attributeAdded(HttpSessionBindingEvent event) {
		HttpSession session=event.getSession();
		System.out.println("valueAdded: "+event.getSession().getId());
		if(this.userId==null){
			if(session.getAttribute("userId")!=null){
				this.userId=event.getValue();
				System.out.println("It is not new Session - create new session: "+session.getId());
				session.invalidate();
			}
		}
	}

	@Override
	public void attributeRemoved(HttpSessionBindingEvent event) {
		System.out.println("valueRemoved: "+event.getSession().getId());
	}

	@Override
	public void attributeReplaced(HttpSessionBindingEvent event) {
		System.out.println("valueReplaced: "+event.getSession().getId());
	}

}
