import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;


/** класс, который посылает на сервер строку в одном из параметров */
public class Sender {
	public static void main(String[] args){
		String pathToServer="http://localhost:8080/HttpParameterExchange/ShowParameters.jsp";
		String parameterName="data";
		String originalString=">>>>>string form client<<<<<";
		HashMap<String,String> parameters=new HashMap<String,String>();
		parameters.put(parameterName, originalString);
		System.out.println("outputResult:"+sendParametersToUrl(pathToServer,parameters,"WINDOWS-1251",System.out));
	}
	
	/** послать на URL ресурс данные в виде пар: имя=значение запросом POST */
	private static boolean sendParametersToUrl(String pathToServer, 
											   HashMap<String,String> parameters,
											   String charSet,
											   PrintStream response){
		boolean returnValue=false;
		// формирование строки для отправки значений
		StringBuffer data=new StringBuffer();
		Iterator<String> keys=parameters.keySet().iterator();
		String currentKey=null;
		while(keys.hasNext()){
			if(data.length()!=0){
				data.append("&");
			}
			currentKey=keys.next();
			data.append(currentKey);
			data.append("=");
			try{
				data.append(URLEncoder.encode(parameters.get(currentKey), charSet));
			}catch(Exception ex){
				data.append("");
			}
		}
		try{
			URL url=new URL(pathToServer);
			HttpURLConnection connection=(HttpURLConnection)url.openConnection();
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Content-Length", Integer.toString(data.length()+1+4));
			connection.setDoOutput(true);
			connection.setDoInput(true);
			PrintWriter out=new PrintWriter(connection.getOutputStream());
			out.println(data.toString());
			out.close();
			connection.connect();
			InputStream inputStream=connection.getInputStream();
			BufferedReader reader=new BufferedReader(new InputStreamReader(inputStream));
			String currentLine=null;
			while((currentLine=reader.readLine())!=null){
				response.println(currentLine);
			}
			reader.close();
			returnValue=true;
		}catch(Exception ex){
			System.err.println("Send data Error: "+ex.getMessage());
		}
		return returnValue;
	}
}
