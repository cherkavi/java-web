package httpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/** ������, ������� ������������� � ���� ��� ����������� ����������, ���������� �� ��������� �������  */
public class HttpResult {

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
	
	/** ��������� ������ �� ����� Http ������� �/��� ��������� ������ */
	public static final int resultError=1;
	/** �������������� ����� � ��������� �������� ������ ������� */
	public static final int resultOk=0;
	
	/** ����� ��������� ������ � �������� - {@link HttpResult#resultError}, ��� {@link HttpResult#resultOk}*/
	private int resultValue=resultError;
	/** �����, ���������� �� ���������� ������� */
	private String resultText=null;
	
	/** ������, ������� ������������� � ���� ��� ����������� ����������, ���������� �� ��������� �������  */
	public HttpResult(){
		
	}

	/** ������, ������� ������������� � ���� ��� ����������� ����������, ���������� �� ��������� �������  
	 * @param value - ����� ��������� ��������������� ������ ({@link HttpResult#resultError} ��� {@link HttpResult#resultOk})
	 * @param text - �����, ���������� � ����� �� Http ������ 
	 * */
	public HttpResult(int value, String text){
		this.resultValue=value;
		this.resultText=text;
	}

	/** ��������� ����� ������ �� InputStream, � ��������� ���������� 
	 * @param inputStream - �����, �� �������� ���������� ����������� ������ ������ 
	 * */
	public boolean setResultText(InputStream inputStream){
		return this.setResultText(inputStream, null);
	}
	
	/** ��������� ����� ������ �� InputStream, � ��������� ���������� 
	 * @param inputStream - �����, �� �������� ���������� ����������� ������ ������ 
	 * @param charset (<strong>null</strong> - �� ���������)- ��������� ( ����� ������) �� ��������� ������� ����� ����������� ������ �� ������
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
	
	/** �������������� ����� ���������� ������� ������� (��� �� - false, ���� �� ������� ��������, ��� �������� ����� �� ���������� ������� )*/
	public boolean isValidResult(){
		return (this.resultValue==resultOk);
	}
	
	/** ����� ��������� ��������������� ������ ({@link HttpResult#resultError} ��� {@link HttpResult#resultOk}) */
	public int getResultValue() {
		return resultValue;
	}

	public void setResultValue(int resultValue) {
		this.resultValue = resultValue;
	}

	/** �����, ���������� � ����� �� Http ������ */
	public String getResultText() {
		return resultText;
	}

	public void setResultText(String resultText) {
		this.resultText = resultText;
	}

	
}
