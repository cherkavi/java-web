package com.cherkashin.vitaliy.computer_shop.server;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.BasicConfigurator;

public class CommodityAssortmentUpload extends HttpServlet{
	private final static long serialVersionUID=1L;
	/** полный путь к каталогу для временного хранения загруженных файлов  */
	private String pathToTempDirectory=null;

	private final String paramFileId="file_id";
	private final String paramFileBody="file_body";
	
	@Override
	public void init() throws ServletException {
		super.init();
		// чтение каталога для сохранения данных
		pathToTempDirectory=getServletContext().getInitParameter("temp_directory");
		if(pathToTempDirectory==null)pathToTempDirectory=System.getProperty("java.io.tmpdir");
		pathToTempDirectory=pathToTempDirectory.trim();
		String fileSeparator=System.getProperty("file.separator");
		if(!this.pathToTempDirectory.endsWith(fileSeparator)){
			pathToTempDirectory=pathToTempDirectory+fileSeparator;
		}
		
		BasicConfigurator.configure();
	}
	
	/** получить уникальный идентификатор сохраняемого файла */
	private String getFileId(List<FileItem> fileItemsList, HttpServletRequest request){
		String returnValue=null;
		for(int counter=0;counter<fileItemsList.size();counter++){
			FileItem currentItem=fileItemsList.get(counter);
			if(currentItem.getFieldName().equals(this.paramFileId)){
				// returnValue=request.getParameter(this.paramFileId);
				//DiskFileItem item=(DiskFileItem)currentItem;
				returnValue=getStringFromFileItem(currentItem);
				break;
			}
		}
		return returnValue;
	}
	
	/** получить строку на основании FileItem */
	private String getStringFromFileItem(FileItem item){
		StringBuffer returnValue=new StringBuffer();
		try{
			BufferedReader reader=new BufferedReader(new InputStreamReader(item.getInputStream()));
			returnValue.append(reader.readLine());
		}catch(Exception ex){
			System.err.println("getStringFromFileItem:"+ex.getMessage());
		}
		return returnValue.toString();
	}
	
	/** получить уникальный идентификатор  */
	private FileItem getFileItemBody(List<FileItem> list){
		FileItem returnValue=null;
		for(int counter=0;counter<list.size();counter++){
			FileItem currentItem=list.get(counter);
			if(currentItem.getFieldName().equals(this.paramFileBody)){
				returnValue=currentItem;
				break;
			}
		}
		return returnValue;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("CommodityAssortmentUpload: ");
		ServletFileUpload servletFileUpload = new ServletFileUpload(new DiskFileItemFactory());
		try{
			List<FileItem> fileItemsList = servletFileUpload.parseRequest(request);
			/** имя параметра, который идентифицирует файл */
			String fileName=getFileId(fileItemsList, request);
			/** имя параметра, который содержит тело */
			FileItem fileBody=getFileItemBody(fileItemsList);
			if((fileName!=null)&&(fileBody!=null)){
				// попытка сохранить файл от клиента
				FileOutputStream fos=new FileOutputStream(this.pathToTempDirectory+fileName);
				this.copyFromSourceToDestination(fileBody.getInputStream(), fos);
				System.out.println("Copy OK");
			}else{
				System.err.println("CommodityAssortmentUpload#doPost: Exception: FileId and FileBody does not found ");
			}
		}catch(Exception ex){
			System.err.println("doPost Exception:"+ex.getMessage());
		}
	}
	

	/** копирование данных из источника в приемник */
	private void copyFromSourceToDestination(InputStream input, OutputStream output) throws IOException{
        int len=(-1);
        byte[] buffer = new byte[8192];
        while ((len = input.read(buffer, 0, buffer.length)) != -1) {
            output.write(buffer, 0, len);
            output.flush();
        }
	}
}
