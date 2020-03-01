package reporter.query;

import java.io.Serializable;

/** ������ � ���� ������ �� ��������� ���������� ��� ������ */
public interface IQuery extends Serializable{
	/** ������ � ���� ������ �� ��������� ���������� ��� ������ */
	public String getQuery();
	/** ������ � ���� ������ �� ��������� ���-�� �������, ������� ����� �������� ResultSet.getInt(1) */
	public String getQuerySize();
}
