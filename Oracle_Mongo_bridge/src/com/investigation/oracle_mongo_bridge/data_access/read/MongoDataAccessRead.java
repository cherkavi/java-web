package com.investigation.oracle_mongo_bridge.data_access.read;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.investigation.oracle_mongo_bridge.data_access.DataElement;
import com.investigation.oracle_mongo_bridge.data_access.IDataAccessRead;
import com.investigation.oracle_mongo_bridge.exceptions.EDataAccess;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.MongoException;

public class MongoDataAccessRead implements IDataAccessRead{
	/**  Mongo collection key for FK to JDBC.TableName.ID*/
	private static final String mongoCollectionKey="jdbc_id";
	/** Mongo server */
	private Mongo mongo;
	/** mongo database */
	private DB mongoDB;

	public MongoDataAccessRead(String mongoHost,
						   	   int mongoPort,
						   	   String mongoDbName) throws UnknownHostException, MongoException{
		this.mongo=new Mongo(mongoHost, mongoPort);
		this.mongoDB=mongo.getDB(mongoDbName);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Iterator<DataElement> readAllData(String tableName) throws EDataAccess {
		DBCollection collection=this.mongoDB.getCollection(tableName);
		DBCursor cursor=collection.find();
		// need to change for many objects
		List<DataElement> returnValue=new ArrayList<DataElement>();
		while(cursor.hasNext()){
			DBObject nextObject=cursor.next();
			returnValue.add(new DataElement((Long)nextObject.get(mongoCollectionKey), convertMapToString(nextObject.toMap())));
		}
		cursor.close();
		return returnValue.iterator();
	}

	@SuppressWarnings("unchecked")
	@Override
	public DataElement readElementById(String tableName, long id) throws EDataAccess {
		DBCollection collection=this.mongoDB.getCollection(tableName);
		
		// DBCursor cursor=this.mongoCollection.find(new BasicDBObject(propertyName,".*"+condition+".*"));
		// DBCursor cursor=this.mongoCollection.find(new BasicDBObject(propertyName, condition));
		// Map<String, String> regmap=new HashMap<String, String>();
		// regmap.put("$regex", value);
		
		DBObject object=new BasicDBObject(mongoCollectionKey, id);
		DBCursor cursor=collection.find(object);
		// need to change for many objects
		DataElement returnValue=null;
		while(cursor.hasNext()){
			DBObject nextObject=cursor.next();
			returnValue=new DataElement((Long)nextObject.get(mongoCollectionKey), convertMapToString(nextObject.toMap()));
		}
		cursor.close();
		return returnValue;
	}

	@Override
	public void close() {
		try{
			this.mongo.close();
		}catch(Exception ex){
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public Iterator<DataElement> readData(String tableName, String xpath, String value) throws EDataAccess {
		DBCollection collection=this.mongoDB.getCollection(tableName);
		DBObject query=new BasicDBObject(xpath, 
										 new BasicDBObject("$regex",".*"+value+".*"));		
		DBCursor cursor=collection.find(query);
		// need to change for many objects
		List<DataElement> returnValue=new ArrayList<DataElement>();
		while(cursor.hasNext()){
			DBObject nextObject=cursor.next();
			returnValue.add(new DataElement((Long)nextObject.get(mongoCollectionKey), convertMapToString(nextObject.toMap()) ));
		}
		cursor.close();
		return returnValue.iterator();
	}
	
	private String convertMapToString(Map<String,?> map){
		StringBuilder returnValue=new StringBuilder();
		Iterator<String> keys=map.keySet().iterator();
		while(keys.hasNext()){
			String key=keys.next();
			Object value=map.get(key);
			returnValue.append("    ");
			returnValue.append("Key:").append(key);
			returnValue.append("   Value:").append(value);
			returnValue.append("\n");
		}
		return returnValue.toString();
	}

}
