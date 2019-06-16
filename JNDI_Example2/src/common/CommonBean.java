package common;

/** объект, который хранится в JNDI и может быть извлечен из него */
public class CommonBean {
	public static String staticValue="this is CommonBean";
	private String message;
	private Integer value;
	
	/** обязательный конструктор без аргументов */
	public CommonBean(){
		System.out.println("CommonBean is created "+this.hashCode());
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}
	
	@Override
	public void finalize(){
		System.out.println("CommonBean is finally:"+this.hashCode());
	}
}
