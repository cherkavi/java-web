

import java.io.*;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class GetFile
 */
public class GetFile extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static void debug(String information){
		System.out.print("GetFile: ");
		System.out.print("DEBUG: ");
		System.out.println(information);
	}
	private static void error(String information){
		System.out.print("GetFile: ");
		System.out.print("ERROR: ");
		System.out.println(information);
	}
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetFile() {
        super();
    }

    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	debug("--------------------SERVICE------------------");
    	String key;
    	String value;
    	debug("Attributes:");
    	Enumeration attributes=request.getAttributeNames();
    	while(attributes.hasMoreElements()){
    		try{
        		key=(String)attributes.nextElement();
        		value=(String)request.getAttribute(key);
        		debug("Attributes: key: "+key+"       value:"+value);
    		}catch(Exception ex){};
    	};
    	
    	debug("charEncoding:"+request.getCharacterEncoding());
    	debug("content Length:"+request.getContentLength());
    	debug("Content type:"+request.getContentType());
    	debug("Context Path:"+request.getContextPath());
    	
    	debug("Headers:");
    	Enumeration headers=request.getHeaderNames();
    	while(headers.hasMoreElements()){
    		try{
        		key=(String)headers.nextElement();
        		value=(String)request.getHeader(key);
        		debug("Headers: key: "+key+"       value:"+value);
    		}catch(Exception ex){};
    	};
    	debug("LocalAddr:"+request.getLocalAddr());
    	debug("LocalName:"+request.getLocalName());
    	debug("LocalPort:"+request.getLocalPort());
    	debug("Method:"+request.getMethod());
    	debug("PathInfo:"+request.getPathInfo());
    	debug("PathTranslated:"+request.getPathTranslated());
    	debug("Protocol:"+request.getProtocol());
    	debug("QueryString:"+request.getQueryString());
    	debug("RemoteAddr:"+request.getRemoteAddr());
    	debug("RemoteHost:"+request.getRemoteHost());
    	debug("RemotePort:"+request.getRemotePort());
    	debug("RemoteUser:"+request.getRemoteUser());
    	debug("SessionId:"+request.getRequestedSessionId());
    	try{
    		debug("RequestURL:"+request.getRequestURL().toString());
    	}catch(NullPointerException ex){};
    	
    	debug("RequestURI:"+request.getRequestURI());
    	debug("Scheme:"+request.getScheme());
    	debug("ServerName:"+request.getServerName());
    	debug("ServerPort:"+request.getServerPort());
    	debug("ServletPath:"+request.getServletPath());
    	
    	try{
    		debug("UserPrincipal:"+request.getUserPrincipal().toString());
    	}catch(NullPointerException ex){};
    	
/*
    	debug("Parameters:");
    	Enumeration parameters=request.getParameterNames();
    	while(parameters.hasMoreElements()){
    		try{
        		key=(String)parameters.nextElement();
        		value=(String)request.getParameter(key);
        		debug("Parameters: key: "+key+"       value:"+value);
    		}catch(Exception ex){};
    	};
*/
    	debug("Parameter from QueryString:");
    	Map<String,String> parameters=getParameterFromQueryString(request.getQueryString());
    	Iterator<String> iterator=parameters.keySet().iterator();
    	while(iterator.hasNext()){
    		key=iterator.next();
    		value=parameters.get(key);
    		debug("Parameters: key: "+key+"       value:"+value);
    	}
    	doAction(request, response);
    }
    
    /** get parameter.name & parameter.value from QueryString GET */
    private Map<String,String> getParameterFromQueryString(String query){
    	HashMap<String,String> return_value=new HashMap<String,String>();
    	try{
        	/** all parameter.name=parameter.value*/
        	String[] key_and_value=query.split("&");
        	/** parameter.name=[0]  and parameter.value[1]*/
        	String[] currentKeyValue;
        	for(int counter=0;counter<key_and_value.length;counter++){
        		try{
            		currentKeyValue=key_and_value[counter].split("=");
            		return_value.put(URLDecoder.decode(currentKeyValue[0],"UTF-8"), URLDecoder.decode(currentKeyValue[1],"UTF-8"));
        		}catch(Exception ex){};
        	}
    	}catch(NullPointerException ex){};
    	return return_value;
    }
    
/*    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	debug("GET: ");
		Enumeration names=request.getParameterNames();
		String key;
		String value;
		while(names.hasMoreElements()){
			key=(String)names.nextElement();
			value=request.getParameter(key);
			debug("Key: "+key+"        Value:"+value);
		}
    	doAction(request, response);
    }
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	debug("POST: ");
		Enumeration names=request.getParameterNames();
		String key;
		String value;
		while(names.hasMoreElements()){
			key=(String)names.nextElement();
			value=request.getParameter(key);
			debug("Key: "+key+"        Value:"+value);
		}
    	doAction(request, response);
    }
*/
	protected void doAction(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		debug("save File to storage");
		InputStream_to_file(request.getInputStream(),new File("c:\\temp\\get_file.bin"));
		request.getInputStream().close();
		debug("file is saved");
	}
	private void InputStream_to_file(InputStream is, File file) throws IOException{
		byte[] buffer=new byte[1024];
		FileOutputStream fos=new FileOutputStream(file);
		int read_bytes=0;
		while((read_bytes=is.read(buffer))>=0){
			fos.write(buffer,0,read_bytes);
		}
		fos.close();
	}
}
