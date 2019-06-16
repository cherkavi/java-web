

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import common.CommonBean;

/**
 * Servlet implementation class Temp
 */
public class Temp extends HttpServlet {
	private static final long serialVersionUID = 1L;


	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doService(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doService(request, response);
	}

	private SimpleDateFormat sdf=new SimpleDateFormat("dd.MM.yyyy HH:mm:ssss");
	
	protected void doService(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("begin");
		Context enviropment=null;
		CommonBean bean=null;
		String exceptionMessage=null;
		try{
			System.out.println("attempt create Context");
			enviropment=(Context)new InitialContext().lookup("java:comp/env");
			System.out.println("Context is created, try getting bean ");
			bean=(CommonBean)enviropment.lookup("root_directory/beans/example_bean");
		}catch(NamingException ne){
			System.err.println("Resolved Name:"+ne.getResolvedName()+"   Message: "+ne.getMessage());
			exceptionMessage=ne.getMessage();
		}catch(Exception ex){
			System.err.println("Temp#doService Exception:"+ex.getMessage());
			exceptionMessage=ex.getMessage();
		}finally{
			try{
				enviropment.close();
			}catch(Exception ex){};
		}

		PrintWriter out=response.getWriter();
		if(bean==null){
			out.println("<html>");
			out.println("	<head>");
			out.println("      <title> Error</title>");
			out.println("	</head>");
			out.println("	<body>");
			out.println(" Error in process JNDI:"+exceptionMessage);
			out.println("	</body>");
			out.println("</html>");
		}else{
			bean.setMessage(sdf.format(new java.util.Date()));
			out.println("<html>");
			out.println("	<head>");
			out.println("      <title> Ok</title>");
			out.println("	</head>");
			out.println("	<body>");
			out.println(" Object has been injecting from storage: "+bean.hashCode());
			out.println("	</body>");
			out.println("</html>");
		}
		out.close();
		System.out.println("end");
	}
}
