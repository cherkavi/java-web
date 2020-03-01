package httpClient;

/** �����-������� ��� ����������, ������� ����� ����������  �� HTTP ������ */
public class HttpParameter {
	/** ��� ��������� HTTP */
	private String parameterName;
	/** �������� ��� �������� ��������� */
	private String parameterValue;
	
	public HttpParameter(){
		this.parameterName="";
		this.parameterValue="";
	}

	public HttpParameter(String name, String value){
		this.parameterName=name;
		this.parameterValue=value;
	}

	public String getParameterName() {
		return parameterName;
	}

	public void setParameterName(String parameterName) {
		this.parameterName = parameterName;
	}

	public String getParameterValue() {
		return parameterValue;
	}

	public void setParameterValue(String parameterValue) {
		this.parameterValue = parameterValue;
	}
	
	
}
