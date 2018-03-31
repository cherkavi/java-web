

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import utility.Users;

/**
 * Servlet implementation class Identifier
 */
public class Identifier extends HttpServlet {
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
		if(request.getParameter("userId")==null){
			// проверка введенного логина и пароля 
			String login=request.getParameter("login");
			String password=request.getParameter("pass");
			String userId=Users.getUserId(login, password);
			if(userId!=null){
				// пользователь опознан 
				request.setAttribute("userId", userId);
				System.out.println("UserId:"+userId);
				try{
					request.getRequestDispatcher("/wellcom.jsp").forward(request, response);
				}catch(Exception ex){
					System.out.println("Exception: "+ex.getMessage());
				}
			}else{
				// пользователь не опознан 
				response.sendRedirect(request.getContextPath()+"/index.jsp");
				request.getSession().invalidate();
			}
		}else{
			try{
				request.getRequestDispatcher("/wellcom.jsp").forward(request, response);
			}catch(Exception ex){
				System.out.println("Exception: "+ex.getMessage());
			}
		}
	}

}
