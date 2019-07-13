package parking.heart_beat.exchange.client;

import parking.heart_beat.exchange.common.Param;

/** параметры, которые служат подтверждением того, что удаленный модуль принял/обработал задачи, посланные сервером */
public class ConfirmTask {
	private Param[] parameters=new Param[]{};


	/** параметры, которые служат подтверждением того, что удаленный модуль принял/обработал задачи, посланные сервером */
	public ConfirmTask(){
	}
	
	/** параметры, которые служат подтверждением того, что удаленный модуль принял/обработал задачи, посланные сервером */
	public ConfirmTask(Param[] parameters){
		this.parameters=parameters;
	}
	
	/** получить кол-во параметров в объекте */
	public int getSize(){
		try{
			return this.parameters.length;
		}catch(NullPointerException ex){
			return 0;
		}
	}
	
	/** получить параметр по его номеру */
	public Param getParameterByIndex(int index){
		try{
			return this.parameters[index];
		}catch(IndexOutOfBoundsException ex){
			return null;
		}
	}
}
