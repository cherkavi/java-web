package parking.heart_beat.client;

import java.util.ArrayList;

import parking.heart_beat.exchange.client.Parameters;
import parking.heart_beat.exchange.common.Param;
import parking.heart_beat.exchange.server.ConfirmParameters;

/** �����-�������� ��� ����������, ������� "����������" �� ������ */
public class ParametersContainer {
	/** ����� ��������� ��� ������� �� ������ */
	private ArrayList<Param> newParameters=new ArrayList<Param>();
	/** ���������, ������� ���� ������� �� ������, � �� ��� ��������� ������������� */
	private ArrayList<Param> sendedParameters=new ArrayList<Param>();
	/** �����-�������� ��� ����������, ������� "����������" �� ������ */
	public ParametersContainer(){
	}
	
	public void addParameter(Param param){
		this.newParameters.add(param);
	}
	
	
	/** ���������� ������������� �� ������� � �������� ���������� */
	public void processConfirmParameter(ConfirmParameters confirmParameter){
		// ������� ����� ���������, �� ������� ������ ������������� �� ������� 
		for(int counter=0;counter<confirmParameter.getSize();counter++){
			this.removeSendedParameterByName(confirmParameter.getParameterByIndex(counter).getName());
		}
		// �������� ���������, ������������� �� ������� �� ������ - ������� �� ��� ���
		if(this.sendedParameters.size()>0){
			for(int counter=0;counter<sendedParameters.size();counter++){
				newParameters.add(sendedParameters.get(counter));
			}
			sendedParameters.clear();
		}else{
			// ��� ������������� ��������, ���� �� �� ��� ��������� �� ���� ��������
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

	/** �������� ��������� ��� �������� */
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
