package com.cherkashin.vitaliy.computer_shop.server;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

public class CommodityAssortmentUpload extends HttpServlet{
	private final static long serialVersionUID=1L;
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ServletFileUpload upload = new ServletFileUpload();
		FileItemStream fileId=null;
		FileItemStream fileBody=null;
        try{
            FileItemIterator iter = upload.getItemIterator(request);
            while (iter.hasNext()) {
                FileItemStream item = iter.next();
                String fieldName = item.getFieldName();
                if((fieldName!=null)&&(fieldName.equals("xlsFileUpload"))){
                	System.out.println("Finded element by name ( UploadFile )"); // fileUpload.setName("xlsFileUpload");
                	fileId=item;
                }
                if((fieldName!=null)&&(fieldName.equals("file_id"))){
                	System.out.println("Finded Element Kod"); // fileUpload.setName("xlsFileUpload");
                	fileId=item;
                }
            }
            if((fileId!=null)&&(fileBody!=null)){
            	System.out.println(" file is found:"+ this.getValueFromStream(fileId));
                InputStream stream = fileBody.openStream();
                // создать файл для выгрузки данных 
                FileOutputStream out=new FileOutputStream(this.getFileName());
                // скопировать из источника в приемник 
                this.copyFromSourceToDestination(stream, out);
                out.close();
            }else{
            	System.err.println(" file id and/or file body does not found ");
            }
        }
        catch(Exception e){
        	System.err.println("CommodityAssortmentUpload#doPost Exception: "+e.getMessage());
            e.printStackTrace();        
        }		
	}
	
	private String getValueFromStream(FileItemStream stream){
		String returnValue=null;
		try{
			BufferedReader reader=new BufferedReader(new InputStreamReader(stream.openStream()));
			returnValue=reader.readLine();
		}catch(Exception ex){
			System.err.println("#getValueFromStream "+ex.getMessage());
		}
		return returnValue;
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
	
	/** получить полный путь к файлу для сохранения полученного вложения */
	private String getFileName(){
		return "c:\\GWT_readed_file.bin";
	}
}
