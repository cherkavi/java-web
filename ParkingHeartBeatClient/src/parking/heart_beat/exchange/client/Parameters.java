package parking.heart_beat.exchange.client;

import parking.heart_beat.exchange.common.Param;

/** параметры, которые пришли от клиента */
public class Parameters {
	private Param[] parameters=new Param[]{};

	/** параметры, которые пришли от клиента */
	public Parameters(){
	}
	
	/** параметры, которые пришли от клиента 
	 * @param parameters - параметры от клиента 
	 */
	public Parameters(Param[] parameters){
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
