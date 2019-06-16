package com.investigation.oracle_mongo_bridge.data_access;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

import org.junit.Test;
import org.w3c.dom.Document;

import com.investigation.oracle_mongo_bridge.data_access.write.MongoDataAccessWrite;

public class TestXmlToMap {
	
	@Test
	public void testXmlToMap() throws ParserConfigurationException{
		Document document=createDocument();
		printDocument(document);
		Map<String, Object> map=new HashMap<String, Object>();
		MongoDataAccessWrite.Utility.parseXmlToMap(document, map);
		printMap(map);
	}
	
	
	private void printMap(Map<String, Object> map) {
		System.out.println("printMap begin");
		Iterator<String> iterator=map.keySet().iterator();
		while(iterator.hasNext()){
			String nextKey=iterator.next();
			System.out.println("Key:"+nextKey+"   Value:"+map.get(nextKey));
		}
		System.out.println("printMap end");
	}


	private void printDocument(Document document) {
		System.out.println(IDataAccessWrite.Utility.convertXmlToString(document));		
	}
	

	private Document createDocument() throws ParserConfigurationException {
		StringBuilder returnValue=new StringBuilder();
		returnValue.append("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>").append("\n");
		returnValue
			.append("<root>")
			.append("	<child_1> first </child_1>")
			.append("	<child_2> second </child_2>")
			.append("	<child_3> third </child_3>")
			.append("	<child_4> ")
			.append("	   <child_31> third1 </child_31>")
			.append("	   <child_32> third2 </child_32>")
			.append("	   <child_33> third3 </child_33>")
			.append("   </child_4>")
			.append("</root>")
			;
		return IDataAccessWrite.Utility.convertStringToXml(returnValue.toString());
	}

}
