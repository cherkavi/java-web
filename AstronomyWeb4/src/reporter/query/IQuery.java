package reporter.query;

import java.io.Serializable;

/** запрос к базе данных на получение информации для вывода */
public interface IQuery extends Serializable{
	/** запрос к базе данных на получение информации для вывода */
	public String getQuery();
	/** запрос к базе данных на получение кол-ва записей, которое можно получить ResultSet.getInt(1) */
	public String getQuerySize();
}
