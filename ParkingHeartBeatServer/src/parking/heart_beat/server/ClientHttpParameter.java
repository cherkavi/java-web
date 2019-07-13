package parking.heart_beat.server;

/** �������� � ���� �������, ������� "������" �� ������� */
public class ClientHttpParameter {
	private String paramName;
	private String paramValue;
	
	/** �������� � ���� �������, ������� "������" �� ������� */
	public ClientHttpParameter(){
	}
	
	/** �������� � ���� �������, ������� "������" �� ������� 
	 * @param paramName - ��� ���������
	 * @param paramValue - �������� ��������� 
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
