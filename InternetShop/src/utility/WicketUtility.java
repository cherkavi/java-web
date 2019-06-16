package utility;

import java.util.List;

import org.apache.wicket.Component;
import org.apache.wicket.behavior.IBehavior;
import org.apache.wicket.behavior.SimpleAttributeModifier;

/** класс, который содержит необходимые Utility для работы с Wicket и HTML */
public class WicketUtility {
	/** удалить из строки Style элемент указанного названия 
	 * @param style_string - строка стиля 
	 * @param element_name
	 * @return возвращает строку Style 
	 */
	public static String removeStyleElement(String style_string, String element_name){
		int index_element_begin=style_string.indexOf(element_name);
		if(index_element_begin>=0){
			int index_element_end=style_string.indexOf(";");
			if(index_element_end>=0){
				if(index_element_begin==0){
					// искомый элемент первый
					return style_string.substring(index_element_end+1);
				}else{
					return style_string.substring(0,index_element_begin)+style_string.substring(index_element_end+1);
				}
			}else{
				// искомый элемент последний 
				return style_string.substring(0, index_element_begin);
			}
		}else{
			return style_string;
		}
	}
	
	/** получить значение элемента из строки style по указанному имени 
	 * @param style_string - строка style из элемента HTMl 
	 * @param element_name - имя элемента в строке Style
	 * @return пустую строку, либо же значение элемента
	 * */
	public static String getStyleElement(String style_string, String element_name){
		String return_value=null;
		int index_element_begin=style_string.indexOf(element_name);
		if(index_element_begin>=0){
			int index_value_begin=style_string.indexOf(":",index_element_begin);
			if(index_value_begin>=0){
				int index_value_end=style_string.indexOf(";",index_value_begin);
				if(index_value_end>=0){
					return_value=style_string.substring(index_value_begin+1,index_value_end);
				}else{
					// element has ends ( has placing in tail )
					return_value=style_string.substring(index_value_begin+1);
				}
			}else{
				//element value not found
			}
		}else{
			// element not found
		}
		
		return (return_value==null)?"":return_value;
	}

	/** добавить в строку элемент указанного названия 
	 * @param style_string - строка стиля
	 * @param element_name - имя элемента 
	 * @param element_value - значение элемента
	 * @return
	 */
	public static String addStyleElement(String style_string,String element_name, String element_value){
		return element_name+":"+element_value+";"+style_string;
	}
	
	/** 
	 * удалить из элемента SimpleAttributeModifier по указанному имени
	 * @param name - указанное имя, по которому нужно удалить данный модификатор  
	 */
	@SuppressWarnings("unchecked")
	public static void removeSimpleAttributeModifier(Component component,String modifier_name){
		List<IBehavior> list=component.getBehaviors();
		for(int counter=0;counter<list.size();counter++){
			if(list.get(counter) instanceof SimpleAttributeModifier){
				if(((SimpleAttributeModifier)list.get(counter)).getAttribute().equals(modifier_name)){
					component.remove(list.get(counter));
					break;
				}
			}
		}
	}
	
	/** получить строку стиля из компонента */
	public static String getStyleString(Component component){
		String return_value=null;
		List<?> list=component.getBehaviors();
		for(int counter=0;counter<list.size();counter++){
			if(list.get(counter) instanceof SimpleAttributeModifier){
				if(((SimpleAttributeModifier)list.get(counter)).getAttribute().equalsIgnoreCase("style")){
					try{
						return_value=(String) ((SimpleAttributeModifier)list.get(counter)).getValue();
					}catch(Exception ex){};
					break;
				}
			}
		}
		return (return_value==null)?"":return_value;
	}

}
