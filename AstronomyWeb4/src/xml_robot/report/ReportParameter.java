package xml_robot.report;

import xml_robot.engine.EngineParameterType;

/** �������� ��� ������  */
public class ReportParameter {
	/** ���� ����, ��� ������ �������� �������� ������������ */
	private boolean required;
	/** �������� ������� ��������� � ���� ������ */
	private String value;
	/** ��� ������� ���������  */
	private String parameterName;
	/** ��� ������� ��������� */
	private EngineParameterType type=null;
	/** �������� ������� �������� */
	private String description;
	
	/** �������� ��� ������
	 * @param parameterName - ��� ��������� ( ���������� � �������� ������ ������ )
	 * @param value - ��������� �������� ��� �������� ��������� 
	 * @param type - ��� ���������� 
	 * @param required - �������� �� ������ ����������
	 * @param description - �������� ������� �������� 
	 */
	public ReportParameter(String parameterName, String value, EngineParameterType type, boolean required, String description){
		this.parameterName=parameterName;
		this.value=value;
		this.type=type;
		this.required=required;
		this.description=(description==null)?"":description;
	}
	
	/** ���������� �������� ��� ������� ��������� 
	 * @param value - ��������� �������� ���������
	 * */
	public void setValue(String value){
		this.value=value;
	}
	
	/** �������� ��� ���������� */
	public String getName(){
		return this.parameterName;
	}
	
	/** �������� �� ��������� ���������  */
	public boolean isRequired(){
		return this.required;
	}
	
	/** �������� �������� ��������� */
	public void clear(){
		this.value=null;
	}
	
	@Override
	public boolean equals(Object obj) {
		try{
			return ((ReportParameter)obj).getName().equals(this.getName());
		}catch(Exception ex){
			return false;
		}
	}
	
	public void copyValueFromAnotherParameter(ReportParameter anotherParameter){
		this.value=anotherParameter.value;
	}

	/** �������� �� ������ �������� ������ (�� ���� �� ���������� �������� ) */
	public boolean isNotFill() {
		return this.value==null;
	}
	
	/** �������� ��� ���������  */
	public EngineParameterType getType(){
		return this.type;
	}

	public String getDescription() {
		return this.description;
	}
}
