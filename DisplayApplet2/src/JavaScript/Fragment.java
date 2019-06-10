package JavaScript;

/** данный класс служит для обмена клиента(Browser) с сервером(Servlet) */
public class Fragment {
	/** имя запрошенной функции */
	private String field_FunctionName;
	/** ключи для дополнительных параметров */
	private String[] field_InformationKeys;
	/** значения для дополнительных параметров */
	private String[] field_InformationValues;
	
	/** установить имя функции */
	public void setFunctionName(String value){
		this.field_FunctionName=value;
	}
	/** получить имя функции */
	public String getFunctionName(){
		return this.field_FunctionName;
	}
	
	/** установить все ключи для дополнительных параметров */
	public void setInformationKeys(String[] values){
		this.field_InformationKeys=values;
	}
	/** получить  все ключи для дополнительных параметров */
	public String[] getInformationKeys(){
		return this.field_InformationKeys;
	}
	
	/** установить все значения для параметров */
	public void setInformationValues(String[] values){
		this.field_InformationValues=values;
	}
	/** получить все значения для параметров */
	public String[] getInformationValues(){
		return this.field_InformationValues;
	}
	
	public int getInformationCount(){
		return this.field_InformationKeys.length;
	}
}
