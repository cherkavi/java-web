package com.investigation.oracle_mongo_bridge.data_access.write;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.w3c.dom.Document;

import com.investigation.oracle_mongo_bridge.data_access.IDataAccessWrite;
import com.investigation.oracle_mongo_bridge.data_access.ISequenceGenerator;
import com.investigation.oracle_mongo_bridge.exceptions.EDataAccess;

public class OracleDataAccessWrite implements IDataAccessWrite, ISequenceGenerator{
	private final Map<String, String> tableFieldLinks;
	private final Map<String, PreparedStatement> prepareStatements=new HashMap<String, PreparedStatement>();
	private final String sequenceName;
	private Connection connection;
	/**
	 * Access to Oracle Database 
	 * @param url - JDBC url
	 * @param login - JDBC login
	 * @param password - JDBC password
	 * @param tableFieldLinks - pair of DB.Table - DB.Table.ColumnName ( type XMLType )
	 */
	public OracleDataAccessWrite(String url,
							String login,
							String password,
							String sequenceName, 
							Map<String, String> tableFieldLinks) throws EDataAccess{
		this.connection=getConnection(url, login, password);
		this.tableFieldLinks=tableFieldLinks;
		this.sequenceName=sequenceName;
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
	public void removeAll() throws EDataAccess{
		Iterator<String> tableNames=this.tableFieldLinks.keySet().iterator();
		while(tableNames.hasNext()){
			String tableName=tableNames.next();
			Statement statement=null;
			try {
				statement = this.connection.createStatement();
				statement.executeUpdate("delete from "+tableName);
				this.connection.commit();
			} catch (SQLException e) {
				throw new EDataAccess("can't remove data from table: "+tableName, e);
			}finally{
				try{
					statement.close();
				}catch(Exception ex){};
			}
		}
	}

	@Override
	public void addRecord(String tableName,
						 long uniqueId,
						 Document xmlDocument) throws EDataAccess{
		addRecord(tableName, uniqueId, IDataAccessWrite.Utility.convertXmlToString(xmlDocument));
	}
	
	@Override
	public void addRecord(String tableName,
						  long uniqueId,
						  String xmlRepresentation) throws EDataAccess {
		try{
			String idColumnName="id";
			String xmlColumnName=this.tableFieldLinks.get(tableName);
			PreparedStatement statement=prepareStatements.get(tableName);
			if(statement==null){
				statement=connection
						.prepareStatement("insert into "+tableName
										  +" ("+idColumnName+", "+xmlColumnName
										  +") values (? , ?)");
				prepareStatements.put(tableName, statement);
			}
			statement.setLong(1, uniqueId);
			// statement.setObject(2, oracle.xdb.XMLType.createXML(connection, convertStringToXml(xmlDocumentAsString)));
			statement.setString(2, xmlRepresentation);
			statement.executeUpdate();
			connection.commit();
		}catch(SQLException ex){
			throw new EDataAccess("addRecord Exception: "+ex.getMessage(), ex);
		}
	}
	
	
	@Override
	public void close() {
		try{
			connection.close();
		}catch(Exception ex){};
		
	}

	@Override
	public long getNextId() throws EDataAccess {
		// System.out.println("getSequenceValue");
		ResultSet rs=null;
		try{
			rs=connection.createStatement().executeQuery("select "+sequenceName+".nextval from dual");
			if(!rs.next()){
				throw new IllegalArgumentException("Sequence does not found: "+this.sequenceName);
			}
			return rs.getLong(1);
		}catch(Exception ex){
			throw new EDataAccess("get NextId Exception: "+ex.getMessage(), ex);
		}finally{
			try{
				rs.close();
			}catch(Exception ex){};
			try{
				rs.getStatement().close();
			}catch(Exception ex){};
		}
	}


}
