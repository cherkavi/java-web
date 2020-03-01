package wicket_utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;

import org.apache.wicket.model.Model;
import org.apache.wicket.util.resource.IResourceStream;
import org.apache.wicket.util.resource.ResourceStreamNotFoundException;
import org.apache.wicket.util.time.Time;

/** класс для DownloadLink - загрузка файла  */
public class FileResourceStream implements IResourceStream{
	private final static long serialVersionUID=1L;
	private Model<String> modelPathToFile;
	private String applicationType;
	private Locale locale;
	
	/** класс для DownloadLink - загрузка файла  
	 * @param modelPathToFile - модель, которая содержит полный путь к файлу 
	 * @param applicationType - тип приложение ("application/jpeg")
	 * @param locale - локаль
	 */
	public FileResourceStream(Model<String> modelPathToFile, String applicationType, Locale locale){
		this.modelPathToFile=modelPathToFile;
		this.applicationType=applicationType;
		this.locale=locale;
	}

	private FileInputStream fis=null;
	
	@Override
	public void close() throws IOException {
		try{
			this.fis.close();
		}catch(Exception ex){
			System.err.println("FileResourceStream#close Exception:"+ex.getMessage());
		}finally{
			fis=null;
		}
	}

	@Override
	public String getContentType() {
		return this.applicationType; // "application/jpeg";
	}

	@Override
	public InputStream getInputStream()
			throws ResourceStreamNotFoundException {
		try{
			this.fis=new FileInputStream(new File(this.modelPathToFile.getObject()));
			return this.fis; 
		}catch(Exception ex){
			return null;
		}
	}

	@Override
	public Locale getLocale() {
		return this.locale;
	}

	@Override
	public long length() {
		return (new File(this.modelPathToFile.getObject())).length();
	}

	@Override
	public void setLocale(Locale arg0) {
	}

	@Override
	public Time lastModifiedTime() {
		return null;
	}

}
