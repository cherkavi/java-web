

import java.io.*;
import java.util.List;
import javax.servlet.*;
import javax.servlet.http.*;
import org.apache.commons.fileupload.*;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

/**
 * Servlet implementation class uploadFileServlet
 */
public class uploadFileServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public uploadFileServlet() {
        // TODO Auto-generated constructor stub
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

	protected void doAction(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(ServletFileUpload.isMultipartContent(request)){
			System.out.println("it is file upload");
			// Create a factory for disk-based file items
			FileItemFactory factory = new DiskFileItemFactory();
			// Create a new file upload handler
			ServletFileUpload upload = new ServletFileUpload(factory);
			try{
				List<FileItem> fileList=upload.parseRequest(request);
				for(FileItem file:fileList){
					System.out.println(file.getName());
					System.out.println(file);
					file.write(new File("c:\\output_servlet.file"));
				}
			}catch(Exception ex){
				System.err.println("load Error ");
			}
			PrintWriter writer=new PrintWriter(response.getOutputStream());
			writer.write("Ok");
			writer.close();
		}else{
			PrintWriter writer=new PrintWriter(response.getOutputStream());
			writer.println("<html>");
			writer.println("<body>");
			writer.println("		<form method=\"post\" enctype=\"multipart/form-data\" action=\"http://localhost:8080/JspLoadFile/uploadFileServlet\"> <input type=\"file\" name=\"file_name\" > <input type=\"submit\" value=\"send file\" /> </form>");
			writer.println("</body>");
			writer.println("</html>");
			writer.close();
		}
	}
}
