package bonpay.partner.database;
import java.sql.Connection;

public interface IConnector {
	/** �������� ���������� � ����� ������*/
	public Connection getConnection();
}
