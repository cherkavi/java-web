

import java.io.*;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

/**
 * Servlet implementation class GetFile
 */
public class GetFile extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private static void debug(String information){
		System.out.print("GetFile DEBUG:");
		System.out.println(information);
	}
	private static void error(String information){
		System.out.print("GetFile ERROR:");
		System.out.println(information);
	}
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetFile() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// не может быть обработан, т.к. файлы нельзя передавать в запросе GET
		//super.doGet(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		action(request,response);
	}

	@SuppressWarnings("unchecked")
	private void action(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(ServletFileUpload.isMultipartContent(request)){
			debug("is multipart");
			debug("Create a factory for disk-based file items");
			DiskFileItemFactory factory = new DiskFileItemFactory();

			debug("Set factory constraints");
			/** maximum file size */
			int max_file_size=5*1024*1024;
			/** path to temp directory*/
			String temp_directory="c:\\temp\\";
			factory.setSizeThreshold(max_file_size);
			factory.setRepository(new File(temp_directory));

			debug("Create a new file upload handler");
			ServletFileUpload upload = new ServletFileUpload(factory);

			debug("Set overall request size constraint");
			upload.setSizeMax(max_file_size);
			// another way to Configure 
			// DiskFileItemFactory factory = new DiskFileItemFactory(max_file_size, yourTempDirectory);

			debug("Parse the request");
			try {
				boolean return_value=true;
				List /* (FileItem) */ items = upload.parseRequest(request);
				Iterator iter = items.iterator();
				int counter=0;
				while (iter.hasNext()) {
					counter++;
					debug("Parameter:<"+counter+">");
				    FileItem item = (FileItem) iter.next();
				    if (item.isFormField()) {
				        /** form Parameter as String*/
				    	processFormField(item);
				    } else {
				    	/** file for upload */
				        return_value=return_value && processUploadedFile(item,temp_directory);
				    }
				    
				}		
				if(return_value==true){
					PrintWriter out=response.getWriter();
					out.println("<html>");
					out.println("<head>");
					out.println("<title>");
					out.println("OK");
					out.println("</title>");
					out.println("<body>");
					out.println("<H1>File saved </H1>");
					out.println("</body>");
					out.println("</head>");
					out.println("</html>");
					out.close();
				}else{
					PrintWriter out=response.getWriter();
					out.println("<html>");
					out.println("<head>");
					out.println("<title>");
					out.println("Error");
					out.println("</title>");
					out.println("<body>");
					out.println("<H1>File saved </H1>");
					out.println("</body>");
					out.println("</head>");
					out.println("</html>");
					out.close();
					
				}
			} catch (FileUploadException e) {
				error("upload.parseRequest");
			}			
		}else{
			debug("this is not multipart");
		}
	}
	/** 
	 * process Field from Form
	 * (if in form is String parameter)
	 */
	private void processFormField(FileItem value){
		debug("this is String parameter:");
		debug("Field Name:"+value.getFieldName());
		debug("Field Value:"+value.getString());
	}
	/** 
	 * process File from Form
	 */
	private boolean processUploadedFile(FileItem value,String directory){
		boolean return_value=true;
		debug("this is File parameter");
		debug("Field Name:"+value.getFieldName());
	    debug("FileName:"+value.getName());
	    debug("ContentType:"+value.getContentType());
	    debug("IsInMemory:"+value.isInMemory());
	    debug("Size:"+value.getSize());
	    // get binary data
	    //value.get()
	    
	    // write data into Disk
	    try{
	    	debug("try save file to Disk");
	    	value.write(new File(directory+value.getName()+".tmp"));
	    	debug("file saved into disk");
	    	return_value=true;
	    }catch(Exception ex){
	    	return_value=false;
	    	error("File Save Error:"+ex.getMessage());
	    }
	    return return_value;
	}
}
