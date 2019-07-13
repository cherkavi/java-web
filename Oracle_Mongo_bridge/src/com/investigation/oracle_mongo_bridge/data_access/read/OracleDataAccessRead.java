package com.investigation.oracle_mongo_bridge.data_access.read;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.investigation.oracle_mongo_bridge.data_access.DataElement;
import com.investigation.oracle_mongo_bridge.data_access.IDataAccessRead;
import com.investigation.oracle_mongo_bridge.exceptions.EDataAccess;

public class OracleDataAccessRead implements IDataAccessRead{
	private Connection connection;
	private Map<String, String> tableXmlLinks;
	
	public OracleDataAccessRead(String url,
								String login,
								String password,
								Map<String, String> tableXmlLinks) throws EDataAccess{
		this.connection=getConnection(url, login, password);
		this.tableXmlLinks=tableXmlLinks;
	}
	
	

	private Connection getConnection(String url, String login, String password) throws EDataAccess{
		try{
			Class.forName("oracle.jdbc.driver.OracleDriver");
			// Connection connection=DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:1521:XE","technik", "technik");
			// Connection connection=DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:1521:XE","system", "root");
			Connection connection=DriverManager.getConnection(url, login, password);
			connection.setAutoCommit(false);
			return connection;
		}catch(Exception ex){
			throw new EDataAccess("can't connect to DB:"+url, ex);
		}
	}
	
	@Override
	public Iterator<DataElement> readAllData(String tableName) throws EDataAccess{
		return this.readData(tableName, null, null);
	}

	@Override
	public DataElement readElementById(String tableName, long id) throws EDataAccess{
		ResultSet rs=null;
		try{
			String xmlColumnName=this.tableXmlLinks.get(tableName);
			DataElement returnValue=null;
			rs=this.connection.createStatement().executeQuery("select id, to_char(to_clob("+xmlColumnName+")) "+xmlColumnName+" from "+tableName+" where id="+id);
			while(rs.next()){
				returnValue=new DataElement(rs.getLong("id"), rs.getString(xmlColumnName));
			}
			return returnValue;
		}catch(Exception ex){
			System.err.println("readElementById Exception: "+ex.getMessage());
			throw new EDataAccess("DataAccess Exception:"+ex.getMessage(), ex);
		}finally{
			try{
				if(rs!=null)rs.getStatement().close();
			}catch(Exception ex){};
		}
	}



	@Override
	public void close() {
		try{
			this.connection.close();
		}catch(Exception ex){
		}
	}



	@Override
	public Iterator<DataElement> readData(String tableName, String xpath, String value) throws EDataAccess {
		// need to change for many data
		ResultSet rs=null;
		try{
			String xmlColumnName=this.tableXmlLinks.get(tableName);
			List<DataElement> returnValue=new ArrayList<DataElement>();
			StringBuilder query=new StringBuilder();
			query.append("select id, to_char(to_clob("+xmlColumnName+")) "+xmlColumnName+" from "+tableName);
			query.append("\n");
			if(xpath!=null){
				query.append("where extractvalue("+xmlColumnName+", '"+xpath+"') like '%"+value+"%'");
			}
			rs=this.connection.createStatement().executeQuery(query.toString());
			
			while(rs.next()){
				returnValue.add( new DataElement(rs.getLong("id"), rs.getString(xmlColumnName)) );
			}
			return returnValue.iterator();
		}catch(Exception ex){
			System.err.println("readAllData Exception: "+ex.getMessage());
			throw new EDataAccess("DataAccess Exception:"+ex.getMessage(), ex);
		}finally{
			try{
				if(rs!=null)rs.getStatement().close();
			}catch(Exception ex){};
		}
	}

}
