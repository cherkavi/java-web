package parking.heart_beat.exchange.client;

import parking.heart_beat.exchange.common.Param;

/** ���������, ������� ������ �������������� ����, ��� ��������� ������ ������/��������� ������, ��������� �������� */
public class ConfirmTask {
	private Param[] parameters=new Param[]{};


	/** ���������, ������� ������ �������������� ����, ��� ��������� ������ ������/��������� ������, ��������� �������� */
	public ConfirmTask(){
	}
	
	/** ���������, ������� ������ �������������� ����, ��� ��������� ������ ������/��������� ������, ��������� �������� */
	public ConfirmTask(Param[] parameters){
		this.parameters=parameters;
	}
	
	/** �������� ���-�� ���������� � ������� */
	public int getSize(){
		try{
			return this.parameters.length;
		}catch(NullPointerException ex){
			return 0;
		}
	}
	
	/** �������� �������� �� ��� ������ */
	public Param getParameterByIndex(int index){
		try{
			return this.parameters[index];
		}catch(IndexOutOfBoundsException ex){
			return null;
		}
	}
}
