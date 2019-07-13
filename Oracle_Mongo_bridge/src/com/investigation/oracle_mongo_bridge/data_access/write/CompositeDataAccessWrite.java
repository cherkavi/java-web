package com.investigation.oracle_mongo_bridge.data_access.write;

import java.util.Arrays;
import java.util.List;
import org.w3c.dom.Document;

import com.investigation.oracle_mongo_bridge.data_access.IDataAccessWrite;
import com.investigation.oracle_mongo_bridge.exceptions.EDataAccess;

public class CompositeDataAccessWrite implements IDataAccessWrite{
	private final List<IDataAccessWrite> list; 
	
	public CompositeDataAccessWrite(IDataAccessWrite ... elements){
		if(elements!=null && elements.length>0){
			list=Arrays.asList(elements);
		}else{
			throw new IllegalArgumentException("no such elements");
		}
	}
	
	@Override
	public void removeAll() throws EDataAccess {
		for(IDataAccessWrite value:list){
			value.removeAll();
		}
	}

	@Override
	public void addRecord(String tableName, 
						  long uniqueId,
						  Document xmlRepresentation) throws EDataAccess {
		for(IDataAccessWrite value:list){
			value.addRecord(tableName, uniqueId, xmlRepresentation);
		}
	}

	@Override
	public void addRecord(String tableName, long uniqueId,
			String xmlRepresentation) throws EDataAccess {
		for(IDataAccessWrite value:list){
			value.addRecord(tableName, uniqueId, xmlRepresentation);
		}
	}

	@Override
	public void close() {
		for(IDataAccessWrite value:list){
			value.close();
		}
	}

}
