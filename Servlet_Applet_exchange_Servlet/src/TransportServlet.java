

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.*;
/**
 * Servlet implementation class transport_servlet
 */
public class TransportServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private static Logger field_logger;
    static{
    	field_logger=Logger.getLogger("TransportSevlet");
    	BasicConfigurator.configure();
    }
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TransportServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
/*	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doAction(request, response);
	}
*/
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
/*	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doAction(request, response);
	}
*/
    protected void service(HttpServletRequest request, HttpServletResponse response)throws ServletException,IOException{
    	field_logger.debug("connect client");
    	doAction(request,response);
    }
	
	private void doAction(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		field_logger.debug("doAction:begin");
		try{
			ServletInputStream input_stream=request.getInputStream();
			ObjectInputStream object_input_stream=new ObjectInputStream(input_stream);
			field_logger.debug("попытка получения данных от клиента ");
			Transport client_request=(Transport)object_input_stream.readObject();
			field_logger.debug("объект получен>>>:"+client_request+"      Command:"+client_request.getCommand());
			input_stream.close();
			ServletOutputStream output_stream=response.getOutputStream();
			ObjectOutputStream object_output=new ObjectOutputStream(output_stream);
			object_output.writeObject(new Transport("server_answer"));
			object_output.close();
			output_stream.close();
		}catch(IOException ex){
			field_logger.error("IOException: "+ex.getMessage());
		}catch(ClassNotFoundException ex){
			field_logger.error("ClassNotFoundException:"+ex.getMessage());
		}catch(Exception ex){
			field_logger.error("Exception:"+ex.getMessage());
		}
		/*
		PrintWriter out=response.getWriter();
		out.println("<html>");
		out.println("<body>");
		out.println("<h1> hello from servlet </h1>");
		out.println("</body>");
		out.println("</html>");
		*/
		field_logger.debug("doAction:end");
	}
}
