package gui.HTML;

/** данныый класс содержит статические методы для конвертации значений при общении JavaScript с Applet*/
public class Utility {
	
	private static void debug(String information){
		System.out.print("Utility: ");
		System.out.print("  DEBUG: ");
		System.out.println(information);
	}
	@SuppressWarnings("unused")
	private static void error(String information){
		System.out.print("Utility: ");
		System.out.print("  ERROR: ");
		System.out.println(information);
	}
	
	
	/** метод, который получает String[] и преобразовует его к виду new Array("element_1","ele\"ment_2")
	 * @param parameter - параметр, который должен быть преобразован 
	 * @return текст, который должен быть вставлен   
	 * */
	public static String getJavaScriptArrayFromArray(String[] parameter){
		StringBuffer return_value=new StringBuffer();
		return_value.append("new Array(");
		for(int counter=0;counter<parameter.length;counter++){
			return_value.append("\""+convertForJavaScript(parameter[counter])+"\"");
			if(counter!=(parameter.length-1)){
				return_value.append(", ");
			}
		}
		return_value.append(")");
		debug(" getJavaScriptArrayFromArray: "+return_value.toString());
		return return_value.toString();
		
	}
	
	
	/** строка для вставки в текст, который будет передан как параметр String для JavaScript*/
	public static String convertForJavaScript(String value){
		return value.replace("\"", "\\\"");
	}
}
