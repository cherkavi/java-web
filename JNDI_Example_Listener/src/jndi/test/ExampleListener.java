package jndi.test;

import java.io.IOException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.event.EventContext;
import javax.naming.event.EventDirContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.naming.NamingContext;

/**
 * Servlet implementation class ExampleListener
 */
public class ExampleListener extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ExampleListener() {
        super();
        try{
        	Context context=new InitialContext();
        	try{
        		context.bind("/share_name", "");
        	}catch(Exception ex){
        		context.rebind("/share_name", "");
        	}
            EventDirContext namingContext= (EventDirContext)(new InitialContext().lookup("/"));
            //EventContext ctx=(EventContext)dirContext;
            namingContext.addNamingListener("/", EventContext.OBJECT_SCOPE, new ObjectListener("share_name"));
        }catch(Exception ex){
        	System.out.println("Exception:"+ex.getMessage());
        }
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

}
