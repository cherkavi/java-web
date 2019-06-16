package parking.heart_beat.client;

import java.util.ArrayList;

import parking.heart_beat.exchange.client.ConfirmTask;
import parking.heart_beat.exchange.common.Param;
import parking.heart_beat.exchange.server.Task;

/** класс-контейнер для задач, которые поступают от сервера */
public class TaskContainer {
	/** новые параметры которые присланы от сервера в виде задач */
	private ArrayList<Param> newParameters=new ArrayList<Param>();

	/** выслать серверу подтверждение о приеме всех задач */
	public ConfirmTask getConfirmTaskForServer() {
		return new ConfirmTask(this.newParameters.toArray(new Param[]{}));
	}

	/** обработать полученные задачи от сервера */
	public void processServerTask(Task serverTask) {
		this.newParameters.clear();
		for(int counter=0;counter<serverTask.getSize();counter++){
			this.newParameters.add(serverTask.getParameterByIndex(counter));
			System.out.println("Task :"+counter+"   ParamName: "+serverTask.getParameterByIndex(counter).getName()+"   ParamValue: "+serverTask.getParameterByIndex(counter).getValue());
		}
		// INFO место для передачи параметров-задач в работу
		
	}
	
}
