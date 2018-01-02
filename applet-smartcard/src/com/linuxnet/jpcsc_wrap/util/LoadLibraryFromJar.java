package com.linuxnet.jpcsc_wrap.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;

import com.linuxnet.jpcsc.PCSC;

/**
 * 
 * Code updated by Danny De Cock, originally created by Nick Liebmann. The
 * original code can be downloaded from
 * http://javadesktop.org/forums/thread.jspa?threadID=3243&tstart=0
 *  
 */

public class LoadLibraryFromJar {
	private static final String windowsLibPrefix = "";

	private final static String windowsLibSuffix = ".dll";

	private static final String linuxLibPrefix = "lib";

	private static final String linuxLibSuffix = ".so";

	private static final String sunLibPrefix = "lib";

	private static final String sunLibSuffix = ".so";

	public static String libraryName = null;
	public static String libraryPath=null;

	public final static String hexChars[] = { "0", "1", "2", "3", "4", "5",
		"6", "7", "8", "9", "A", "B", "C", "D", "E", "F" };
	public static String getUniqueChar(int count){
        StringBuffer return_value=new StringBuffer();
        Random random=new java.util.Random();
        int temp_value;
        for(int counter=0;counter<count;counter++){
            temp_value=random.nextInt(hexChars.length);
            return_value.append(hexChars[temp_value]);
        }
        return return_value.toString();
    }
	/** получение имени для библиотеки, которую нужно подгрузить */
	private static String getLocalLibraryName(String libName, String prefix,
			String suffix) {
		String osname = System.getProperty("os.name");
		if (osname.indexOf("Windows") >= 0) {
			libraryName = prefix + libName;
			return (windowsLibPrefix + libraryName + windowsLibSuffix + suffix);
		} else if (osname.indexOf("Linux") >= 0) {
			libraryName = prefix + libName;
			return (linuxLibPrefix + libraryName + linuxLibSuffix + suffix);
		} else if (osname.indexOf("SunOS") >= 0) {
			libraryName = prefix + libName;
			return (sunLibPrefix + libraryName + sunLibSuffix + suffix);
		}
		return (null);
	}
	
	/** создание уникального имени библиотеки, для сохранения на диске, на случай коллизий браузеров, сессий, сеансов*/
	private static String getLocalLibraryNameUnique(String libName, String prefix,
			String suffix) {
		String osname = System.getProperty("os.name");
		if (osname.indexOf("Windows") >= 0) {
			libraryName = getUniqueChar(5)+prefix + libName;
			return (windowsLibPrefix + libraryName + windowsLibSuffix + suffix);
		} else if (osname.indexOf("Linux") >= 0) {
			libraryName = prefix + libName;
			return (linuxLibPrefix + libraryName + linuxLibSuffix + suffix);
		} else if (osname.indexOf("SunOS") >= 0) {
			libraryName = prefix + libName;
			return (sunLibPrefix + libraryName + sunLibSuffix + suffix);
		}
		return (null);
	}
	
/*
	private static File copyFileFromJARToLocalFileSystem(URL urlToCopy,
			File fDestDir) {
		String strFileName = urlToCopy.getFile();
		int nLastSlashPos = strFileName.lastIndexOf('/');
		if (nLastSlashPos != -1)
			strFileName = strFileName.substring(nLastSlashPos);

		File fDestFile = new File(fDestDir, strFileName);
		System.err.println("Creating local copy of shared library: "
				+ fDestFile.getAbsolutePath());
		try {
			fDestFile.createNewFile();
			java.io.InputStream iStream = urlToCopy.openStream();
			java.io.FileOutputStream fos = new java.io.FileOutputStream(
					fDestFile);
			byte szBuffer[] = new byte[1024];

			int nRead = 0;
			while ((nRead = iStream.read(szBuffer, 0, 1024)) > 0)
				fos.write(szBuffer, 0, nRead);

			fos.close();
			iStream.close();
		} catch (IOException ex) {
			ex.printStackTrace();
			return (null);
		}
		return (fDestFile);
	}

	public static void initLocalLibrary(String libName, String prefix) {
		initLocalLibrary(libName, prefix, "", "" + System.currentTimeMillis());
	}

	public static void initLocalLibrary(String libName, String prefix,
			String pathPrefix) {
		initLocalLibrary(libName, prefix, "", pathPrefix);
	}

	public static void initLocalLibrary(String libName, String prefix,
			String suffix, String pathPrefix) {
		try {
			throw new Exception();
		} catch (Exception e) {
			System.err
					.println("Dummy exception thrown to know which applet loads the native library <"
							+ prefix + libName + "*" + suffix + ">");
			e.printStackTrace();
		}
		String strLibraryPath = System.getProperty("java.library.path");
		{
			File fTempDir = null;
			try {
				fTempDir = File.createTempFile("prefix", "suffix");
				if (fTempDir != null)
					fTempDir = fTempDir.getParentFile();

				fTempDir = new File(fTempDir.getAbsolutePath() + File.separator
						+ "UnsatisfiedLinkWorkAround" + File.separator
						+ pathPrefix);
				fTempDir.mkdirs();
				fTempDir.deleteOnExit();
			} catch (IOException ex) {
			}
			if (fTempDir != null) {
				URL urlSharedLibrary = LoadLibraryFromJar.class
						.getClassLoader().getResource(
								getLocalLibraryName(libName, prefix, suffix));
				File fSharedLibrary = copyFileFromJARToLocalFileSystem(
						urlSharedLibrary, fTempDir);
				fSharedLibrary.deleteOnExit();
				strLibraryPath += File.pathSeparator + "." + File.pathSeparator
						+ fTempDir.getAbsolutePath();
				System.setProperty("java.library.path", strLibraryPath);
				try {
					//DO THE DIRTYNESS on sys_paths
					Field fieldSysPath = ClassLoader.class
							.getDeclaredField("sys_paths");
					fieldSysPath.setAccessible(true);
					if (fieldSysPath != null)
						fieldSysPath.set(System.class.getClassLoader(), null);
				} catch (SecurityException ex) {
					ex.printStackTrace();
				} catch (NoSuchFieldException ex) {
					ex.printStackTrace();
				} catch (IllegalAccessException ex) {
					ex.printStackTrace();
				} catch (IllegalArgumentException ex) {
					ex.printStackTrace();
				}
			}

		}
	}
	
*/	
	// -----------------------------------------
	/** загрузить библиотеку из указанного удаленного пути в локальный репозиторий, доступный пользователю
	 * и установить имя для объекта PCSC
	 * @param full_url
	 */
	public static boolean loadRemoteDriver(String full_url){
		boolean return_value=false;
		try{
			if(full_url.equals("")){
				String destination_path=System.getProperty("java.io.tmpdir")+getLocalLibraryNameUnique(PCSC.pcscNativeLibName, "", "");
				System.out.println("Local load");
				copyLocalToLocal("C:\\card_driver\\jpcsc.dll", destination_path);
				LoadLibraryFromJar.libraryPath=destination_path;
			}else{
				String destination_path=System.getProperty("java.io.tmpdir")+getLocalLibraryNameUnique(PCSC.pcscNativeLibName, "", "");
				System.out.println("Get remote library:"+full_url+getLocalLibraryName(PCSC.pcscNativeLibName,"",""));
				System.out.println("Put remote library:"+destination_path);
				copyHttpRemoteToLocal(full_url+getLocalLibraryName(PCSC.pcscNativeLibName,"",""),destination_path);
				LoadLibraryFromJar.libraryPath=destination_path;
			}
			return_value=true;
		}catch(Exception ex){
			System.out.println("loadRemoteDriver Exception:"+ex.getMessage());
			return_value=false;
		}
		
		return return_value;
	}
	

	/** копирование удаленного содержимого через HTTP протокол */
	public static boolean copyHttpRemoteToLocal(String url_path,String local_path){
		boolean return_value=false;
        try{
            URL url=new URL(url_path.replaceAll(" ","%20"));
            HttpURLConnection connection=(HttpURLConnection)url.openConnection();
            connection.setDoInput(true);
            connection.setDoOutput(false);
            //connection.setRequestMethod("GET");
            //connection.setRequestProperty("Accept","text//xml,application//xml,application//xhtml+xml,text//html;q=0.9,text//plain;q=0.8,image//png,*//*;q=0.5");
            //connection.setRequestProperty("User-Agent"," Mozilla//5.0 (Windows; U; Windows NT 5.1; uk; rv:1.8.1.13) Gecko//20080311 Firefox//2.0.0.13");

            // создание переменных запроса
            /*StringBuffer data=new StringBuffer();
            data.append(URLEncoder.encode(key,"UTF-8")+"="+URLEncoder.encode(value,"UTF-8"));
            OutputStreamWriter output=new OutputStreamWriter(connection.getOutputStream());
            System.out.println("URL:"+url_path+"<"+data.toString()+">");
            output.flush();*/
            connection.connect();
            //System.out.println("ResponseMessage:"+connection.getResponseMessage());
            //System.out.println("ResponseCode:"+connection.getResponseCode());
            InputStream input_stream=connection.getInputStream();
            FileOutputStream file_output_stream=new FileOutputStream(local_path);
            byte[] buffer=new byte[1024];
            int readed=0;
            while ((readed=input_stream.read(buffer))>=0){
                file_output_stream.write(buffer,0,readed);
            }
            input_stream.close();
            file_output_stream.flush();
            file_output_stream.close();
            return_value=true;
        }catch(Exception ex){
            System.out.println("error in read data from resource:"+ex.getMessage());
            return_value=false;
        }
        return return_value;
	}
	/** копирование локального содержимого*/
	public static boolean copyLocalToLocal(String source_path, String destination_path){
		boolean return_value=false;
		try{
		      File source = new File(source_path);
		      File destination = new File(destination_path);
		      InputStream in = new FileInputStream(source);
		      
		      OutputStream out = new FileOutputStream(destination);

		      byte[] buf = new byte[1024];
		      int len;
		      while ((len = in.read(buf)) > 0){
		        out.write(buf, 0, len);
		      }
		      in.close();
		      out.close();
		      return_value=true;
		}catch(FileNotFoundException ex){
		      System.out.println(ex.getMessage() + " in the specified directory.");
		      System.exit(0);
		}catch(IOException e){
		      System.out.println(e.getMessage());      
		}        
		return return_value;
	}
	
	
	/** копирование файла из глобального пути (URL) в локальный файл */
	private static File copyFileFromJARToLocalFileSystem(URL urlToCopy,
			File fDestDir) {
		String strFileName = urlToCopy.getFile();
		int nLastSlashPos = strFileName.lastIndexOf('/');
		if (nLastSlashPos != -1)
			strFileName = strFileName.substring(nLastSlashPos);

		File fDestFile = new File(fDestDir, strFileName);
		System.err.println("Creating local copy of shared library: "
				+ fDestFile.getAbsolutePath());
		try {
			fDestFile.createNewFile();
			java.io.InputStream iStream = urlToCopy.openStream();
			java.io.FileOutputStream fos = new java.io.FileOutputStream(
					fDestFile);
			byte szBuffer[] = new byte[1024];

			int nRead = 0;
			while ((nRead = iStream.read(szBuffer, 0, 1024)) > 0)
				fos.write(szBuffer, 0, nRead);

			fos.close();
			iStream.close();
		} catch (IOException ex) {
			ex.printStackTrace();
			return (null);
		}
		return (fDestFile);
	}

	/** инициализация локальной библиотеки */
	public static void initLocalLibrary(String libName, String prefix) {
		initLocalLibrary(libName, prefix, "", "" + System.currentTimeMillis());
	}
	/** инициализация локальной библиотеки */
	public static void initLocalLibrary(String libName, String prefix,
			String pathPrefix) {
		initLocalLibrary(libName, prefix, "", pathPrefix);
	}
	/** инициализация локальной библиотеки */
	public static void initLocalLibrary(String libName, 
										String prefix,
										String suffix, 
										String pathPrefix) {
		try {
			throw new Exception();
		} catch (Exception e) {
			System.err
					.println("Dummy exception thrown to know which applet loads the native library <"
							+ prefix + libName + "*" + suffix + ">");
			e.printStackTrace();
		}
		String strLibraryPath = System.getProperty("java.library.path");
		{
			File fTempDir = null;
			try {
				fTempDir = File.createTempFile("prefix", "suffix");
				if (fTempDir != null)
					fTempDir = fTempDir.getParentFile();

				fTempDir = new File(fTempDir.getAbsolutePath() + File.separator
						+ "UnsatisfiedLinkWorkAround" + File.separator
						+ pathPrefix);
				fTempDir.mkdirs();
				fTempDir.deleteOnExit();
			} catch (IOException ex) {
			}
			if (fTempDir != null) {
				URL urlSharedLibrary = LoadLibraryFromJar.class
						.getClassLoader().getResource(
								getLocalLibraryName(libName, prefix, suffix));
				File fSharedLibrary = copyFileFromJARToLocalFileSystem(
						urlSharedLibrary, fTempDir);
				fSharedLibrary.deleteOnExit();
				strLibraryPath += File.pathSeparator + "." + File.pathSeparator
						+ fTempDir.getAbsolutePath();
				System.setProperty("java.library.path", strLibraryPath);
				try {
					//DO THE DIRTYNESS on sys_paths
					Field fieldSysPath = ClassLoader.class
							.getDeclaredField("sys_paths");
					fieldSysPath.setAccessible(true);
					if (fieldSysPath != null)
						fieldSysPath.set(System.class.getClassLoader(), null);
				} catch (SecurityException ex) {
					ex.printStackTrace();
				} catch (NoSuchFieldException ex) {
					ex.printStackTrace();
				} catch (IllegalAccessException ex) {
					ex.printStackTrace();
				} catch (IllegalArgumentException ex) {
					ex.printStackTrace();
				}
			}

		}
	}
	
}