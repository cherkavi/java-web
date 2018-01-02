package manager.transport;

import java.io.Serializable;
import java.util.ArrayList;


/** данный класс является единицей, которая должна выполнить команду, либо дать указание на выполнение*/
public class SubCommand  implements Serializable{
	/**
	 * Serial Version
	 */
	private static final long serialVersionUID = 1L;
	/** command name 
	 * уникальный идентификатор команды 
	 */
	private String field_command;
	/** параметр (in/out) byte[] для отработки данных*/
	private byte[] field_parameter=null;
	/** параметр (in/out) int для обработки данных - пока для передачи клиенту необходимых данных, ответ получаем в getParameter()*/
	private int field_parameter_int;
	/** параметр (in/out) String для отработки данных*/
	private String field_parameter_string;
	
	/** содержит ключи дополнительных параметров */
	private ArrayList<String> field_parameter_keys=null;
	/** содержит значения дополнительных параметров */
	private ArrayList<String> field_parameter_values=null;
	
	/** флаг, который говорит о том, что данные в команде были изменены <br>
	 * необходимо когда сервер дает задание, а клиент кладет ответ в тот же объект,
	 * но в случае успешного изменения меняет данный флаг
	 */
	private int field_data_description=DATA_ORIGINAL;
	
	/** флаг, который говорит о том что в объекте находятся оригинальные данные */
	public static int DATA_ORIGINAL=0;
	/** флаг, который говорит о том, что в объекте находятся уже измененные данные */
	public static int DATA_FOR_RESPONSE=1;

	/** флаг, который говорит о принадлежности команды к общению с CardReader*/
	public static int FOR_READER=0;
	/** флаг, который говорит о принадлежности команды для отображения данных пользователю*/
	public static int FOR_DISPLAY=1;
	/** уникальный номер команды для четкой идентификации команды в пакете ActionStep на стороне сервера*/
	private int field_unique_index=(-1);
	
	/** флаг, который говорит о принадлежности команды :
	 * <li> Общению с Reader-ом </li>, 
	 * <li> для отобрежния данных клиенту</li> 
	 */
	private int field_command_for=FOR_READER;
	
	/**
	 * Минимальная единица, которая совершает действие на стороне клиента,
	 * и возвращает данные по действию на сторону сервера
	 * @param command_name
	 * @param field_parameter значение, которое нужно передать в параметрах 
	 */
	public SubCommand(String command_name,byte[] parameter){
		this.setCommand(command_name);
		this.setParameter(parameter);
	}
	
	/** 
	 * Минимальная единица, которая совершает действие на стороне клиента,
	 * и возвращает данные по действию на сторону сервера
	 * @param unique_index уникальный индекс в пакете ActionStep  
	 * @param command_name
	 * @param field_parameter значение, которое нужно передать в параметрах 
	 */
	public SubCommand(int unique_index,String command_name,byte[] parameter){
		this(command_name,parameter);
		this.setUniqueIndex(unique_index);
	}

	/**
	 * Минимальная единица, которая совершает действие на стороне клиента,
	 * и возвращает данные по действию на сторону сервера
	 * @param command_name имя команды
	 * @param field_parameter значение, которое нужно передать в параметрах
	 * @param flag_for - флаг, для кого предназначена данная команда - для общения с Reader-ом, либо же для общения с Интерфейсом пользователя 
	 */
	public SubCommand(String command_name,byte[] parameter,int flag_for){
		this.setCommand(command_name);
		this.setParameter(parameter);
		this.setCommandFor(flag_for);
	}
	/** 
	 * Минимальная единица, которая совершает действие на стороне клиента,
	 * и возвращает данные по действию на сторону сервера
	 * @param unique_index уникальный индекс в пакете ActionStep  
	 * @param command_name имя команды
	 * @param field_parameter значение, которое нужно передать в параметрах
	 * @param flag_for - флаг, для кого предназначена данная команда - для общения с Reader-ом, либо же для общения с Интерфейсом пользователя 
	 */
	public SubCommand(int unique_index,String command_name,byte[] parameter,int flag_for){
		this.setCommand(command_name);
		this.setParameter(parameter);
		this.setCommandFor(flag_for);
		this.setUniqueIndex(unique_index);
	}
	 

	/**
	 * Минимальная единица, которая совершает действие на стороне клиента,
	 * и возвращает данные по действию на сторону сервера
	 * @param command_name имя команды
	 * @param field_parameter_string текстовый параметр для команды
	 * @param flag_for - флаг, для кого предназначена данная команда - для общения с Reader-ом, либо же для общения с Интерфейсом пользователя 
	 */
	public SubCommand(String command_name,String parameter_string,int flag_for){
		this.setCommand(command_name);
		this.setParameterString(parameter_string);
		this.setCommandFor(flag_for);
	}

	/**
	 * Минимальная единица, которая совершает действие на стороне клиента,
	 * и возвращает данные по действию на сторону сервера
	 * @param unique_index уникальный индекс в пакте ActionStep  
	 * @param command_name имя команды
	 * @param field_parameter_string текстовый параметр для команды
	 * @param flag_for - флаг, для кого предназначена данная команда - для общения с Reader-ом, либо же для общения с Интерфейсом пользователя 
	 */
	public SubCommand(int unique_index,String command_name,String parameter_string,int flag_for){
		this.setCommand(command_name);
		this.setParameterString(parameter_string);
		this.setCommandFor(flag_for);
		this.setUniqueIndex(unique_index);
	}
	
	/**
	 * Минимальная единица, которая совершает действие на стороне клиента,
	 * и возвращает данные по действию на сторону сервера
	 * Конструктор, который подразумевает динамическую установку параметра  
	 * @param command_name имя команды
	 * @param flag_for - флаг, для кого предназначена данная команда - для общения с Reader-ом, либо же для общения с Интерфейсом пользователя 
	 */
	public SubCommand(String command_name,int flag_for){
		this.setCommand(command_name);
		this.setCommandFor(flag_for);
	}
	/**
	 * Минимальная единица, которая совершает действие на стороне клиента,
	 * и возвращает данные по действию на сторону сервера
	 * Конструктор, который подразумевает динамическую установку параметра
	 * @param unique_index уникальный индекс в пакте ActionStep  
	 * @param command_name имя команды
	 * @param flag_for - флаг, для кого предназначена данная команда - для общения с Reader-ом, либо же для общения с Интерфейсом пользователя 
	 */
	public SubCommand(int unique_index,String command_name,int flag_for){
		this.setCommand(command_name);
		this.setCommandFor(flag_for);
		this.setUniqueIndex(unique_index);
	}
	
	/** Установить уникальный индекс в пакете Action*/
	public void setUniqueIndex(int value){
		this.field_unique_index=value;
	}
	/** Получить уникальный индекс в пакете Action*/
	public int getUniqueIndex(){
		return this.field_unique_index;
	}
	
	/** Установить Для чего предназначена данная команда (Reader или Display)*/
	public void setCommandFor(int value){
		this.field_command_for=value;
	}
	/** Получить для чего предназначена данная команда (Reader или Display)*/	
	public int getCommandFor(){
		return this.field_command_for;
	}
	
	/** установить параметр String*/
	public void setParameterString(String value){
		this.field_parameter_string=value;
	}
	/** получить параметр String */
	public String getParameterString(){
		return this.field_parameter_string;
	}
	
	/** установить параметр int*/
	public void setParameterInt(int value){
		this.field_parameter_int=value;
	}
	/** прочитать параметр int*/
	public int getParameterInt(){
		return this.field_parameter_int;
	}
	
	/** установить флаг для описания данных в пакете */
	public void setDataDescription(int value){
		this.field_data_description=value;
	}
	/** получить флаг для описания данных в пакете */
	public int getDataDescription(){
		return this.field_data_description;
	}
	
	/** установить параметр команды */
	public void setParameter(byte[] value){
		this.field_parameter=value;
	}
	/** получить параметр команды */
	public byte[] getParameter(){
		return this.field_parameter;
	}
	
	/** установить уникальное имя для команды */
	public void setCommand(String value){
		this.field_command=value;
	}
	
	/** получить уникальное имя для команды */
	public String getCommand(){
		return this.field_command;
	}

	/** получить кол-во записей в дополнительной информации */
	public int getInformationCount(){
		if(this.field_parameter_keys!=null){
			return this.field_parameter_keys.size();
		}else{
			return 0;
		}
	}
	
	/** получить все ключи */
	public String[] getInformationKeys(){
		if(this.field_parameter_keys!=null){
			return this.field_parameter_keys.toArray(new String[]{});
		}else{
			return new String[]{};
		}
	}
	/** получить все значения */
	public String[] getInformationValues(){
		if(this.field_parameter_values!=null){
			return this.field_parameter_values.toArray(new String[]{});
		}else{
			return new String[]{};
		}
	}
	
	/** получить ключ по порядковому номер*/
	public String getInformationKey(int index){
		if(this.field_parameter_keys!=null){
			if(index<this.field_parameter_keys.size()){
				return this.field_parameter_keys.get(index);
			}else{
				return "";
			}
		}else{
			return "";
		}
	}
	
	/** получить значение по порядковому номеру*/
	public String getInformationValue(int index){
		if(this.field_parameter_values!=null){
			if(index<this.field_parameter_values.size()){
				return this.field_parameter_values.get(index);
			}else{
				return "";
			}
		}else{
			return "";
		}
	}
	
	/** получить значение по имени ключа */
	public String getInformationValueByKey(String key){
		if(this.field_parameter_keys!=null){
			int index=this.field_parameter_keys.indexOf(key);
			if(index>=0){
				return this.field_parameter_values.get(index);
			}else{
				return "";
			}
		}else{
			return "";
		}
	}
	/** положить значение в хранилище информации */
	public void putInformation(String key, String value){
		// создать, если еще не созданы - не создаются для экономии трафика и места в хранилище при общении 
		if(this.field_parameter_keys==null){
			this.field_parameter_keys=new ArrayList<String>();
			this.field_parameter_values=new ArrayList<String>();
		};
		// просмотреть на наличие данного ключа
		int index=this.field_parameter_keys.indexOf(key);
		if(index>=0){
			// заменить
			this.field_parameter_values.set(index, value);
		}else{
			// положить как новый
			this.field_parameter_keys.add(key);
			this.field_parameter_values.add(value);
		}
	}
	
	/** очистить все значения */
	public void clearInforamtion(){
		if(this.field_parameter_keys!=null){
			this.field_parameter_keys.clear();
			this.field_parameter_values.clear();
		}
	}
}
