package Utility;
import java.net.*;
import java.io.*;


public class UrlResource {
    private static boolean FLAG_DEBUG=true;
    /** 
     * метод для отладки информации
     */
    protected static void debug(String information){
        if(FLAG_DEBUG==true){
            System.out.print("UrlResource ");
            System.out.print("DEBUG: ");
            System.out.println(information);
        }
    }
    protected static void error(String information){
    	System.out.print("UrlResource ");
    	System.out.print("ERROR: ");
    	System.out.println(information);
    }
    

    /** читает URL и возвращает данные в виде строки 
     * @param url_path полный путь к URL данные из которого нужно получить 
     */
    public static String get_http_text(String url_path) throws Exception {
    	return get_http_text(url_path,new String[]{}, new String[]{});
    }
    
    /** читает URL и возвращает данные в виде строки 
     * @param url_path полный путь к URL данные из которого нужно получить 
     * @param key - имена параметров 
     * @param value - значения параметров 
     */
    public static String get_http_text(String   url_path,
    							String[] key,
    							String[] value) throws Exception{
    	StringBuffer return_value=new StringBuffer();
        try{
            URL url=new URL(url_path);
            HttpURLConnection connection=(HttpURLConnection)url.openConnection();
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setRequestMethod("GET");
            //connection.setRequestProperty("Accept","text//xml,application//xml,application//xhtml+xml,text//html;q=0.9,text//plain;q=0.8,image//png,*//*;q=0.5");
            //connection.setRequestProperty("User-Agent"," Mozilla//5.0 (Windows; U; Windows NT 5.1; uk; rv:1.8.1.13) Gecko//20080311 Firefox//2.0.0.13");

            // создание переменных запроса
            StringBuffer data=new StringBuffer();
            for(int counter=0;counter<key.length;counter++){
                if(counter==0){
                    data.append(key[counter]+"="+value[counter].replaceAll(" ","%20"));
                }else{
                    data.append("&"+key[counter]+"="+value[counter].replaceAll(" ","%20"));
                }
            }
            OutputStreamWriter output=new OutputStreamWriter(connection.getOutputStream());
            output.write(data.toString());
            output.flush();
            connection.connect();
            InputStream input_stream=connection.getInputStream();
            InputStreamReader isr=new InputStreamReader(input_stream,"UTF8");
            BufferedReader br=new BufferedReader(isr);
            String current_string;
            while( (current_string=br.readLine())!=null){
            	return_value.append(current_string);
            }
            input_stream.close();
            isr.close();
            debug("OK");
        }catch(Exception ex){
            error(" get_http_text:"+ex.getMessage());
            throw ex;
        }
    	return return_value.toString();
    }

}
