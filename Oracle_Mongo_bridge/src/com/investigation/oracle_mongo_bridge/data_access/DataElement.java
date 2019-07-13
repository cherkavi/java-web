package com.investigation.oracle_mongo_bridge.data_access;

public class DataElement {
	private long id;
	private String xmlDocument;
	
	public DataElement(){
	}

	public DataElement(long id, String xmlDocument){
		this.id=id;
		this.xmlDocument=xmlDocument;
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getXmlDocument() {
		return xmlDocument;
	}
	public void setXmlDocument(String xmlDocument) {
		this.xmlDocument = xmlDocument;
	}

	@Override
	public String toString() {
		return "DataElement [id=" + id + ", xmlDocument=" + xmlDocument + "]";
	}
	
	
}
