package com.investigation.oracle_mongo_bridge.data_access;

import java.util.Iterator;

import com.investigation.oracle_mongo_bridge.exceptions.EDataAccess;

public interface IDataAccessRead {
	public Iterator<DataElement> readAllData(String tableName) throws EDataAccess;
	public Iterator<DataElement> readData(String tableName, String xpath, String value) throws EDataAccess;
	public DataElement readElementById(String tableName, long id) throws EDataAccess;
	public void close();
}
