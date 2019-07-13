package test;

import java.io.Serializable;

/** ������ ��� �������� � ������ JNDI  */
public class SharedObject implements Serializable{

	private static final long serialVersionUID = 1L;
	
	/** ������ ��� �������� � ������ JNDI  */
	public SharedObject(){
	}
	
	/** ������ ��� �������� � ������ JNDI  */
	public SharedObject(String value){
		this.value=value;
	}
	
	private String value;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	
	@Override
	public String toString(){
		return this.value;
	}
}
