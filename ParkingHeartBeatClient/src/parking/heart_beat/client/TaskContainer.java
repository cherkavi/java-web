package parking.heart_beat.client;

import java.util.ArrayList;

import parking.heart_beat.exchange.client.ConfirmTask;
import parking.heart_beat.exchange.common.Param;
import parking.heart_beat.exchange.server.Task;

/** �����-��������� ��� �����, ������� ��������� �� ������� */
public class TaskContainer {
	/** ����� ��������� ������� �������� �� ������� � ���� ����� */
	private ArrayList<Param> newParameters=new ArrayList<Param>();

	/** ������� ������� ������������� � ������ ���� ����� */
	public ConfirmTask getConfirmTaskForServer() {
		return new ConfirmTask(this.newParameters.toArray(new Param[]{}));
	}

	/** ���������� ���������� ������ �� ������� */
	public void processServerTask(Task serverTask) {
		this.newParameters.clear();
		for(int counter=0;counter<serverTask.getSize();counter++){
			this.newParameters.add(serverTask.getParameterByIndex(counter));
			System.out.println("Task :"+counter+"   ParamName: "+serverTask.getParameterByIndex(counter).getName()+"   ParamValue: "+serverTask.getParameterByIndex(counter).getValue());
		}
		// INFO ����� ��� �������� ����������-����� � ������
		
	}
	
}
