package database;

/** ������, ������� ��������� ������ ��������� "�������" ��� �������� �������� �� ������ {@link database.wrap.ConnectWrap}*/
public interface IConnectWrapAware {
	/** �������� �����-������� ��� ���������� � ����� ������ */
	public ConnectWrap getConnectWrap();
}
