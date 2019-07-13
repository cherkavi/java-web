package jms.id;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class JnlpGenerator
 */
public class JnlpGenerator extends HttpServlet {
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

	protected void doAction(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ServletOutputStream out=response.getOutputStream();
		response.setCharacterEncoding("utf-8");
		  out.println("<?xml version=\"1.0\" encoding=\"windows-1251\"?>	");
		  out.println("	    <jnlp	");
		  out.println("	      spec=\"1.0+\"	");
		  out.println("	      codebase=\""+getFullPathToApplication(request)+"\" ");
		  out.println("	      href=\"client.jnlp\">	");
		  out.println("	      <information>	");
		  out.println("	        <title>Test JMS</title>	");
		  out.println("	        <vendor>Bon-club</vendor>	");
		  out.println("	        <homepage href=\"www.bon-club.com.ua\"/>	");
		  out.println("	        <description>Test JMS </description>	");
		  out.println("	        <offline-allowed/>	");
		  out.println("	      </information>	");
		  out.println("	      <security>	");
		  out.println("	          <all-permissions/>	");
		  out.println("	      </security>	");
		  out.println("	      <resources>	");
		  out.println("	        <j2se version=\"1.6+\"/>	");
		  out.println("	        <jar href=\"JMS_WebStart.jar\"/>	");
		  out.println("	      </resources>	");
		  out.println("	      <application-desc main-class=\"example.EnterPoint\">	");
		  out.println("	      	<argument>"+request.getSession(true).getId()+"</argument> ");
		  out.println("	      </application-desc>	");
		  out.println("	    </jnlp>	");
	}

	/** получить полный путь к данному приложению, чтобы передать в качестве CodeBase */
	private String getFullPathToApplication(HttpServletRequest request){
		/*
		System.out.println("ContextPath: "+request.getContextPath());
		System.out.println("LocalAddr: "+request.getLocalAddr());
		System.out.println("LocalName: "+request.getLocalName());
		System.out.println("LocalPort: "+request.getLocalPort());
		System.out.println("PathInfo: "+request.getPathInfo());
		System.out.println("Protocol: "+request.getProtocol());
		System.out.println("QueryString: "+request.getQueryString());
		System.out.println("RequestURI: "+request.getRequestURI());
		System.out.println("RequestURL: "+request.getRequestURL());
		System.out.println("Scheme: "+request.getScheme());
		System.out.println("ServerName: "+request.getServerName());
		System.out.println("ServerPort: "+request.getServerPort());
		System.out.println("ServletPath: "+request.getServletPath());
		System.out.println("PathInfo: "+request.getPathInfo());
		
		// пример обращения к данному сервлету из Браузера 
		ContextPath: /JMS_WebStart_j2ee
		LocalAddr: 192.168.15.120
		LocalName: pcitdvip01
		LocalPort: 8080
		PathInfo: null
		Protocol: HTTP/1.1
		QueryString: null
		RequestURI: /JMS_WebStart_j2ee/client.jnlp
		RequestURL: http://192.168.15.120:8080/JMS_WebStart_j2ee/client.jnlp
		Scheme: http
		ServerName: 192.168.15.120
		ServerPort: 8080
		ServletPath: /client.jnlp
		PathInfo: null		
		*/
		String fullRequest=request.getRequestURL().toString();
		String servletPath=request.getServletPath();
		int index=fullRequest.lastIndexOf(servletPath);
		return fullRequest.substring(0,index);
	}
}
