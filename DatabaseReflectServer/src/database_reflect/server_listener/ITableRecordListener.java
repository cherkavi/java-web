package database_reflect.server_listener;

import database_reflect.marshalling.TransportObject;

/** интерфейс, который реализует функции по "общению" с удаленным клиентом*/
public interface ITableRecordListener {
	public String getRecord(TransportObject transport);
}
