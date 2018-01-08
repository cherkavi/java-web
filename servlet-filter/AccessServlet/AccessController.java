package bonclub.office_private.web_service.access_control;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;


public class AccessController implements Filter{
	private final static String WHITE_LIST="white_list";
	/** when each one from parameters is 0.0.0.0 */
	private boolean openForAll=false; 
	private String[] whiteList=null;
	
	@Override
	public void destroy() {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
		if(filterAllows(request)){
			filterChain.doFilter(request, response);
		}else{
			RequestDispatcher dispatcher=((HttpServletRequest)request).getRequestDispatcher("/error.html");
			dispatcher.forward(request, response);
		}
	}
	
	

	private boolean filterAllows(ServletRequest request) {
		if(this.openForAll==true){
			return true;
		}
		if(this.whiteList==null){
			return true;
		}
		String requestIp=request.getRemoteAddr();
		for(int counter=0;counter<this.whiteList.length;counter++){
			if(this.whiteList[counter].equals(requestIp)){
				return true;
			}
		}
		return false;
	}

	@Override
	public void init(FilterConfig config) throws ServletException {
		String parameter=config.getInitParameter(WHITE_LIST);
		if((parameter!=null)&&(parameter.trim().length()>0)){
			this.whiteList=config.getInitParameter(WHITE_LIST).trim().split(",");
			for(int index=0;index<whiteList.length;index++){
				whiteList[index]=whiteList[index].replaceAll("[^0-9^.]", " ");
				if(whiteList[index].equals("0.0.0.0")){
					openForAll=true;
					return;
				}
			}
		}else{
			openForAll=true;
		}
	}

}
