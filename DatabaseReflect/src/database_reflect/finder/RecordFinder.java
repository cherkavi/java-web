package database_reflect.finder;

import java.sql.Connection;

import java.sql.ResultSet;
import java.util.ArrayList;

import database_reflect.wrapper.TableWrap;


/** объект, который занимается поиском записей, которые необходимо передавать, или, другими словами, у которых есть возможность записи*/
public class RecordFinder {
	private ArrayList<TableWrap> tables=new ArrayList<TableWrap>();

	/** объект, который занимается поиском записей, которые необходимо передавать, или, другими словами, у которых есть возможность записи
	 * @param connectorAware - объект, который ведает соединением с базой данных
	 * @param table - таблицы, которые зарегестрированы в качестве сканирования
	 */
	public RecordFinder(TableWrap ... table){
		for(int counter=0;counter<table.length;counter++){
			this.tables.add(table[counter]);
		}
	}
	
	/** последний выданный объект */
	private Object lastObject=null;
	/** индекс по последнему взятому из таблицы значению */
	private int tableWrapIndex=(-1);
	
	/** последнюю выданную запись из таблицы пометить как переданную на сервер */
	public boolean lastRecordSetAsSended(Connection connection){
		return this.tables.get(tableWrapIndex).setObjectAsSended(connection, lastObject);
	}
	
	/** получить запись для передачи, у которой указанный контрольный столбец не равен нулю*/
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
	
	/** получить очередную запись для передачи из таблицы */
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
