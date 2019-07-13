package parking.heart_beat.exchange.server;

import parking.heart_beat.exchange.common.Param;

/** ������, ������� ����� �������� �� ������� � ������ ���� �� ���������� */
public class Task {
	private Param[] parameters=new Param[]{};


	/** ������, ������� ����� �������� �� ������� � ������ ���� �� ���������� */
	public Task(){
	}
	
	/** ������, ������� ����� �������� �� ������� � ������ ���� �� ���������� */
	public Task(Param[] parameters){
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
