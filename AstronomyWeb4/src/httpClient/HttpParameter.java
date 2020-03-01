package httpClient;

/** класс-обертка для параметров, которые нужно передавать  на HTTP ресурс */
public class HttpParameter {
	/** имя параметра HTTP */
	private String parameterName;
	/** значение для передачи параметра */
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
