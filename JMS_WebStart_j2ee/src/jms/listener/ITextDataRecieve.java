package jms.listener;

/** ��������� ������������� ��������� ������ */
public interface ITextDataRecieve {
	
	/**  �������� ��������� ��������� ��������� �� �������  
	 * @param remoteTextMessage - ��������� ��������� �� ���������� ������� 
	 * */
	public void recieveTextData(String remoteTextMessage);
}
