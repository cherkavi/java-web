package com.investigation.oracle_mongo_bridge.data_access;

import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;

import org.junit.Test;

import com.investigation.oracle_mongo_bridge.data_access.read.MongoDataAccessRead;
import com.investigation.oracle_mongo_bridge.data_access.write.MongoDataAccessWrite;
import com.investigation.oracle_mongo_bridge.exceptions.EDataAccess;
import com.mongodb.MongoException;

public class TestMongoDataAccess extends AbstractDataAccess{

	@Test
	public void connectionTest() throws EDataAccess, UnknownHostException, MongoException{
		// write block
		Map<String, String> tableFieldLinks=new HashMap<String, String>();
		tableFieldLinks.put("table_one", "payload");
		tableFieldLinks.put("table_two", "payload");
		IDataAccessWrite dataAccess=new MongoDataAccessWrite("127.0.0.1", 27017,"testDb");
		long id=new Random().nextInt(100);
		dataAccess.addRecord("table_one",
							 id,
							 getRandomXmlRepresentation());
		// dataAccess.removeAll();
		dataAccess.close();
		
		// read block 
		IDataAccessRead readAccess=new MongoDataAccessRead("127.0.0.1", 27017,"testDb");
		DataElement elementFromMongo=readAccess.readElementById("table_one", id);
		System.out.println("Element from DataBase:"+elementFromMongo);

		// read block 2 
		System.out.println(">>> iterator: begin");
		// Iterator<DataElement> iterator=readAccess.readAllData("table_one"); 
		Iterator<DataElement> iterator=readAccess.readData("table_one", "/root/child_1", "fi");
		while(iterator.hasNext()){
			System.out.println(iterator.next());
		}
		System.out.println(">>> iterator: end");

		readAccess.close();
	}
	

}
