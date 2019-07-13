package database_reflect.wrapper;

import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.ResultSet;

/** ������� ��� ������� � ���� ������ */
public abstract class TableWrap {
	// ��� �������
	private String tableName=null;
	// ����������� ����, ������� ����� ��������� �� �������� �� ����
	private String controlField=null;
	// ����, ������� "�������" � �������������� ������ 
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
	
	/** ������� ��� ������� � ���� ������ 
	 * @param tableName - ��� �������, ��� ������� ������� �������
	 * @param controlField - ����������� ���� (���� ������ 0 - ����� ������� �� ������, � �������� ������� ���� ����� ��������� )
	 * @param uniqueId - ���������� ������������� ���� 
	 */
	public TableWrap(String tableName, String controlField, String uniqueId){
		this.tableName=tableName;
		this.controlField=controlField;
		this.uniqueId=uniqueId;
	}

	/** �������� ������ �� ������� ������, ��� ��������� ������, ������� ��� �� ���� �������� */
	public String getQuery(){
		String query=null;
		if((uniqueId!=null)&&(!uniqueId.equals(""))){
			query=" select * from "+tableName+" where "+controlField+">0"; 
		}else{
			query=" select * from "+tableName+" where "+controlField+">0 order by "+uniqueId; 
		}
		return query;
	}

	/** �������� �� ��������� ResultSet ������ �� ������� */
	public abstract Object getObjectFromResultSet(ResultSet rs);

	/** ���������� ������ ������������� ��� ������ �� ������ �������, ��������� � ��� �������� ���� "controlField" */
	public boolean setObjectAsSended(Connection connection, Object lastObject) {
		boolean returnValue=false;
		try{
			Integer id=this.getIntegerFromObjectBean(lastObject, this.getUniqueId());
			// ������������� ������� � �������� �������� ������� � ��������� ������
			String query=" select "+this.getControlField()+" from "+this.getTableName()+" where "+this.getUniqueId()+"="+id;
			ResultSet rs=connection.createStatement().executeQuery(query);
			if(rs.next()){
				int forSend=rs.getInt(1)-1;
				// update ��������� ������ 
				String queryUpdate="update "+this.getTableName()+" set for_send="+forSend+" where id="+id;
				connection.createStatement().executeUpdate(queryUpdate);
				rs.getStatement().close();
				// commit - Free Lock on table 
				connection.commit();
				returnValue=true;
			}else{
				rs.getStatement().close();
				// ������ ���� ������� - ����� ��������� ����������� �������������
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
	
	
	/** �������� �������� Integer �� ���� ������� ����� ����� 
	 * @param object - ������, ������� �������� ����� Get �� ����� ����
	 * @param fieldName - ����, ������� ���� � ������� � ����� ����������� ����� Get 
	 * @return Integer - ������������ �������� 
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
	
	/** �������� �� ��������� ����� ����, �������� ������ GET ��� ��������� �������� 
	 * @param fieldName - ��� ����
	 * @return ���������� ��� ������ GET (getXxxx);
	 */
	private String getStringGetterForMethodName(String fieldName){
		return "get"+(new Character(fieldName.charAt(0)).toString()).toUpperCase()+fieldName.substring(1);
	}
	
}
