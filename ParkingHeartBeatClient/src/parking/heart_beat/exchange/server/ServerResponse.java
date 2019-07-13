package parking.heart_beat.exchange.server;

public class ServerResponse {
	private ConfirmParameters confirmParameters=new ConfirmParameters();
	private Task task=new Task();
	
	/** ������, ������� ���� ������ � �������� ������� �� ������ */
	public ServerResponse(){
	}
	
	/** ������, ������� ���� ������ � �������� ������� �� ������ 
	 * @param confirmTask - ������������� � ���������� �������
	 * @param parameters - ������� ��������� 
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
