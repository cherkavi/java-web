package database_reflect.database;

import database_reflect.database.ConnectWrap;

/** �������, ����������� ������ ��������� ������� ����������� � ����� ������*/
public interface IConnectorAware {
	/** �������� ���������� � ����� ������ */
	public ConnectWrap getConnector();
}
