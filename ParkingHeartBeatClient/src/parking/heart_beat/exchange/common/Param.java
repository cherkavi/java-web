package parking.heart_beat.exchange.common;

/** параметр с именем и значением */
public class Param {
	private String name;
	private String value;
	
	/** параметр с именем и значением */
	public Param(){
	}
	

	/** параметр с именем и значением 
	 * @param name - имя параметра
	 * @param value - значение параметра
	 */
	public Param(String name, String value){
		this.name=name;
		this.value=value;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	
}
