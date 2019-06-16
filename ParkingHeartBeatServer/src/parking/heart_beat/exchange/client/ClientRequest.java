package parking.heart_beat.exchange.client;

/** объект, который шлет клиент в качестве запроса на сервер */
public class ClientRequest {
	private ConfirmTask confirmTask=new ConfirmTask();
	private Parameters parameters=new Parameters();
	
	/** объект, который шлет клиент в качестве запроса на сервер */
	public ClientRequest(){
	}
	
	/** объект, который шлет клиент в качестве запроса на сервер 
	 * @param confirmTask - подтверждение о полученных задачах
	 * @param parameters - текущие параметры 
	 */
	public ClientRequest(ConfirmTask confirmTask,Parameters parameters){
		this.confirmTask=confirmTask;
		this.parameters=parameters;
	}

	public ConfirmTask getConfirmTask() {
		return confirmTask;
	}

	public void setConfirmTask(ConfirmTask confirmTask) {
		this.confirmTask = confirmTask;
	}

	public Parameters getParameters() {
		return parameters;
	}

	public void setParameters(Parameters parameters) {
		this.parameters = parameters;
	}
	
	
}
