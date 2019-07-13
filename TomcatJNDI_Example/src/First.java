

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javax.naming.InitialContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class First
 */
public class First extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request, response);
	}


	protected void doProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String returnValue="ERROR";
		try{
			String valueForSave=request.getParameter("value");
			InitialContext context=new InitialContext();
			context.bind("test_value_for_tomcat", valueForSave);
			returnValue="save OK";
			System.out.println(" Save OK ");
		}catch(Exception ex){
			System.err.println("doProcess Exception: "+ex.getMessage());
		}
		
		BufferedWriter out=new BufferedWriter(new PrintWriter(response.getOutputStream()));
		out.write("<html>");
		out.write("<head>");
		out.write("</head>");
		out.write("<body>");
		out.write("<h1>");
		out.write(returnValue);
		out.write("</h1>");
		out.write("</body>");
		out.write("</html>");
		out.close();
	}

}
