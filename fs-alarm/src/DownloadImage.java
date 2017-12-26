

import java.io.File;

import java.io.FileInputStream;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class DownloadImage
 */
public class DownloadImage extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private String directory;
	
	@Override
	public void init() throws ServletException {
		super.init();
		directory=this.getServletContext().getInitParameter("photo_dir");
	}
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doAction(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doAction(request, response);
	}

	protected void doAction(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String fileName=request.getParameter("name");
		if(fileName!=null){
			File file=new File(this.directory+fileName);
			if(file.exists()){
				// response.setContentType("application/csv");
				// response.addHeader("Content-Disposition", "attachment; filename="+file_name);
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
			}else{
				return;
			}
		}else{
			return;
		}
	}
	
}
