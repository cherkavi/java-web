package com.investigation.oracle_mongo_bridge;

import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import com.investigation.oracle_mongo_bridge.data_access.IDataAccessWrite;
import com.investigation.oracle_mongo_bridge.data_access.ISequenceGenerator;
import com.investigation.oracle_mongo_bridge.data_access.write.CompositeDataAccessWrite;
import com.investigation.oracle_mongo_bridge.data_access.write.MongoDataAccessWrite;
import com.investigation.oracle_mongo_bridge.data_access.write.OracleDataAccessWrite;
import com.investigation.oracle_mongo_bridge.exceptions.EDataAccess;
import com.mongodb.MongoException;

public class MockGenerator {
	public static void main(String ... args) throws UnknownHostException, MongoException, EDataAccess{
		System.out.println("begin");
		
		int count=1000;
		
		Map<String, String> tableFieldLinks=new HashMap<String, String>();
		tableFieldLinks.put("table_one", "payload");
		tableFieldLinks.put("table_two", "payload");
		IDataAccessWrite oracleDataAccess=new OracleDataAccessWrite("jdbc:oracle:thin:@127.0.0.1:1521:XE", 
																	"SYSTEM", 
																	"root", 
																	"main_sequence", 
																	tableFieldLinks);
		IDataAccessWrite mongoDataAccess=new MongoDataAccessWrite("127.0.0.1", 27017,"testDb");
		IDataAccessWrite compositeWriter=new CompositeDataAccessWrite(
				mongoDataAccess,
				oracleDataAccess
				);
		compositeWriter.removeAll();
		ISequenceGenerator idGenerator=(ISequenceGenerator)oracleDataAccess;
		for(int index=0; index<count; index++){
			if((index%50)==0){
				System.out.println("Done:   "+index+"/"+count);
			};
			compositeWriter.addRecord("table_one", idGenerator.getNextId(), getRandomXml());
		}
		System.out.println("-end-");
	}
	
	/**
	 *  @return
	 		<root>
				<child_1> first </child_1>
				<child_2> second </child_2>
			</root>
	 */
	protected static String getRandomXml(){
		StringBuilder returnValue=new StringBuilder();
		returnValue.append("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>").append("\n");
		
		returnValue.append("<root>");
		int elementCount=new Random().nextInt(10);
		for(int index=0;index<elementCount; index++){
			returnValue.append(getRandomXmlElement(index, 100));
		}
		returnValue.append("</root>");
		return returnValue.toString();
	}

	/**
	 * @return one element of XML <br />
	 * For Example:
	 * "	<child_1> value_99 </child_1>"
	 */
	private static String getRandomXmlElement(int index, int maxValue) {
		return "	<child_"+index+"> value_"+ new Random().nextInt(maxValue) +" </child_"+index+">";
	}
	
}
