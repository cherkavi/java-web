package JavaScript;

import java.util.ArrayList;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

/** 
 * Класс, который является отображением функций JavaScript клиента на сервере
 * обращение к методу sendToServer приводит к получению необходимых данных для загрузки в объект 
 */
public class GetBody {
	private static Logger field_logger;
	static{
		field_logger=Logger.getLogger("JavaScript.GetBody");
		field_logger.setLevel(Level.DEBUG);
		BasicConfigurator.configure();
	}
	/** путь к сервлету DisplayApplet*/
	private String path_to_display_applet="http://192.168.15.119:8080/DisplayApplet/DisplayForApplet";
	
	
	/** получить запрос от клиента и отреагировать на него <br> 
	 *  клиент вызывает данную функцию из броузера (JavaScript) и благодаря DWR мы получаем все объекты уже здесь
	 *  @param fragment_from_client - данный параметр является блоком информации, который передает клиент серверу для обработки 
	 */
	public Fragment sendToServer(JavaScript.Fragment fragment_from_client){
		// CONNECTION:6
		// TODO SubCommand.FOR_DISPLAY (SubCommand.CommandName())анализ функции, которую нужно отобразить на клиенте в HTML  
		Fragment fragment=null;
		try{
			field_logger.debug("CONNECTION:6  function:"+fragment_from_client.getFunctionName());
			fragment=sendHttpRequest(fragment_from_client);
			field_logger.debug("CONNECTION:9 ");

/*			// проверка на приход функции SHOWDEVICES 
			if(fragment_from_client.getFunctionName().equals("SHOWDEVICES")){
				field_logger.debug("CONNECTION:6  function: SHOWDEVICES");
				try{
					// отправить CONNECTION:7->    получить CONNECTION:8<-
					fragment=sendHttpRequest(fragment_from_client);
				}catch(Exception ex){
					field_logger.error("sendToServer exception:"+ex.getMessage());
				}
				field_logger.debug("CONNECTION:9 ");
			}else{
				field_logger.debug("Fragment.functionName is unknown"+fragment_from_client.getFunctionName());
			}
			if(fragment_from_client)
			// проверка, если запрос не отработан ни одной функцией
			if(fragment==null){
				fragment=fragment_from_client;
			}
*/			
		}catch(Exception ex){
			field_logger.error("sendToServer Exception:"+ex.getMessage());
		}
		// CONNECTION:9
		return fragment;
	}
	
	/** вернуть массив из значений, который состоит из переданного значения и переданного массива 
	 * @param first_value - первое значение в списке
	 * @param array_for_add - массив из значений 
	 * */
	private String[] addStringToStringArray(String first_value,String[] array_for_add){
		ArrayList<String> temp=new ArrayList<String>();
		temp.add(first_value);
		if(array_for_add!=null){
			for(int counter=0;counter<array_for_add.length;counter++){
				temp.add(array_for_add[counter]);
			}
		}
		return temp.toArray(new String[]{});
	}
	
	/** 
	 * Послать CONNECTION:7     получить CONNECTION:8
	 * @param fragment - полученный CONNECTION:6 от JavaScript(DWR) 
	 * @return
	 */
	private Fragment sendHttpRequest(Fragment fragment_6){
		Fragment fragment_9=new Fragment();
		try{
			fragment_9.setFunctionName("innerHTML");
			fragment_9.setInformationValues(new String[]{readUrlByParameter(addStringToStringArray("FUNCTIONNAME",fragment_6.getInformationKeys()),
														 					addStringToStringArray(fragment_6.getFunctionName(),fragment_6.getInformationValues())
														 					)
														 }
										    );
			fragment_9.setInformationKeys(new String[]{"MAINFRAME"});
		}catch(Exception ex){
			fragment_9.setFunctionName("ERROR");
			fragment_9.setInformationValues(new String[]{"Error DisplayForApplet"});
			fragment_9.setInformationKeys(new String[]{"MESSAGE"});
		}
		return fragment_9;
	}

	/** обращение к сервлету DisplayApplet, <br> 
	 * имея параметры @param keys <br>
	 * имея значения параметров @param values<br>
	 * @throw если произошла ошибка во время получения ответа от сервлета 
	 */
	private String readUrlByParameter(String[] keys, String[] values) throws Exception{
		String return_value="";
		return_value=Utility.UrlResource.get_http_text(path_to_display_applet, keys, values);
		return return_value;
	}
	
}
