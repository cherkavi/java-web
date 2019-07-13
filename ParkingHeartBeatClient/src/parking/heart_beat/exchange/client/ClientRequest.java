package parking.heart_beat.exchange.client;

/** ������, ������� ���� ������ � �������� ������� �� ������ */
public class ClientRequest {
	private ConfirmTask confirmTask=new ConfirmTask();
	private Parameters parameters=new Parameters();
	
	/** ������, ������� ���� ������ � �������� ������� �� ������ */
	public ClientRequest(){
	}
	
	/** ������, ������� ���� ������ � �������� ������� �� ������ 
	 * @param confirmTask - ������������� � ���������� �������
	 * @param parameters - ������� ��������� 
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
