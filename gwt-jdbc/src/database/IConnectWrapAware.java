package database;

/** объект, который реализует данный интерфейс "владеет" или является фабрикой по выдаче {@link database.wrap.ConnectWrap}*/
public interface IConnectWrapAware {
	/** получить класс-обертку для соединения с базой данных */
	public ConnectWrap getConnectWrap();
}
