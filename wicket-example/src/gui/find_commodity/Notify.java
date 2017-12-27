package gui.find_commodity;

import org.apache.wicket.Component;

public interface Notify {
	/** ����������� ������ ��������� ������ ��������� ��������� � ������������� �������� ������������ ��������� ������ ������ ( �������� �/��� Ajax ����������) 
	 * @param - ��������� (������) ������� ������� ������ ������ 
	 * */
	public void notifySubmit(Component sender);
	/** ����������� ������ ��������� ������ ��������� ��������� � ������������� �������� ������������ ��������� �� ��������� ��������� 
	 * @param - ��������� (������) ������� ������� ������ ������ 
	 * */
	public void notifyError(Component sender);
}
