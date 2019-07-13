package transport;

import java.io.Serializable;

public class Transport implements Serializable{
	private final static long serialVersionUID=1L;
	
	private String transportName;
	private String string;
	private Integer integer;
	private byte[] byteArray;
	
	public Transport(String value){
		this.transportName=value;
	}

	public String getTransportName() {
		return transportName;
	}

	public void setTransportName(String transportName) {
		this.transportName = transportName;
	}

	public String getString() {
		return string;
	}

	public void setString(String string) {
		this.string = string;
	}

	public Integer getInteger() {
		return integer;
	}

	public void setInteger(Integer integer) {
		this.integer = integer;
	}

	public byte[] getByteArray() {
		return byteArray;
	}

	public void setByteArray(byte[] byteArray) {
		this.byteArray = byteArray;
	}

	public String toString(){
		return "Name:"+this.transportName+"     Integer:"+this.integer+"   String:"+this.string;
	}
}
