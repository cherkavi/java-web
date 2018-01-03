package exchange.objects.shared;

import java.io.Serializable;

/** ������ ��� �������������� ������ ����� �������� � �������� */
public class ExchangeObject implements Serializable{
	private final static long serialVersionUID=1L;
	
	private String stringValue;
	private int intValue;
	private byte[] array;
	
	/** 
	 * <strong> ������������ �������������� �� ������ ����������� ������ � ����������� ��� ���������� �� ��������� </strong>
	 * */
	public ExchangeObject(int intValue, String stringValue){
		this.intValue=intValue;
		this.stringValue=stringValue;
	}
	
	public String getStringValue() {
		return stringValue;
	}
	public void setStringValue(String stringValue) {
		this.stringValue = stringValue;
	}
	public int getIntValue() {
		return intValue;
	}
	public void setIntValue(int intValue) {
		this.intValue = intValue;
	}
	public byte[] getArray() {
		return array;
	}
	public void setArray(byte[] array) {
		this.array = array;
	}

	@Override
	public String toString() {
		return "IntValue:"+this.getIntValue()+"   StringValue:"+this.getStringValue()+"   ";
	}
	
}