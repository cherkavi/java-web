package parking.heart_beat.server;

import java.util.ArrayList;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

/** запрос, который "пришел" от клиента в виде списка параметров  */
public class ClientHttpRequest {
	private ArrayList<ClientHttpParameter> parameters=new ArrayList<ClientHttpParameter>();
	
	/** запрос, который "пришел" от клиента в виде списка параметров  */
	public ClientHttpRequest(){
	}
	
	/** запрос, который "пришел" от клиента в виде списка параметров  */
	public ClientHttpRequest(HttpServletRequest request){
		this.readFromRequest(request);
	}
	
	/** прочесть все параметры из Http запроса */
	public void readFromRequest(HttpServletRequest request){
		parameters.clear();
		Enumeration<?> names=request.getParameterNames();
		String key;
		String value;
		while(names.hasMoreElements()){
			key=(String)names.nextElement();
			value=request.getParameter(key);
			this.addParameter(new ClientHttpParameter(key, value));
		}
	}
	
	/** добавить параметр (запроса от клиента )к данному объекту */
	public void addParameter(ClientHttpParameter clientParameter){
		this.parameters.add(clientParameter);
	}

	/** получить кол-во параметров, которые были прочитаны */
	public int getParameterCount(){
		return this.parameters.size();
	}
	
	/** получить параметр по указанному номеру 
	 * @param index - индекс искомого параметра 
	 * @return 
	 * <li>объект</li>   
	 * <li>null - если индекс вне диапазона </li>   
	 */
	public ClientHttpParameter getParameter(int index){
		if((index<this.parameters.size())&&(index>=0)){
			return this.parameters.get(index);
		}else{
			return null;
		}
	}
	
	/** получить параметр из списка по имени */
	public ClientHttpParameter getParameterByName(String parameterName){
		ClientHttpParameter returnValue=null;
		for(int counter=0;counter<this.parameters.size();counter++){
			if(parameters.get(counter).getParamName().equals(parameterName)){
				returnValue=parameters.get(counter);
				break;
			}
		}
		return returnValue;
	}
}
