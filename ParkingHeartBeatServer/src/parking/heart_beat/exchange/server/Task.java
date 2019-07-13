package parking.heart_beat.exchange.server;

import parking.heart_beat.exchange.common.Param;

/** задачи, которые будут переданы на клиента и должны быть им обработаны */
public class Task {
	private Param[] parameters=new Param[]{};


	/** задачи, которые будут переданы на клиента и должны быть им обработаны */
	public Task(){
	}
	
	/** задачи, которые будут переданы на клиента и должны быть им обработаны */
	public Task(Param[] parameters){
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
