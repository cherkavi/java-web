package jndi.example;

import java.io.IOException;
import java.io.PrintWriter;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Binder
 */
public class Binder extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Binder() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doAction(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doAction(request,response);
	}

	private void doAction(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out=response.getWriter();
		String value=request.getParameter("name");
		if(value==null){
			out.println("<form>");
			out.println("<b>Value:</b>");
			out.println("<input type=\"text\" name=\"name\" value=\"\"  >");
			out.println("<input type=\"submit\" value=\"send\" > ");
			out.println("</form>");
		}else{
			try{
				Context context= new InitialContext();
				context.bind("share_name", value); // имя для связки в JNDI
				out.println("Binded:"+value);
			}catch(Exception ex){
				out.println("<font color=\"red\">");
				out.println(value+"   does not added");
				out.println("</font>");
			}
		}
	}
}
