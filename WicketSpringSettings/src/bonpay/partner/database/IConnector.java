package bonpay.partner.database;
import java.sql.Connection;

public interface IConnector {
	/** получить соединение с базой данных*/
	public Connection getConnection();
}
