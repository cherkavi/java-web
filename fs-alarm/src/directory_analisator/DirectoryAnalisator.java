package directory_analisator;

import java.io.File;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Comparator;

import directory_analisator.filter.IFilePostAnalisatorFilter;

/** анализатор каталогов  */
public class DirectoryAnalisator {
	private String directory;
	private IFileNameConverter fileNameConverter;
	private ArrayList<IFilePostAnalisatorFilter> postFilters=new ArrayList<IFilePostAnalisatorFilter>();
	/** анализатор каталогов 
	 * @param pathToDirectory - полный путь к каталогу
	 * @param fileNameConverter - преобразователь файлов в текстовое представление  
	 *  */
	public DirectoryAnalisator(String pathToDirectory, IFileNameConverter fileNameConverter, IFilePostAnalisatorFilter ... filters){
		directory=this.prepareDirectoryName(pathToDirectory);
		this.fileNameConverter=fileNameConverter;
		if((filters!=null)&&(filters.length>0)){
			for(int counter=0;counter<filters.length;counter++){
				if(filters[counter]!=null){
					this.postFilters.add(filters[counter]);
				}
			}
		}
	}
	
	/** просмотреть имя каталога и вернуть его с разделителем в конце */
	private String prepareDirectoryName(String path){
		String fileSeparator=System.getProperty("file.separator");
		path=path.trim();
		if(path.endsWith(fileSeparator)){
			return path+fileSeparator;
		}else{
			return path;
		}
	}
	
	/** полный список файлов  */
	private File[] listOfFiles=null;
	
	/** проанализировать каталог по-умолчанию ( заданный в конструкторе ) 
	 * @return 
	 * <ul>
	 * 	<li><b>true</b> - прошло успешно </li>
	 * 	<li><b>false</b> - ошибка анализа </li>
	 * </ul>
	 * */
	public boolean analisator(){
		return this.analisator(this.directory);
	}
	
	/** проанализировать каталог 
	 * @param path
	 * */
	public boolean analisator(String path){
		boolean returnValue=false;
		this.listOfFiles=null;
		try{
			// получить файлы
			listOfFiles=(new File(path)).listFiles();
			// упорядочить файлы
			Arrays.sort(listOfFiles, new Comparator<File>(){
				@Override
				public int compare(File arg0, File arg1) {
					if((arg0!=null)&&(arg1!=null)){
						if(arg0.lastModified()==arg1.lastModified())return 0;
						if(arg0.lastModified()<arg1.lastModified()){
							return 1;
						}else{
							return -1;
						}
					}else{
						return -1;
					}
				}
			});
			returnValue=true;
		}catch(Exception ex){
			System.err.println("DirectoryAnalisator#analisator Exception:"+ex.getMessage());
		}
		if(this.listOfFiles!=null){
			for(int counter=0;counter<this.postFilters.size();counter++){
				this.listOfFiles=this.postFilters.get(counter).filterFile(this.listOfFiles);
			}
		}
		return returnValue;
	}
	
	// private SimpleDateFormat sdf=new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
	
	/**
	 * предварительно должен быть вызван метод {@link DirectoryAnalisator#analisator}  
	 * @param minutesLow
	 * @param minutesHigh
	 * @return список каталогов, который удовлетворяет указанным условиям
	 */
	public ArrayList<String> getFileList(int minutesLow, int minutesHigh, int limit){
		Calendar currentTime=Calendar.getInstance();
		currentTime.add(Calendar.MINUTE, (-1)*minutesLow);
		long lowLimit=currentTime.getTimeInMillis();
		currentTime.add(Calendar.MINUTE, minutesLow);
		currentTime.add(Calendar.MINUTE, (-1)*minutesHigh);
		long highLimit=currentTime.getTimeInMillis();
		
		// System.out.println("Low limit:"+sdf.format(new Date(lowLimit)));
		// System.out.println("High limit:"+sdf.format(new Date(highLimit)));

		ArrayList<String> returnValue=new ArrayList<String>();
		try{
			for(int counter=0;counter<this.listOfFiles.length;counter++){
				long currentValue=this.listOfFiles[counter].lastModified();
				// System.out.println("current value="+sdf.format(new Date(currentValue)) );
				if( (highLimit<=currentValue) && (currentValue<=lowLimit) ){
					if(limit>=0){
						if(limit<=returnValue.size())break;
					}
					returnValue.add(this.fileNameConverter.convertFileName(this.listOfFiles[counter]));
				}
			}
		}catch(Exception ex){
			System.out.println("DirectoryAnalisator#getFileList: "+ex.getMessage());
		}
		return returnValue;
	}
	
	public static void main(String[] args){
		System.out.println("begin");
		DirectoryAnalisator analisator=new DirectoryAnalisator("d:\\temp\\", new NameConverter());
		analisator.analisator();
		ArrayList<String> returnValue=analisator.getFileList(0, 50000, 2);
		for(int counter=0;counter<returnValue.size();counter++){
			System.out.println(counter+" : "+returnValue.get(counter));
		}
		System.out.println("-end-");
	}
}

class NameConverter implements IFileNameConverter{

	@Override
	public String convertFileName(File file) {
		if(file!=null){
			return file.getAbsolutePath();
		}else{
			return "";
		}
		
	}
	
}
