package httpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/** объект, который инкапсулирует в себе всю необходимую информацию, полученную от удалнного сервера  */
public class HttpResult {

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
	
	/** произошла ошибка во время Http запроса и/или получения ответа */
	public static final int resultError=1;
	/** информационный обмен с удаленным ресурсом прошел успешно */
	public static final int resultOk=0;
	
	/** общий результат обмена с ресурсом - {@link HttpResult#resultError}, или {@link HttpResult#resultOk}*/
	private int resultValue=resultError;
	/** текст, полученный от удаленного ресурса */
	private String resultText=null;
	
	/** объект, который инкапсулирует в себе всю необходимую информацию, полученную от удалнного сервера  */
	public HttpResult(){
		
	}

	/** объект, который инкапсулирует в себе всю необходимую информацию, полученную от удалнного сервера  
	 * @param value - общий результат информационного обмена ({@link HttpResult#resultError} или {@link HttpResult#resultOk})
	 * @param text - текст, переданный в ответ на Http запрос 
	 * */
	public HttpResult(int value, String text){
		this.resultValue=value;
		this.resultText=text;
	}

	/** загрузить текст ответа из InputStream, с указанной кодировкой 
	 * @param inputStream - поток, из которого необходимо производить чтение данных 
	 * */
	public boolean setResultText(InputStream inputStream){
		return this.setResultText(inputStream, null);
	}
	
	/** загрузить текст ответа из InputStream, с указанной кодировкой 
	 * @param inputStream - поток, из которого необходимо производить чтение данных 
	 * @param charset (<strong>null</strong> - по умолчанию)- кодировка ( набор данных) на основании которой нужно производить чтение из потока
	 * @see {@link java.nio.charset.Charset}  
	 * */
	public boolean setResultText(InputStream inputStream,String charset){
		boolean result=false;
		try{
			InputStreamReader isr=null;
			if(charset!=null){
				isr=new InputStreamReader(inputStream,charset);
			}else{
				isr=new InputStreamReader(inputStream);
			}
			BufferedReader br=new BufferedReader(isr);
			StringBuffer sb=new StringBuffer();
			String line=null;
			while( (line=br.readLine())!=null){
				sb.append(line);
			}
			this.setResultText(sb.toString());
			result=true;
		}catch(IOException ex){
			error("setResultText: "+ex.getMessage());
		}catch(Exception ex){
			error("setResultText: "+ex.getMessage());
		}
		return result;
	}
	
	/** информационный обмен закончился ответом сервера (или же - false, если не удалось свзаться, или получить ответ от удаленного сервера )*/
	public boolean isValidResult(){
		return (this.resultValue==resultOk);
	}
	
	/** общий результат информационного обмена ({@link HttpResult#resultError} или {@link HttpResult#resultOk}) */
	public int getResultValue() {
		return resultValue;
	}

	public void setResultValue(int resultValue) {
		this.resultValue = resultValue;
	}

	/** текст, переданный в ответ на Http запрос */
	public String getResultText() {
		return resultText;
	}

	public void setResultText(String resultText) {
		this.resultText = resultText;
	}

	
}
