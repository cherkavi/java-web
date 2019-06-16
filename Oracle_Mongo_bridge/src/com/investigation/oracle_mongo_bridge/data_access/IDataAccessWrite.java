package com.investigation.oracle_mongo_bridge.data_access;

import java.io.ByteArrayInputStream;
import java.io.StringWriter;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;

import com.investigation.oracle_mongo_bridge.exceptions.EDataAccess;

public interface IDataAccessWrite {
	public static class Utility{

		public static Document convertStringToXml(String xmlRepresentation){
			Document return_value=null;
			javax.xml.parsers.DocumentBuilderFactory document_builder_factory=javax.xml.parsers.DocumentBuilderFactory.newInstance();
	        document_builder_factory.setValidating(false);
	        try {
	            javax.xml.parsers.DocumentBuilder parser=document_builder_factory.newDocumentBuilder();
	            return_value=parser.parse(new ByteArrayInputStream(xmlRepresentation.getBytes("UTF-8")));
	        }catch(Exception ex){
	        	System.err.println("XmlExchange.java ERROR:"+ex.getMessage());
	        }
			return return_value;
		}
		
		public static String convertXmlToString(Document document){
	        Transformer transformer;
			try {
				transformer = TransformerFactory.newInstance().newTransformer();
				// transformer.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, "testing.dtd");
				transformer.setOutputProperty(OutputKeys.INDENT, "yes");
				transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
				StringWriter writer=new StringWriter();
				transformer.transform(new DOMSource(document),  new StreamResult(writer));
				return writer.toString();
			} catch (TransformerConfigurationException e) {
				e.printStackTrace();
			} catch (TransformerFactoryConfigurationError e) {
				e.printStackTrace();
			} catch (TransformerException e) {
				e.printStackTrace();
			}
			return null;
		}
		
	}
	/**
	 * remove all elements from database <br />
	 * totaly clear
	 * @throws if an Exception happened during execute sequential removing 
	 */
	void removeAll() throws EDataAccess;
	
	/**
	 * add one XML record to the certain table
	 * @param tableName - name of the table ( with XMLType column )
	 * @param uniqueId - unique id for the Table
	 * @param xmlRepresentation - xml data for add to the table
	 * @return id of the inserted record    
	 */
	void addRecord(String tableName,
				   long uniqueId, 
				   Document xmlRepresentation) throws EDataAccess;

	/**
	 * add one XML record to the certain table
	 * @param tableName - name of the table ( with XMLType column )
	 * @param uniqueId - unique id for the Table
	 * @param xmlRepresentation - xml representation as String 
	 * @return id of the inserted record    
	 */
	void addRecord(String tableName,
				   long uniqueId,
				   String xmlRepresentation) throws EDataAccess;

	/**
	 * close connection ( connections ) to DataSource 
	 */
	void close();
}
