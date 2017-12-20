package com.cherkashyn.vitalii.computer_shop.rediscount.client.output;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.cherkashyn.vitalii.computer_shop.rediscount.client.restwrapper.ParametersName;

/**
 * Servlet Filter implementation class RediscountFilter
 */
public class RediscountFilter implements Filter {

    /**
     * Default constructor. 
     */
    public RediscountFilter() {
    }


	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		// check for parameters into Request 
		if( request.getParameter(ParametersName.PARAMETER_POINT_NUMBER)!=null &&  request.getParameter(ParametersName.PARAMETER_REDISCOUNT_DATE )!=null){
			chain.doFilter(request, response);
		}else{
			HttpSession session=((HttpServletRequest)request).getSession(false);
			// check for Attributes into request 
			if(session!=null && session.getAttribute(ParametersName.PARAMETER_POINT_NUMBER)!=null &&  session.getAttribute(ParametersName.PARAMETER_REDISCOUNT_DATE )!=null){
				chain.doFilter(request, response);
			}else{
				request.getRequestDispatcher("/index.jsp").forward(request, response);
			}
		}
		
	}


	public void destroy() {
	}


	public void init(FilterConfig arg0) throws ServletException {
	}

}
