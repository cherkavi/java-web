package com.investigation.oracle_mongo_bridge.data_access.write;

import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.investigation.oracle_mongo_bridge.data_access.IDataAccessWrite;
import com.investigation.oracle_mongo_bridge.exceptions.EDataAccess;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.MongoException;

public class MongoDataAccessWrite implements IDataAccessWrite{
	/**  Mongo collection key for FK to JDBC.TableName.ID*/
	private static final String mongoCollectionKey="jdbc_id";
	private Mongo mongo;
	/** mongo database */
	private DB mongoDB;

	public MongoDataAccessWrite(String mongoHost,
						   int mongoPort,
						   String mongoDbName) throws UnknownHostException, MongoException{
		this.mongo=new Mongo(mongoHost, mongoPort);
		this.mongoDB=mongo.getDB(mongoDbName);
	}
	
	@Override
	public void removeAll() throws EDataAccess {
		this.mongoDB.dropDatabase();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addRecord(String tableName,
						  long uniqueId,
						  Document xmlDocument) throws EDataAccess {
		DBCollection collection=this.mongoDB.getCollection(tableName);
		this.mongoDB.requestStart();
		collection.insert(getDBObject(uniqueId, xmlDocument));
		this.mongoDB.requestDone();
	}

	private DBObject getDBObject(long uniqueId, Document xmlDocument) {
		Map<String, Object> map=new HashMap<String, Object>();
		map.put(MongoDataAccessWrite.mongoCollectionKey, uniqueId);
		Utility.parseXmlToMap(xmlDocument, map);
		return new BasicDBObject(map);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addRecord(String tableName, long uniqueId, String xmlRepresentation)
			throws EDataAccess {
		this.addRecord(tableName, uniqueId, IDataAccessWrite.Utility.convertStringToXml(xmlRepresentation));
	}

	@Override
	public void close() {
		this.mongo.close();
	}
	
	
	
	public static final class Utility{
		public final static String elementDelimiter="/";
		
		public static void parseXmlToMap(Document xmlDocument, 
												Map<String, Object> map) {
			NodeList nodeList=xmlDocument.getChildNodes();
			for(int index=0;index<nodeList.getLength();index++){
				Node rootNode=nodeList.item(index);
				if(rootNode instanceof Element){
					Element rootElement=(Element)rootNode;
					parseXmlToMap("/", rootElement, map);
					break;
				}
			}
		}
		
		private static void parseXmlToMap(String preambula, 
												Element rootElement, 
											    Map<String, Object> map) {
			String childPreambula=preambula+rootElement.getTagName()+elementDelimiter;
			if(hasChildElement(rootElement)){
				NodeList nodeList=rootElement.getChildNodes();
				Node nextNode=null;
				for(int index=0;index<nodeList.getLength();index++){
					nextNode=nodeList.item(index);
					if(nextNode instanceof Element){
						parseXmlToMap(childPreambula, (Element)nextNode, map);
					}
				}
			}else{
				map.put(preambula+rootElement.getTagName(), 
						StringUtils.trimToEmpty(rootElement.getTextContent()));
			}
		}
		
		private static boolean hasChildElement(Element element){
			if(element.hasChildNodes()){
				// check ChildNodes for Element
				NodeList list=element.getChildNodes();
				for(int index=0;index<list.getLength();index++){
					if(list.item(index) instanceof Element){
						return true;
					}
				}
				return false;
			}else{
				// no have any child elements 
				return false;
			}
		}
	}
}
