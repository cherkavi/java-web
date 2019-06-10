package JavaScript;

/** ������ ����� ������ ��� ������ �������(Browser) � ��������(Servlet) */
public class Fragment {
	/** ��� ����������� ������� */
	private String field_FunctionName;
	/** ����� ��� �������������� ���������� */
	private String[] field_InformationKeys;
	/** �������� ��� �������������� ���������� */
	private String[] field_InformationValues;
	
	/** ���������� ��� ������� */
	public void setFunctionName(String value){
		this.field_FunctionName=value;
	}
	/** �������� ��� ������� */
	public String getFunctionName(){
		return this.field_FunctionName;
	}
	
	/** ���������� ��� ����� ��� �������������� ���������� */
	public void setInformationKeys(String[] values){
		this.field_InformationKeys=values;
	}
	/** ��������  ��� ����� ��� �������������� ���������� */
	public String[] getInformationKeys(){
		return this.field_InformationKeys;
	}
	
	/** ���������� ��� �������� ��� ���������� */
	public void setInformationValues(String[] values){
		this.field_InformationValues=values;
	}
	/** �������� ��� �������� ��� ���������� */
	public String[] getInformationValues(){
		return this.field_InformationValues;
	}
	
	public int getInformationCount(){
		return this.field_InformationKeys.length;
	}
}
