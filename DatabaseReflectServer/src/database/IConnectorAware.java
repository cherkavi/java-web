package database;

import database.ConnectWrap;

/** �������, ����������� ������ ��������� ������� ����������� � ����� ������*/
public interface IConnectorAware {
	/** �������� ���������� � ����� ������ */
	public ConnectWrap getConnector();
}
