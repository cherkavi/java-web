package xml_robot.report;

public enum ReportType {
	/** вывод в формате JPEG */
	JPEG, 
	/** вывод данных в формате XML */
	XML;
	
	public static ReportType getFromString(String value){
		ReportType returnValue=null;
		while(true){
			if(value==null)break;
			value=value.trim().toUpperCase();
			if(value.equalsIgnoreCase("JPEG")){
				returnValue=JPEG; 
				break;
			}
			if(value.equalsIgnoreCase("JPG")){
				returnValue=JPEG;
				break;
			}
			if(value.equalsIgnoreCase("XML")){
				returnValue=XML;
				break;
			}
			break;
		}
		return returnValue;
	}
}
