package xml_robot.exception;

/** Exception �� ����� ������� ��������� ������  */
public class EngineException extends Exception{
	private final static long serialVersionUID=1L;
	
	private int errorKod;
	private String errorText;
	
	/** Exception �� ����� ������� ��������� ������  */
	public EngineException(int errorKod, String errorText){
		this.errorKod=errorKod;
		this.errorText=errorText;
	}
	
	/** ��� ������������� ������� */
	public int getErrroKod(){
		return this.errorKod;
	}
	
	/** ����� ������������� ������� */
	public String getErrorText(){
		return this.errorText;
	}
}
