package parking.heart_beat.exchange.client;

import parking.heart_beat.exchange.common.Param;

/** ���������, ������� ������ �� ������� */
public class Parameters {
	private Param[] parameters=new Param[]{};

	/** ���������, ������� ������ �� ������� */
	public Parameters(){
	}
	
	/** ���������, ������� ������ �� ������� 
	 * @param parameters - ��������� �� ������� 
	 */
	public Parameters(Param[] parameters){
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
