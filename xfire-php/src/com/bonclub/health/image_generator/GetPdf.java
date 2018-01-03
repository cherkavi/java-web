package com.bonclub.health.image_generator;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class GetPdf
 */
public class GetPdf extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doAction(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doAction(request,response);
	}

	protected void doAction(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String uniqueId=request.getParameter("uniqueId");
		if(uniqueId!=null){
			// сгенерировать файл и выдать его клиенту
			String returnFileName=this.generatedFile(uniqueId);
			String outputFileName=this.getFileNameForClient(returnFileName);
			returnFileToClient(response, returnFileName, outputFileName);
		}else{
			// TODO 
			System.err.println("Ошбика получения данных");
		}
	}
	
	/** сгенерировать имя файла, которое будет отображаться клиенту на основании возвращаемого файла */
	private String getFileNameForClient(String returnFileName) {
		try{
			File file=new File(returnFileName);
			return file.getName();
		}catch(Exception ex){
			return "report";
		}
	}

	private final static int BUFSIZE=1024;
	
	/** вылить файл пользователю в качестве ответа */
	private void returnFileToClient(HttpServletResponse response, String generatedFile, String outputFileName) throws IOException{
		File f = new File(generatedFile);
        int length   = 0;
        ServletOutputStream op = response.getOutputStream();
        String mimetype = getServletConfig().getServletContext().getMimeType(generatedFile);
        //
        //  Set the response and go!
        //
        //
        response.setContentType( (mimetype != null) ? mimetype : "application/octet-stream" );
        response.setContentLength( (int)f.length() );
        response.setHeader( "Content-Disposition", "attachment; filename=\"" + outputFileName + "\"" );
        //
        //  Stream to the requester.
        //
        byte[] bbuf = new byte[BUFSIZE];
        DataInputStream in = new DataInputStream(new FileInputStream(f));
        while ((in != null) && ((length = in.read(bbuf)) != -1))
        {
            op.write(bbuf,0,length);
        }
        in.close();
        op.flush();
        op.close();		
	}

	private String generatedFile(String id){
		// TODO 
		return "D:\\headphones.txt ";
	}
}
