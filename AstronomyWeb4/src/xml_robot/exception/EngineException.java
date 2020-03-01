package xml_robot.exception;

/** Exception во время попытки обработки данных  */
public class EngineException extends Exception{
	private final static long serialVersionUID=1L;
	
	private int errorKod;
	private String errorText;
	
	/** Exception во время попытки обработки данных  */
	public EngineException(int errorKod, String errorText){
		this.errorKod=errorKod;
		this.errorText=errorText;
	}
	
	/** код произошедшего события */
	public int getErrroKod(){
		return this.errorKod;
	}
	
	/** текст произошедшего события */
	public String getErrorText(){
		return this.errorText;
	}
}
