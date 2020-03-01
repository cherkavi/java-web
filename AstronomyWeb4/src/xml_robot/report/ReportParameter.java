package xml_robot.report;

import xml_robot.engine.EngineParameterType;

/** параметр для отчета  */
public class ReportParameter {
	/** флаг того, что данный параметр является обязательным */
	private boolean required;
	/** значение данного параметра в виде текста */
	private String value;
	/** имя данного параметра  */
	private String parameterName;
	/** тип данного параметра */
	private EngineParameterType type=null;
	/** описание данного элемента */
	private String description;
	
	/** параметр для отчета
	 * @param parameterName - имя параметра ( уникальное в пределах одного отчета )
	 * @param value - текстовое значение для текущего параметра 
	 * @param type - тип переменной 
	 * @param required - является ли данная переменная
	 * @param description - описание данного элемента 
	 */
	public ReportParameter(String parameterName, String value, EngineParameterType type, boolean required, String description){
		this.parameterName=parameterName;
		this.value=value;
		this.type=type;
		this.required=required;
		this.description=(description==null)?"":description;
	}
	
	/** установить значение для данного параметра 
	 * @param value - текстовое значение параметра
	 * */
	public void setValue(String value){
		this.value=value;
	}
	
	/** получить имя переменной */
	public String getName(){
		return this.parameterName;
	}
	
	/** является ли компонент требуемым  */
	public boolean isRequired(){
		return this.required;
	}
	
	/** очистить значение параметра */
	public void clear(){
		this.value=null;
	}
	
	@Override
	public boolean equals(Object obj) {
		try{
			return ((ReportParameter)obj).getName().equals(this.getName());
		}catch(Exception ex){
			return false;
		}
	}
	
	public void copyValueFromAnotherParameter(ReportParameter anotherParameter){
		this.value=anotherParameter.value;
	}

	/** является ли данный параметр пустым (то есть не содержащим значение ) */
	public boolean isNotFill() {
		return this.value==null;
	}
	
	/** получить тип параметра  */
	public EngineParameterType getType(){
		return this.type;
	}

	public String getDescription() {
		return this.description;
	}
}
