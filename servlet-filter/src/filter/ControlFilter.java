package filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ControlFilter implements Filter {

	@Override
	public void destroy() {
		
	}

	@Override
	public void doFilter(ServletRequest request, 
						 ServletResponse response,
						 FilterChain filterChain) throws IOException, ServletException {
		System.out.println("ControlFilter doFilter BEGIN");
		if(request.getParameterNames().hasMoreElements()){
			System.out.println("parameter exists - error");
			System.out.println("Redirect: "+((HttpServletRequest)request).getContextPath()+"/ErrorPage");
			//((HttpServletResponse)response).sendRedirect(((HttpServletRequest)request).getContextPath()+"/ErrorPage");
			RequestDispatcher dispatcher=((HttpServletRequest)request).getRequestDispatcher("/ErrorPage");
			dispatcher.forward(request, response);
		}else{
			System.out.println("parameter is not exists");
			filterChain.doFilter(request, response);
		}
		
		System.out.println("ControlFilter doFilter END");
	}

	@Override
	public void init(FilterConfig config) throws ServletException {
		
	}

}
