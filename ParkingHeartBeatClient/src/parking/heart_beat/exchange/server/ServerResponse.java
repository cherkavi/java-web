package parking.heart_beat.exchange.server;

public class ServerResponse {
	private ConfirmParameters confirmParameters=new ConfirmParameters();
	private Task task=new Task();
	
	/** объект, который шлет клиент в качестве запроса на сервер */
	public ServerResponse(){
	}
	
	/** объект, который шлет клиент в качестве запроса на сервер 
	 * @param confirmTask - подтверждение о полученных задачах
	 * @param parameters - текущие параметры 
	 */
	public ServerResponse(ConfirmParameters confirmParameters,Task task){
		this.confirmParameters=confirmParameters;
		this.task=task;
	}

	public ConfirmParameters getConfirmParameters() {
		return confirmParameters;
	}

	public void setConfirmParameters(ConfirmParameters confirmParameters) {
		this.confirmParameters = confirmParameters;
	}

	public Task getTask() {
		return task;
	}

	public void setTask(Task task) {
		this.task = task;
	}


}
