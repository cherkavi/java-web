package httpClient;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
/** передает данные на HTTP ресурс и возвращает от него
полученную информацию в виде текста.
*/
public class HttpClient {
	
	/** вывод отладочной информации */
	@SuppressWarnings("unused")
	private void debug(Object information){
		System.out.print(this.getClass().getName().toString());
		System.out.print(" DEBUG ");
		System.out.println(information);
	}

	/** вывод информации об ошибке */
	private void error(Object information){
		System.out.print(this.getClass().getName().toString());
		System.out.print(" ERROR ");
		System.out.println(information);
	}
	
	/** полный путь к ресурсу HTTP на который нужно передавать данные и принимать эти данные из него */
	private String url;
	/** параметры, которые нужно передавать для удаленного ресурса */
	private ArrayList<HttpParameter> parameters=new ArrayList<HttpParameter>();
	
	/** передает данные на HTTP ресурс и возвращает от него
	* полученную информацию в виде текста.
	* @param url - полный путь к ресурсу Http, на который нужно передать данные и получить ответ от него
	* @param params - параметры, которые следует добавить в запрос HTTP
	*/
	public HttpClient(String url,HttpParameter ... params){
		this.url=url;
		for(int counter=0;counter<params.length;counter++){
			this.parameters.add(params[counter]);
		}
	}
	
	/** добавить параметр для отправки на сервер */
	public void addParameter(HttpParameter parameter){
		this.parameters.add(parameter);
	}

	/** отправить запрос на сервер и получить ответ о проведенной операции ({@link HttpResult}})
	 * */
	public HttpResult requestToServer(String readCharset){
		HttpResult result=new HttpResult();
		try{
			// подключиться к удаленному серверу
			URL url=new URL(this.url);
			HttpURLConnection connection=(HttpURLConnection)url.openConnection();
			connection.setDoInput(true);
			connection.setDoOutput(true);
			connection.setRequestMethod("POST");
			connection.setUseCaches(false); // возможно, говорит о том что при соединении не передается тело запроса
			// соединиться с сервером
			connection.connect();
			// записать данные в сервер 
			PrintWriter printToServer=new PrintWriter(connection.getOutputStream());
			printToServer.print(this.getHttpParametersAsString());
			printToServer.flush();
			printToServer.close();
			// получить ответ от удаленного сервера
			InputStream inputStream=connection.getInputStream();
			result.setResultText(inputStream,readCharset);
			inputStream.close();
			result.setResultValue(HttpResult.resultOk);
		}catch(Exception ex){
			result.setResultValue(HttpResult.resultError);
			error("requestToServer: "+ex.getMessage());
		}
		return result;
	}
	

	/** получить для передачи на HTTP ресурс в виде строки */
	private String getHttpParametersAsString(){
		StringBuffer result=new StringBuffer();
		for(int counter=0;counter<this.parameters.size();counter++){
			try{
				result.append(URLEncoder.encode(this.parameters.get(counter).getParameterName(),"UTF-8")
						      +"="
						      +URLEncoder.encode(this.parameters.get(counter).getParameterValue(),"UTF-8"));
			}catch(UnsupportedEncodingException ex){
				error("Encoding Exception:"+ex.getMessage());
			};
			if(counter!=(this.parameters.size()-1)){
				result.append("&");
			}
		}
		return result.toString();
	}
}
