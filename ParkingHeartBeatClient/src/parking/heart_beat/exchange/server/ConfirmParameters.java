package parking.heart_beat.exchange.server;

import parking.heart_beat.exchange.common.Param;

/** подтверждение параметров, полученных от клиентов */
public class ConfirmParameters {
	private Param[] parameters=new Param[]{};

	/** подтверждение параметров, полученных от клиентов */
	public ConfirmParameters(){
	}
	
	/** подтверждение параметров, полученных от клиентов  
	 * @param parameters - параметры от клиента 
	 */
	public ConfirmParameters(Param[] parameters){
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
