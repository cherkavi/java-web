package parking.heart_beat.server;

/** параметр в виде объекта, который "пришел" от клиента */
public class ClientHttpParameter {
	private String paramName;
	private String paramValue;
	
	/** параметр в виде объекта, который "пришел" от клиента */
	public ClientHttpParameter(){
	}
	
	/** параметр в виде объекта, который "пришел" от клиента 
	 * @param paramName - имя параметра
	 * @param paramValue - значение параметра 
	 */
	public ClientHttpParameter(String paramName, String paramValue){
		this.paramName=paramName;
		this.paramValue=paramValue;
	}

	public String getParamName() {
		return paramName;
	}

	public void setParamName(String paramName) {
		this.paramName = paramName;
	}

	public String getParamValue() {
		return paramValue;
	}

	public void setParamValue(String paramValue) {
		this.paramValue = paramValue;
	}
	
	
}
