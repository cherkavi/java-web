package wicket_utility;
import java.lang.reflect.Method;


/** объект, который содержит функционал дл€ копировани€ из источника в приемник по найденным getter setter свойствам */
public class GetterSetterAnalisator {
	
	/** вывести сообщение об ошибке */
	private void error(Object information){
		System.out.println("ERROR GetterSetterAnalisator#"+information);
	}
	
	/** вывести сообщение об ошибке */
	@SuppressWarnings("unused")
	private void warn(Object information){
		System.out.println("WARN GetterSetterAnalisator#"+information);
	}

	/** вывести сообщение об ошибке */
	@SuppressWarnings("unused")
	private void debug(Object information){
		System.out.println("DEBUG GetterSetterAnalisator#"+information);
	}
	

	/* объект, который содержит функционал дл€ копировани€ из источника в приемник по найденным getter setter свойствам 
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
				// найден get метод - получить его данные и попробовать
				try{
					Object returnValue=sourceMethods[counter].invoke(source);
					destinationCurrentMethod.invoke(destination, returnValue);
				}catch(Exception ex){
					System.out.println("#copy FieldName: "+fieldName+"  Exception: "+ex.getMessage());
				}
			}
		}
	}

	/** первую букву в строке - UpperCase*/
	private String firstLetterToUpper(String fieldName) {
		if((fieldName!=null)&&(fieldName.length()>0)){
			String temp=new String(new char[]{fieldName.charAt(0)});
			return temp.toUpperCase()+fieldName.substring(1);
		}else{
			return null;
		}
	}

	/** из массива методов вернуть метод по его имени */
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
	
	/** €вл€етс€ ли данный метод Getter-ом, если €вл€етс€ - вернуть его им€ 
	 * @return возвращает им€ пол€, которому принадлежит данный getter
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
