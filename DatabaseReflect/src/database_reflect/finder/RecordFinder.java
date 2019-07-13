package database_reflect.finder;

import java.sql.Connection;

import java.sql.ResultSet;
import java.util.ArrayList;

import database_reflect.wrapper.TableWrap;


/** ������, ������� ���������� ������� �������, ������� ���������� ����������, ���, ������� �������, � ������� ���� ����������� ������*/
public class RecordFinder {
	private ArrayList<TableWrap> tables=new ArrayList<TableWrap>();

	/** ������, ������� ���������� ������� �������, ������� ���������� ����������, ���, ������� �������, � ������� ���� ����������� ������
	 * @param connectorAware - ������, ������� ������ ����������� � ����� ������
	 * @param table - �������, ������� ���������������� � �������� ������������
	 */
	public RecordFinder(TableWrap ... table){
		for(int counter=0;counter<table.length;counter++){
			this.tables.add(table[counter]);
		}
	}
	
	/** ��������� �������� ������ */
	private Object lastObject=null;
	/** ������ �� ���������� ������� �� ������� �������� */
	private int tableWrapIndex=(-1);
	
	/** ��������� �������� ������ �� ������� �������� ��� ���������� �� ������ */
	public boolean lastRecordSetAsSended(Connection connection){
		return this.tables.get(tableWrapIndex).setObjectAsSended(connection, lastObject);
	}
	
	/** �������� ������ ��� ��������, � ������� ��������� ����������� ������� �� ����� ����*/
	public Object getNextFindRecord(Connection connection){
		lastObject=null;
		tableWrapIndex=(-1);
		Object returnValue=null;
		for(int counter=0;counter<this.tables.size();counter++){
			returnValue=this.gerRecordFromTable(connection, this.tables.get(counter));
			if(returnValue!=null){
				lastObject=returnValue;
				tableWrapIndex=counter;
				break;
			}
		};
		return returnValue;
	}
	
	/** �������� ��������� ������ ��� �������� �� ������� */
	private Object gerRecordFromTable(Connection connection, TableWrap tableWrap){
		Object returnValue=null;
		try{
			String query=tableWrap.getQuery();
			ResultSet rs=connection.createStatement().executeQuery(query);
			if(rs.next()){
				returnValue=tableWrap.getObjectFromResultSet(rs);
			}
			rs.getStatement().close();
		}catch(Exception ex){
			System.err.println("RecordFinder#getRecordFromTable: "+ex.getMessage());
		}finally{
		}
		return returnValue;
	}
}
