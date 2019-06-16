package database_reflect.server_listener;

/** интерфейс, который реализует функции по "общению" с удаленным клиентом*/
public interface ITableRecordListener {
	public String getRecord(String record);
}
