package temp;

import java.io.IOException;
import java.text.SimpleDateFormat;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class temp_servlet
 */
public class temp_servlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public temp_servlet() {
        super();
        System.out.println("servlet constructor");
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("doGet");
		doResponse(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("doPost");
		doResponse(request, response);
	}
	
	protected void doResponse(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().write("<html><body><h1>"+new SimpleDateFormat("HH:mm:ss").format(new java.util.Date())+"</h1></body></html>");
	}
	@Override
	public void init() throws ServletException {
		System.out.println("init");
		super.init();
	}
	
	@Override
	public void destroy() {
		System.out.println("destroy");
		super.destroy();
	}

}
