package httpClient;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
/** �������� ������ �� HTTP ������ � ���������� �� ����
���������� ���������� � ���� ������.
*/
public class HttpClient {
	
	/** ����� ���������� ���������� */
	@SuppressWarnings("unused")
	private void debug(Object information){
		System.out.print(this.getClass().getName().toString());
		System.out.print(" DEBUG ");
		System.out.println(information);
	}

	/** ����� ���������� �� ������ */
	private void error(Object information){
		System.out.print(this.getClass().getName().toString());
		System.out.print(" ERROR ");
		System.out.println(information);
	}
	
	/** ������ ���� � ������� HTTP �� ������� ����� ���������� ������ � ��������� ��� ������ �� ���� */
	private String url;
	/** ���������, ������� ����� ���������� ��� ���������� ������� */
	private ArrayList<HttpParameter> parameters=new ArrayList<HttpParameter>();
	
	/** �������� ������ �� HTTP ������ � ���������� �� ����
	* ���������� ���������� � ���� ������.
	* @param url - ������ ���� � ������� Http, �� ������� ����� �������� ������ � �������� ����� �� ����
	* @param params - ���������, ������� ������� �������� � ������ HTTP
	*/
	public HttpClient(String url,HttpParameter ... params){
		this.url=url;
		for(int counter=0;counter<params.length;counter++){
			this.parameters.add(params[counter]);
		}
	}
	
	/** �������� �������� ��� �������� �� ������ */
	public void addParameter(HttpParameter parameter){
		this.parameters.add(parameter);
	}

	/** ��������� ������ �� ������ � �������� ����� � ����������� �������� ({@link HttpResult}})
	 * */
	public HttpResult requestToServer(String readCharset){
		HttpResult result=new HttpResult();
		try{
			// ������������ � ���������� �������
			URL url=new URL(this.url);
			HttpURLConnection connection=(HttpURLConnection)url.openConnection();
			connection.setDoInput(true);
			connection.setDoOutput(true);
			connection.setRequestMethod("POST");
			connection.setUseCaches(false); // ��������, ������� � ��� ��� ��� ���������� �� ���������� ���� �������
			// ����������� � ��������
			connection.connect();
			// �������� ������ � ������ 
			PrintWriter printToServer=new PrintWriter(connection.getOutputStream());
			printToServer.print(this.getHttpParametersAsString());
			printToServer.flush();
			printToServer.close();
			// �������� ����� �� ���������� �������
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
	

	/** �������� ��� �������� �� HTTP ������ � ���� ������ */
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
