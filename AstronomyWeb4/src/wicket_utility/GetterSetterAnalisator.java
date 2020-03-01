package wicket_utility;
import java.lang.reflect.Method;


/** ������, ������� �������� ���������� ��� ����������� �� ��������� � �������� �� ��������� getter setter ��������� */
public class GetterSetterAnalisator {
	
	/** ������� ��������� �� ������ */
	private void error(Object information){
		System.out.println("ERROR GetterSetterAnalisator#"+information);
	}
	
	/** ������� ��������� �� ������ */
	@SuppressWarnings("unused")
	private void warn(Object information){
		System.out.println("WARN GetterSetterAnalisator#"+information);
	}

	/** ������� ��������� �� ������ */
	@SuppressWarnings("unused")
	private void debug(Object information){
		System.out.println("DEBUG GetterSetterAnalisator#"+information);
	}
	

	/* ������, ������� �������� ���������� ��� ����������� �� ��������� � �������� �� ��������� getter setter ��������� 
	public GetterSetterAnalisator(){
	}*/
	

	public void copy(Object source, Object destination){
		Class<?> classSource=source.getClass();
		Method[] sourceMethods=classSource.getMethods();
		Class<?> classDestination=destination.getClass();
		Method[] destinationMethods=classDestination.getMethods();
		for(int counter=0;counter<sourceMethods.length;counter++){
			String fieldName=this.getMethodFieldName(sourceMethods[counter]);
			Method destinationCurrentMethod=this.getMethodByName(destinationMethods, "set"+this.firstLetterToUpper(fieldName));
			if((fieldName!=null)&&(destinationCurrentMethod!=null)){
				// ������ get ����� - �������� ��� ������ � �����������
				try{
					Object returnValue=sourceMethods[counter].invoke(source);
					destinationCurrentMethod.invoke(destination, returnValue);
				}catch(Exception ex){
					System.out.println("#copy FieldName: "+fieldName+"  Exception: "+ex.getMessage());
				}
			}
		}
	}

	/** ������ ����� � ������ - UpperCase*/
	private String firstLetterToUpper(String fieldName) {
		if((fieldName!=null)&&(fieldName.length()>0)){
			String temp=new String(new char[]{fieldName.charAt(0)});
			return temp.toUpperCase()+fieldName.substring(1);
		}else{
			return null;
		}
	}

	/** �� ������� ������� ������� ����� �� ��� ����� */
	private Method getMethodByName(Method[] methods, String findName){
		Method returnValue=null;
		try{
			for(int counter=0;counter<methods.length;counter++){
				if(methods[counter].getName().equals(findName)){
					returnValue=methods[counter];
					break;
				}
			}
		}catch(NullPointerException ex){
			error("getMethodByName: "+ex.getMessage());
		}
		return returnValue;
		
	}
	
	/** �������� �� ������ ����� Getter-��, ���� �������� - ������� ��� ��� 
	 * @return ���������� ��� ����, �������� ����������� ������ getter
	 * */
	private String getMethodFieldName(Method method) {
		if(method!=null){
			String tempName=method.getName();
			if(tempName.startsWith("get")){
				return tempName.substring(3);
			}else{
				return null;
			}
		}else{
			return null;
		}
	}
	
	
	
}
