package com.investigation.oracle_mongo_bridge.data_access;

abstract class AbstractDataAccess {

	protected String getRandomXmlRepresentation(){
		StringBuilder returnValue=new StringBuilder();
		returnValue.append("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>").append("\n");
		returnValue
			.append("<root>")
			.append("	<child_1> first </child_1>")
			.append("	<child_2> second </child_2>")
			.append("</root>")
			;
		return returnValue.toString();
	}
	
}
