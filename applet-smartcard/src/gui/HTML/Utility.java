package gui.HTML;

/** ������� ����� �������� ����������� ������ ��� ����������� �������� ��� ������� JavaScript � Applet*/
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
	
	
	/** �����, ������� �������� String[] � ������������� ��� � ���� new Array("element_1","ele\"ment_2")
	 * @param parameter - ��������, ������� ������ ���� ������������ 
	 * @return �����, ������� ������ ���� ��������   
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
	
	
	/** ������ ��� ������� � �����, ������� ����� ������� ��� �������� String ��� JavaScript*/
	public static String convertForJavaScript(String value){
		return value.replace("\"", "\\\"");
	}
}
