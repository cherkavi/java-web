package manager.transport;

import java.io.Serializable;
import java.util.ArrayList;

/** Класс, который содержит SubCommand которые нужно выполнить или которые были выполнены,
 * в зависимости от направления (Direction)
 */
public class Transport implements Serializable{
	/**
	 * Serial Version
	 */
	private static final long serialVersionUID = 3L;
	/** type of command - REQUEST пакет от клиента */
	public static int TYPE_REQUEST=0;
	/** type of command - RESPONSE пакет от сервера */
	public static int TYPE_RESPONSE=1;
	/** направление команды - чем является команда - вопросом или ответом */
	private int field_direction=0;

	/** статус полученного пакета OK - можно продолжать обмен*/
	public static int STATUS_OK=0;
	/** статус полученного пакета ERROR - прекращение информационного обмена*/
	public static int STATUS_ERROR=1;
	/** статус полученного пакет DONE - все необходимые действия по Action выполнены <br> 
	 * (только для клиента - флаг об окончании обмена с сервером - просмотреть полученные результаты)
	 */
	public static int STATUS_DONE=2;
	/** статус текущего пакета */
	private int field_status=0;
	/** уникальное имя для текущего ACTION*/
	private String field_action="";
	/** уникальное имя для текущей сессии */
	private String field_session_id=null;
	/** SubCommand Store*/
	private ArrayList<SubCommand> field_store=new ArrayList<SubCommand>();

	/** Information Name хранит дополнительное описание - ключ информации */
	private ArrayList<String> field_information_key=new ArrayList<String>();
	/** Information Description хранит дополнительное описание - содержание информации */
	private ArrayList<String> field_information_text=new ArrayList<String>();
	
	/** Получить уникальный идентификатор соединения клиента и сервера - SessionID*/
	public String getSessionId(){
		return this.field_session_id;
	}
	/** Установить уникальный идентификатор соединения клиента и сервера - SessionID*/
	public void setSessionId(String value){
		this.field_session_id=value;
	}
	/** Получить ответ, есть ли в данном объекте уникальный идентификатор сессии ?*/
	public boolean isSessionIdPresent(){
		boolean return_value=true;
		if(this.field_session_id==null){
			return false;
		};
		if(this.field_session_id.equals("")){
			return false;
		}
		return return_value;
	}
	
	/** Добавить SubCommand в очередь  
	 * append command to store*/
	public void addSubCommand(SubCommand value){
		this.field_store.add(value);
	}
	/** 
	 * получить имя текущего Action, который выполняется в данный момент  
	 */
	public String getActionName(){
		return this.field_action;
	}
	/**
	 * установить имя текущего Action
	 */
	public void setActionName(String action){
		this.field_action=action;
	}
	
	/** Получить SubCommand по индексу
	 * @param value SubCommand by index
	 * @return null, if index is of bounds 
	 */
	public SubCommand getSubCommand(int value){
		if(value<this.getSubCommandCount()){
			return this.field_store.get(value);
		}else{
			return null;
		}
	}
	/** 
	 * Получить SubCommand по уникальному индексу в пакете ActionStep
	 * @param unique_index уникальный индекс в пакете ActionStep
	 * @return null, если по указанному индексу не найден SubCommand
	 */
	public SubCommand getSubCommandByUniqueIndex(int unique_index){
		SubCommand return_value=null;
		for(int counter=0;counter<this.getSubCommandCount();counter++){
			if(this.getSubCommand(counter).getUniqueIndex()==unique_index){
				return_value=this.getSubCommand(counter);
				break;
			}
		}
		return return_value;
	}
	
	/** @return command count from store*/
	public int getSubCommandCount(){
		return this.field_store.size();
	}
	
	/** set direction */
	public void setDirection(int value){
		this.field_direction=value;
	}
	/** get direction*/
	public int getDirection(){
		return this.field_direction;
	}
	/** set status */
	public void setStatus(int status){
		this.field_status=status;
	}
	/** get status*/
	public int getStatus(){
		return this.field_status;
	}
	
	/** добавить новую информацию в поле, которое содержит Информацию по объекту 
	 * @param key ключ для записи
	 * @param value текст записи
	 */
	public void addInformation(String key, String value){
		this.field_information_key.add(key);
		this.field_information_text.add(value);
	}
	/** удалить информацию в поле, которое содержит Информацию по объетку
	 * @param key
	 */
	public boolean removeInformationByKey(String key){
		boolean return_value=false;
		int index_for_remove=this.field_information_key.indexOf(key);
		if(index_for_remove>=0){
			this.field_information_key.remove(index_for_remove);
			this.field_information_text.remove(index_for_remove);
			return_value=true;
		}
		return return_value;
	}
	/** удалить информацию в поле, которое содержит Информацию по объетку
	 * @param index
	 */
	public boolean removeInformationByIndex(int index){
		boolean return_value=false;
		if((index>=0)&&(index<this.getInformationCount())){
			this.field_information_key.remove(index);
			this.field_information_text.remove(index);
			return_value=true;
		}
		return return_value;
	}
	
	/** получить количество записей в поле Информация */
	public int getInformationCount(){
		return this.field_information_key.size();
	}
	/** очистить всю Информацию */
	public void clearInformation(){
		this.field_information_text.clear();
		this.field_information_key.clear();
	}
	/** получить информацию по имени ключа 
	 * @param key имя ключа
	 */
	public String getInformationTextByKey(String key){
		String return_value=null;
		int index=this.field_information_key.indexOf(key);
		if(index>=0){
			return_value=this.field_information_text.get(index);
		}
		return return_value;
	}
	/** получить Description информации по порядковому номеру в списке */
	public String getInformationTextByIndex(int index){
		return this.field_information_text.get(index);
	}
	/** получить Key информации по порядковому номер */ 
	public String getInformationKeyByIndex(int index){
		try{
			return this.field_information_key.get(index);
		}catch(Exception ex){
			return "";
		}
		
	}
	
	/** установить компонент в первоначальное состояние <br> 
	 *  флаги в первоначальное состояние <br>
	 *  очищаем очередь команд
	 */
	public void reset(){
		this.field_direction=0;
		this.field_status=0;
		this.field_store.clear();
		this.clearInformation();
	}
	
	/** очистить всю очередь команд */
	public void clearSubCommand(){
		this.field_store.clear();
	}
	
	/** получить все ключи для поля Information в виде String[]*/
	public String[] getInformationKeys(){
		return this.field_information_key.toArray(new String[]{});
	}
	/** получить все значения  для поля Information в виде String[]*/
	public String[] getInformationValues(){
		return this.field_information_text.toArray(new String[]{});
	}
}
