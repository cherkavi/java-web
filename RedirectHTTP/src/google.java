

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.net.URLConnection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class google
 */
public class google extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public google() {
        // TODO Auto-generated constructor stub
    }

    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	// послать HTTP
		String root_redirect="http://google.com.ua";
		System.out.println("QueryString:"+request.getQueryString());
		System.out.println("Context path:"+request.getContextPath());
		System.out.println("RequestURL:"+request.getRequestURL());
		System.out.println("redirect:"+root_redirect+getParameterGet("http://192.168.15.119:8080/Redirect",request));

		//response.sendRedirect(response.encodeRedirectURL("http://google.com.ua"));
		// получить поток
		redirect(response,root_redirect+getParameterGet("http://192.168.15.119:8080/Redirect",request));
    	
    	
    }

    /** получить параметр Get из запроса */
	private String getParameterGet(String source_url,HttpServletRequest request){
		String return_value="";
		String request_url=request.getRequestURL().toString();
		if(source_url.length()<request.getRequestURL().length()){
			return_value=request_url.substring(source_url.length());
		}
		return return_value; 
	}

	public void redirect(HttpServletResponse response,String path_to_server) {
		try {
			URL url=new URL(path_to_server);
			HttpURLConnection connection=(HttpURLConnection)url.openConnection();
			
			InputStream input=connection.getInputStream();
			response.setContentType(connection.getContentType());
			response.setContentLength(connection.getContentLength());
			OutputStream output=response.getOutputStream();
			byte[] buffer=new byte[1024];
			int counter=0;
			while( (counter=input.read(buffer))>0){
				output.write(buffer,0,counter);
			}
			input.close();
			output.close();
		} catch (Exception e) {
			System.out.println("Exception:"+e.getMessage());
		}
	}	
    

	
}
