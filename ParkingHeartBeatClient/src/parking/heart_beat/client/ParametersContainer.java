package parking.heart_beat.client;

import java.util.ArrayList;

import parking.heart_beat.exchange.client.Parameters;
import parking.heart_beat.exchange.common.Param;
import parking.heart_beat.exchange.server.ConfirmParameters;

/** класс-конейнер для параметров, которые "сгружаются" на сервер */
public class ParametersContainer {
	/** новые параметры для посылки на сервер */
	private ArrayList<Param> newParameters=new ArrayList<Param>();
	/** параметры, которые были посланы на сервер, и по ним ожидаются подтверждения */
	private ArrayList<Param> sendedParameters=new ArrayList<Param>();
	/** класс-конейнер для параметров, которые "сгружаются" на сервер */
	public ParametersContainer(){
	}
	
	public void addParameter(Param param){
		this.newParameters.add(param);
	}
	
	
	/** обработать подтверждение от сервера о принятых параметрах */
	public void processConfirmParameter(ConfirmParameters confirmParameter){
		// удалить такие параметры, на которые пришло подтверждение от сервера 
		for(int counter=0;counter<confirmParameter.getSize();counter++){
			this.removeSendedParameterByName(confirmParameter.getParameterByIndex(counter).getName());
		}
		// остались параметры, подтверждение на которые не пришло - послать их еще раз
		if(this.sendedParameters.size()>0){
			for(int counter=0;counter<sendedParameters.size();counter++){
				newParameters.add(sendedParameters.get(counter));
			}
			sendedParameters.clear();
		}else{
			// все подтверждения получены, либо же не был отправлен ни один параметр
		}
	}
	
	private void removeSendedParameterByName(String name){
		int counter=0;
		while(counter<this.sendedParameters.size()){
			if(sendedParameters.get(counter).getName().equals(name)){
				this.sendedParameters.remove(counter);
			}else{
				counter++;	
			}
		}
	}

	/** получить параметры для отправки */
	public Parameters getParametersForSend() {
		Param[] params=this.newParameters.toArray(new Param[]{});
		Parameters returnValue=new Parameters(params);
		for(int counter=0;counter<newParameters.size();counter++){
			sendedParameters.add(newParameters.get(counter));
		}
		newParameters.clear();
		return returnValue;
	}
	
}
