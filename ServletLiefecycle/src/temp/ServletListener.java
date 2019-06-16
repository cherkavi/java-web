package temp;

import javax.servlet.ServletContextAttributeEvent;
import javax.servlet.ServletContextAttributeListener;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletRequestAttributeEvent;
import javax.servlet.ServletRequestAttributeListener;
import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.http.HttpSessionActivationListener;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * Application Lifecycle Listener implementation class ServletListener
 *
 */
public class ServletListener implements ServletContextListener, ServletContextAttributeListener, HttpSessionListener, HttpSessionAttributeListener, HttpSessionActivationListener, HttpSessionBindingListener, ServletRequestListener, ServletRequestAttributeListener {

    /**
     * Default constructor. 
     */
    public ServletListener() {
        System.out.println("ServletListener: constructor: servlet listener created ");
    }

	/**
     * @see ServletRequestListener#requestDestroyed(ServletRequestEvent)
     */
    public void requestDestroyed(ServletRequestEvent arg0) {
    	System.out.println("ServletListener: requestDestroyed");
    }

	/**
     * @see HttpSessionAttributeListener#attributeAdded(HttpSessionBindingEvent)
     */
    public void attributeAdded(HttpSessionBindingEvent arg0) {
    	System.out.println("ServletListener: (Session) attributeAdded"); 
    }

	/**
     * @see ServletContextListener#contextInitialized(ServletContextEvent)
     */
    public void contextInitialized(ServletContextEvent arg0) {
    	System.out.println("ServletListener: (context) contextInitialized");
    }

	/**
     * @see HttpSessionActivationListener#sessionDidActivate(HttpSessionEvent)
     */
    public void sessionDidActivate(HttpSessionEvent arg0) {
    	System.out.println("ServletListener: (Session) sessionDidActivate");
    }

	/**
     * @see HttpSessionBindingListener#valueBound(HttpSessionBindingEvent)
     */
    public void valueBound(HttpSessionBindingEvent arg0) {
    	System.out.println("ServletListener: (Session) valueBound");
    }

	/**
     * @see ServletContextAttributeListener#attributeAdded(ServletContextAttributeEvent)
     */
    public void attributeAdded(ServletContextAttributeEvent arg0) {
    	System.out.println("ServletListener: (ServletContext) attributeAdded:"+arg0.getName()+"  value:"+arg0.getValue());
    }

	/**
     * @see ServletContextAttributeListener#attributeRemoved(ServletContextAttributeEvent)
     */
    public void attributeRemoved(ServletContextAttributeEvent arg0) {
    	System.out.println("ServletListener: (ServletContext) attributeRemoved");
    }

	/**
     * @see HttpSessionListener#sessionDestroyed(HttpSessionEvent)
     */
    public void sessionDestroyed(HttpSessionEvent arg0) {
    	System.out.println("ServletListener: (SessionEvent) sessionDestroyed");
    }

	/**
     * @see HttpSessionAttributeListener#attributeRemoved(HttpSessionBindingEvent)
     */
    public void attributeRemoved(HttpSessionBindingEvent arg0) {
    	System.out.println("ServletListener: (Session) attributeRemoved");
    }

	/**
     * @see ServletRequestAttributeListener#attributeAdded(ServletRequestAttributeEvent)
     */
    public void attributeAdded(ServletRequestAttributeEvent arg0) {
    	System.out.println("ServletListener: (Request) attributeAdded");
    }

	/**
     * @see HttpSessionBindingListener#valueUnbound(HttpSessionBindingEvent)
     */
    public void valueUnbound(HttpSessionBindingEvent arg0) {
    	System.out.println("ServletListener: (Session) valueUnbound");
    }

	/**
     * @see HttpSessionActivationListener#sessionWillPassivate(HttpSessionEvent)
     */
    public void sessionWillPassivate(HttpSessionEvent arg0) {
    	System.out.println("ServletListener: (Session) sessionWillPassivate");
    }

	/**
     * @see HttpSessionListener#sessionCreated(HttpSessionEvent)
     */
    public void sessionCreated(HttpSessionEvent arg0) {
    	System.out.println("ServletListener: (Session) sessionCreated");
    }

	/**
     * @see HttpSessionAttributeListener#attributeReplaced(HttpSessionBindingEvent)
     */
    public void attributeReplaced(HttpSessionBindingEvent arg0) {
    	System.out.println("ServletListener: (Session) attributeReplaced");
    }

	/**
     * @see ServletContextAttributeListener#attributeReplaced(ServletContextAttributeEvent)
     */
    public void attributeReplaced(ServletContextAttributeEvent arg0) {
    	System.out.println("ServletListener: (ServletContext) attributeReplaced");
    }

	/**
     * @see ServletRequestAttributeListener#attributeRemoved(ServletRequestAttributeEvent)
     */
    public void attributeRemoved(ServletRequestAttributeEvent arg0) {
    	System.out.println("ServletListener: (Request) attributeRemoved");
    }

	/**
     * @see ServletContextListener#contextDestroyed(ServletContextEvent)
     */
    public void contextDestroyed(ServletContextEvent arg0) {
    	System.out.println("ServletListener: (ServletContext) contextDestroyed");
    }

	/**
     * @see ServletRequestAttributeListener#attributeReplaced(ServletRequestAttributeEvent)
     */
    public void attributeReplaced(ServletRequestAttributeEvent arg0) {
    	System.out.println("ServletListener: (ServletRequest) attributeReplaced");
    }

	/**
     * @see ServletRequestListener#requestInitialized(ServletRequestEvent)
     */
    public void requestInitialized(ServletRequestEvent arg0) {
    	System.out.println("ServletListener: (ServletRequest) requestInitialized");
    }
	
}
