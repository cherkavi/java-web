package com.investigation.oracle_mongo_bridge.data_access;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.junit.Test;

import com.investigation.oracle_mongo_bridge.data_access.read.OracleDataAccessRead;
import com.investigation.oracle_mongo_bridge.data_access.write.OracleDataAccessWrite;
import com.investigation.oracle_mongo_bridge.exceptions.EDataAccess;

public class TestOracleDataAccess extends AbstractDataAccess{

	@Test
	public void connectionTest() throws EDataAccess{
		// write part 
		Map<String, String> tableFieldLinks=new HashMap<String, String>();
		tableFieldLinks.put("table_one", "payload");
		tableFieldLinks.put("table_two", "payload");
		String sequenceName="main_sequence";
		IDataAccessWrite dataAccess=new OracleDataAccessWrite("jdbc:oracle:thin:@127.0.0.1:1521:XE", 
													"SYSTEM", 
													"root", 
													sequenceName, 
													tableFieldLinks);
		ISequenceGenerator idGenerator=(ISequenceGenerator)dataAccess;
		long id=idGenerator.getNextId();
		dataAccess.addRecord("table_one",
							 id,
							 getRandomXmlRepresentation());
		// dataAccess.removeAll();
		dataAccess.close();
		
		// read part 
		IDataAccessRead dataRead=new OracleDataAccessRead("jdbc:oracle:thin:@127.0.0.1:1521:XE", 
														  "SYSTEM", 
														  "root", 
														  tableFieldLinks);
		DataElement dataElement=dataRead.readElementById("table_one", 
								 						 id);
		System.out.println("Id:"+id+"    DataElement:"+dataElement);
		
		// read part 2
		System.out.println(">>> iterator: begin");
		Iterator<DataElement> iterator=dataRead.readData("table_one", "/root/child_1", "first");
		while(iterator.hasNext()){
			System.out.println(iterator.next());
		}
		System.out.println(">>> iterator: end");
		
		dataRead.close();
		
		
		
	}
	
}
