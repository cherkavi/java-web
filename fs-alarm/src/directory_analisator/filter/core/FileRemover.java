package directory_analisator.filter.core;

import java.io.File;

import directory_analisator.filter.IFilePostAnalisatorFilter;

/** оставляет только заданное кол-во файлов */
public class FileRemover implements IFilePostAnalisatorFilter{
	private int countAlive;
	/** оставляет только заданное кол-во файлов */
	public FileRemover(int countAlive){
		this.countAlive=countAlive;
	}
	
	@Override
	public File[] filterFile(File[] files) {
		if(files.length>this.countAlive){
			File[] returnValue=new File[this.countAlive];
			int index=0;
			for(int counter=files.length;counter>=0;counter--){
				if(counter<this.countAlive){
					returnValue[index++]=files[counter];
				}else{
					try{
						files[counter].delete();
					}catch(Exception ex){
					}
				}
			}
			return returnValue;
		}else{
			return files;
		}
		
	}
	
}
