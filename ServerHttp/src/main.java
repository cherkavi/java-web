

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import transport.Transport;

/**
 * Servlet implementation class main
 */
public class main extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	private void debug(Object information){
		System.out.print("main");
		System.out.print(" DEBUG ");
		System.out.println(information);
	}
    
    public void service(HttpServletRequest request, HttpServletResponse response)throws ServletException,IOException{
    	try{
    		debug("get InputStream");
        	InputStream is=request.getInputStream();
        	debug("get ObjectInputStream");
    		ObjectInputStream ois=new ObjectInputStream(is);
    		debug("get Transport");
    		Transport transport=(Transport)ois.readObject();
    		writeObjectToOutputStream(getResponse(transport),response.getOutputStream());
    	}catch(Exception ex){
    		System.out.println("main.service Exception:"+ex.getMessage());
    	}
    }
    
    /** обработать входящее сообщение Transport и передача его в качестве ответа*/
    private Transport getResponse(Transport request){
    	debug("Transport: "+request);
    	return new Transport("Servlet main");
    }
    
    /** записать/сериализовать объект, в OutputStream */
    private void writeObjectToOutputStream(Object object, OutputStream stream) throws IOException{
    	ObjectOutputStream oos=new ObjectOutputStream(stream);
    	oos.writeObject(object);
    }
}
