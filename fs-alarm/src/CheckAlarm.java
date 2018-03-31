

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import directory_analisator.DirectoryAnalisator;
import directory_analisator.IFileNameConverter;
import directory_analisator.filter.core.FileRemover;

/**
 * Servlet implementation class CheckAlarm
 */
public class CheckAlarm extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private String directory;
    private DirectoryAnalisator directoryAnalisator;
	
	@Override
	public void init() throws ServletException {
		super.init();
		directory=this.getServletContext().getInitParameter("photo_dir");
		Integer removeFileCount=null;
		try{
			removeFileCount=Integer.parseInt(this.getServletContext().getInitParameter("remove_file_count"));
		}catch(Exception ex){};
		directoryAnalisator=new DirectoryAnalisator(directory, new IFileNameConverter() {
			@Override
			public String convertFileName(File file) {
				if(file!=null){
					return file.getAbsolutePath();
				}else{
					return "";
				}
			}
		}, (removeFileCount==null)?null:new FileRemover(removeFileCount));
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doAction(request,response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doAction(request,response);
	}

	private SimpleDateFormat sdf=new SimpleDateFormat("HH:mm:ss  dd.MM.yyyy");
	
	protected void doAction(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getOutputStream().println("<html>");
		response.getOutputStream().println("	<head>");
		response.getOutputStream().println("		<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\">");
		response.getOutputStream().println("		<META HTTP-EQUIV=\"refresh\" CONTENT=\"15\">");
		response.getOutputStream().println("	<title> "+sdf.format(new Date())+" </title>");
		response.getOutputStream().println("	</head>");
		response.getOutputStream().println("<body>");
		// прочесть все файлы в указанном каталоге
		directoryAnalisator.analisator();
		// вывести на HTML форму
		ServletOutputStream output=response.getOutputStream();
		ArrayList<String> list=directoryAnalisator.getFileList(0, 3,-1);
		// alarm если более трех минут  
		if(list.size()>0){
			output.println("	<EMBED SRC=\"tada.wav\" AUTOSTART=true WIDTH=0 HEIGHT=0 LOOP=0>");
		}
		outToHtml(request, output, list," [0 .. 3] min");
		outToHtml(request, output, directoryAnalisator.getFileList(3, 10,-1)," [3 .. 10] min");
		outToHtml(request, output, directoryAnalisator.getFileList(10, 30,-1)," [10 .. 30] min");
		outToHtml(request, output, directoryAnalisator.getFileList(30, 120, 30)," [30 .. 120] min");
		outToHtml(request, output, directoryAnalisator.getFileList(120, 360, 30)," [120 .. 360] min");
	}

	
	private void outToHtml(HttpServletRequest request, ServletOutputStream output, ArrayList<String> list, String title) throws IOException{
		output.println("<fieldset>");
		output.println("	<legend>");
		output.println(title);
		output.println("	</legend>");
		output.println("<table>");
		for(int counter=0;counter<list.size();counter++){
			output.println("<tr><td>  <a target=\"_blank\" href=\""+request.getContextPath()+"/DownloadImage?name="+this.getFileName(this.getFileName(list.get(counter)))+"\" >"+this.getDisplayFileName(list.get(counter))+" </a> </td></tr>");
		}
		output.println("</table>");
		output.println("</fieldset>");
	}
	
	private SimpleDateFormat sdfDecode=new SimpleDateFormat("yyyyMMddHHmmss");
	private SimpleDateFormat sdfOutput=new SimpleDateFormat("dd MM  HH:mm:ss ");
	/** получить преобразованное имя файла для отображения на форме   */
	private String getDisplayFileName(String fullFileName){
		try{
			return sdfOutput.format(sdfDecode.parse(fullFileName.substring(fullFileName.length()-18, fullFileName.length()-4)));
		}catch(Exception ex){
			return "";
		}
	}
	
	/** получить имя файла для отображения  */
	private String getFileName(String fullFileName){
		try{
			return fullFileName.substring(fullFileName.length()-21);
		}catch(Exception ex){
			return null;
		}
	}
	
 	
}
