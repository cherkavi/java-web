package common;

/** ������, ������� �������� � JNDI � ����� ���� �������� �� ���� */
public class CommonBean {
	public static String staticValue="this is CommonBean";
	private String message;
	private Integer value;
	
	/** ������������ ����������� ��� ���������� */
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
