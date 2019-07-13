

import java.io.*;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class FileSender
 */
public class FileSender extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public FileSender() {

    }

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
		String file_name=request.getParameter("file");
		if((file_name!=null)&&(!file_name.equals(""))){
			File file=new File(getPathToFile(file_name));
			response.setContentType("application/csv");
			response.addHeader("Content-Disposition", "attachment; filename="+file_name);
			response.setContentLength((int)file.length());
			FileInputStream input=new FileInputStream(file);
			ServletOutputStream output=response.getOutputStream();
			
			byte[] buffer=new byte[1024]; 
			int bytes_counter=0;
			while( (bytes_counter=input.read(buffer))!= -1){
				output.write(buffer,0,bytes_counter);
			}
			input.close();
			output.close();
		}
	}
	
	private String getPathToFile(String file_name){
		return "c:\\"+file_name;
	}
}
