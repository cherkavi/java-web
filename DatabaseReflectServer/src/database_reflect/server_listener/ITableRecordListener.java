package database_reflect.server_listener;

import database_reflect.marshalling.TransportObject;

/** ���������, ������� ��������� ������� �� "�������" � ��������� ��������*/
public interface ITableRecordListener {
	public String getRecord(TransportObject transport);
}
