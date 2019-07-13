

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class UserServlet
 */
public class UserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doAction(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doAction(request, response);
	}

	private void doAction(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Object sessionObject=request.getSession().getAttribute("user_zk_attribute");
		String outputValue=null;
		if(sessionObject!=null){
			outputValue=sessionObject.getClass().getName();
			System.out.println("Session Object:"+outputValue);
		}
		
		ServletOutputStream out=response.getOutputStream();
		out.println("<html>");
		out.println("	<head>");
		out.println("		<title>user servlet</title>");
		out.println("	</head>");
		out.println("	<body>");
		out.println("	session user object: "+outputValue);
		out.println("	</body>");
		out.println("</html>");
	}
	
}
