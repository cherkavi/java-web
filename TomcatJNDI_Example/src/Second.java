

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javax.naming.InitialContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Second
 */
public class Second extends HttpServlet {
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
		String returnValue=null;
		try{
			InitialContext context=new InitialContext();
			/** значение будет получено только если в случае перезагрузки/старта сервера была вызывана функция Bind, иначе возвращает null */
			returnValue=(String)context.lookup("test_value_for_tomcat");
			System.out.println("Read OK ");
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
