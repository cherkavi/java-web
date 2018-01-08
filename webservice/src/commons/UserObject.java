package commons;

import java.io.Serializable;

public class UserObject implements Serializable{
	private final static long serialVersionUID=1L;
	public static String staticString="";
	private int intValue;
	private String stringValue;
	
	public UserObject(){
	}
	
	public UserObject(int a, String value){
		this.intValue=a;
		this.stringValue=value;
	}

	/**
	 * @return the intValue
	 */
	public int getIntValue() {
		return intValue;
	}

	/**
	 * @param intValue the intValue to set
	 */
	public void setIntValue(int intValue) {
		this.intValue = intValue;
	}

	/**
	 * @return the stringValue
	 */
	public String getStringValue() {
		return stringValue;
	}

	/**
	 * @param stringValue the stringValue to set
	 */
	public void setStringValue(String stringValue) {
		this.stringValue = stringValue;
	}
	
	
}
