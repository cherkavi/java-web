package test;

import java.io.Serializable;

/** объект для хранения в дереве JNDI  */
public class SharedObject implements Serializable{

	private static final long serialVersionUID = 1L;
	
	/** объект для хранения в дереве JNDI  */
	public SharedObject(){
	}
	
	/** объект для хранения в дереве JNDI  */
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
