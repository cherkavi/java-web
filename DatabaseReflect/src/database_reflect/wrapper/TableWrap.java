package database_reflect.wrapper;

import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.ResultSet;

/** обертка для таблицы в базе данных */
public abstract class TableWrap {
	// имя таблицы
	private String tableName=null;
	// контрольное поле, которое нужно проверять на отличное от нуля
	private String controlField=null;
	// поле, которое "говорит" о упорядочивании данных 
	private String uniqueId=null;

	
	public String getTableName(){
		return tableName;
	}
	
	public String getControlField(){
		return controlField;
	}
	
	public String getUniqueId(){
		return uniqueId;
	}
	
	/** обертка для таблицы в базе данных 
	 * @param tableName - имя таблицы, для которой создана обертка
	 * @param controlField - контрольное поле (если больше 0 - будет послано на сервер, и значение данного поля будет уменьшено )
	 * @param uniqueId - уникальный идентификатор поля 
	 */
	public TableWrap(String tableName, String controlField, String uniqueId){
		this.tableName=tableName;
		this.controlField=controlField;
		this.uniqueId=uniqueId;
	}

	/** получить запрос на выборку данных, для получения записи, которые еще не были переданы */
	public String getQuery(){
		String query=null;
		if((uniqueId!=null)&&(!uniqueId.equals(""))){
			query=" select * from "+tableName+" where "+controlField+">0"; 
		}else{
			query=" select * from "+tableName+" where "+controlField+">0 order by "+uniqueId; 
		}
		return query;
	}

	/** получить на основании ResultSet запись из таблицы */
	public abstract Object getObjectFromResultSet(ResultSet rs);

	/** полученный объект рассматривать как запись из данной таблицы, уменьшить у нее значение поля "controlField" */
	public boolean setObjectAsSended(Connection connection, Object lastObject) {
		boolean returnValue=false;
		try{
			Integer id=this.getIntegerFromObjectBean(lastObject, this.getUniqueId());
			// заблокировать таблицу и получить значение столбца в указанной записи
			String query=" select "+this.getControlField()+" from "+this.getTableName()+" where "+this.getUniqueId()+"="+id;
			ResultSet rs=connection.createStatement().executeQuery(query);
			if(rs.next()){
				int forSend=rs.getInt(1)-1;
				// update указанной записи 
				String queryUpdate="update "+this.getTableName()+" set for_send="+forSend+" where id="+id;
				connection.createStatement().executeUpdate(queryUpdate);
				rs.getStatement().close();
				// commit - Free Lock on table 
				connection.commit();
				returnValue=true;
			}else{
				rs.getStatement().close();
				// запись была удалена - очень маленькая вероятность произошедшего
				// rollback - Free Lock on table
				connection.rollback();
				returnValue=false;
			}
		}catch(Exception ex){
			System.err.println("WrapCartridgeModel Exception ex: "+ex.getMessage());
			returnValue=false;
		}
		return returnValue;
	}
	
	
	/** получить значение Integer из поля объекта через метод 
	 * @param object - объект, который содержит метод Get по имени поля
	 * @param fieldName - поле, которое есть в объекте и имеет одноименный метод Get 
	 * @return Integer - возвращаемое значение 
	 */
	protected Integer getIntegerFromObjectBean(Object object, String fieldName){
		String methodName=this.getStringGetterForMethodName(fieldName);
		try{
			Method method=object.getClass().getMethod(methodName);
			Object returnValue=method.invoke(object);
			return (Integer)returnValue;
		}catch(Exception ex){
			System.err.println("getIntegerFromObjectBean Exception: "+ex.getMessage());
			return null;
		}
	}
	
	/** получить на основании имени поля, название метода GET для получения значения 
	 * @param fieldName - имя поля
	 * @return возвращает имя метода GET (getXxxx);
	 */
	private String getStringGetterForMethodName(String fieldName){
		return "get"+(new Character(fieldName.charAt(0)).toString()).toUpperCase()+fieldName.substring(1);
	}
	
}
