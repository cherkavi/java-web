package xml_robot.engine;

import java.io.ByteArrayInputStream;

import java.io.StringWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import database.ConnectWrap;

import xml_robot.exception.EngineException;

/** ������ �� ��������� �������� XML �������� */
public abstract class Engine {
	
	/** �������� ��� ������� */
	protected abstract String getFunctionName(); 
	
	/** �������� �� ������ �������� ��������������� ��� ��������� ������ ������� ������������ */
	protected boolean isCurrentDocument(String functionName, Document document){
		if(functionName==null){
			return false;
		}
		if(this.getFunctionName()==null){
			return false;
		}
		return functionName.trim().equalsIgnoreCase(this.getFunctionName().trim());
	}
	
	/** �������� �� ������� ���� ���������� 
	 * @param document - �������� XML ��������
	 * @throws EngineException - � ������, ���� ����������� (required) �������� �� ������  
	 * */
	abstract protected void isAllParametersExists(Document document) throws EngineException;

	/** �������� �� ���������� ���� ���������� 
	 * @param document - �������� XML �������� 
	 * */
	abstract protected void isAllParametersValid(Document document) throws EngineException;
	
	/** ��������� �������� 
	 * @param partnerKod - ���������� ��� �������� �� ���� ������ 
	 * @param document - �������� XML ��������
	 * @return ��������� XML �������� 
	 * */
	protected abstract Document execute(int partnerKod, Document document) throws EngineException;
	
	/** ���������� ���������� XML 
	 * @param partnerKod - ��� �������� 
	 * @param functionName - ��� ������������� �������
	 * @param document - ���������� �������� �� ������������
	 * @return 
	 * <ul>
	 * 	<li><b>String</b> - �����, ������� ������� �������� ��������, ������������ ���������� </li>
	 * 	<li><b>null</b></li>
	 * </ul>
	 * @throws EngineException - ������ ��������� ������� ( �� ���� ������ ������ ��� ����� Engine, �� �� ����� ���� ��������� ��-�� ������ )
	 */
	public Document process(int partnerKod, String functionName, Document document) throws EngineException{
		if(this.isCurrentDocument(functionName, document)){
			/** �������� �� ������� ���� ����������  */
			isAllParametersExists(document);
			/** �������� �� ���������� ���� ����������  */
			isAllParametersValid(document);
			/** ��������� ��������  */
			return execute(partnerKod, document);
		}else{
			return null;
		}
	}
	
	/** 
	 * �������� �� String, ���������� ��� XML ����� ������ Document
	 * @param value ������, ���������� XML �����
	 * @return null, ���� ��������� ������ ��������, ���� �� ��� XML � ���� org.w3c.dom.Document
	 */
	public static Document getXmlFromString(String value){
		Document return_value=null;
		javax.xml.parsers.DocumentBuilderFactory document_builder_factory=javax.xml.parsers.DocumentBuilderFactory.newInstance();
        // ���������� ������������� ���������� Parser-��
        document_builder_factory.setValidating(false);
        try {
            // ��������� �����������
            javax.xml.parsers.DocumentBuilder parser=document_builder_factory.newDocumentBuilder();
            // Parse ��������
            return_value=parser.parse(new ByteArrayInputStream(value.getBytes("UTF-8")));
        }catch(Exception ex){
        	System.err.println("getXmlFromString from String exception: "+ex.getMessage());
        }
		return return_value;
	}
	
	/** ������������� XML � ��������� �������������  */
	public static String getStringFromXmlDocument(Document document){
		Writer out=null;
		try{
			javax.xml.transform.TransformerFactory transformer_factory = javax.xml.transform.TransformerFactory.newInstance();  
			javax.xml.transform.Transformer transformer = transformer_factory.newTransformer();  
			javax.xml.transform.dom.DOMSource dom_source = new javax.xml.transform.dom.DOMSource(document); // Pass in your document object here  
			out=new StringWriter();
			//string_writer = new Packages.java.io.StringWriter();  
			javax.xml.transform.stream.StreamResult stream_result = new javax.xml.transform.stream.StreamResult(out);  
			transformer.transform(dom_source, stream_result);  
		}catch(Exception ex){
			System.err.println("getStringFromXmlDocument:"+ex.getMessage());
		}
		return (out==null)?"":out.toString();
	}
	
	/** ������� XML �������� */
	public static Document createDocument(){
		// create empty XML document
		javax.xml.parsers.DocumentBuilderFactory document_builder_factory=javax.xml.parsers.DocumentBuilderFactory.newInstance();
		document_builder_factory.setValidating(false);
		document_builder_factory.setIgnoringComments(true);
		javax.xml.parsers.DocumentBuilder document_builder;
		try {
			document_builder = document_builder_factory.newDocumentBuilder();
			return document_builder.newDocument();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	/** �������� ��������� ���������� ���������� (XPath) Node */
	protected String getStringFromDocumentByPath(Document document, String path){
		Node node=this.getNodeFromDocument(document, path);
		if(node!=null){
			return node.getTextContent();
		}else{
			return null;
		}
	}
	
	private XPath xpath = XPathFactory.newInstance().newXPath();
	/** �������� Node �� ���������� ���� */
	protected Node getNodeFromDocument(Document document, String path){
		try {
			return (Node)xpath.evaluate(path, document, XPathConstants.NODE);
		} catch (XPathExpressionException e) {
			return null;
		}
	}

	/** �������� NodeList �� ���������� Node */
	protected NodeList getNodeSetFromDocument(Document document, String path){
		try {
			return (NodeList)xpath.evaluate(path, document, XPathConstants.NODESET);
		} catch (XPathExpressionException e) {
			return null;
		}
	}

	/** ��������� �������� �� ������� � ��� ���������� �� ���������� ����  
	 * @param document - �������� �������� 
	 * @param path - ������ XPath ���� � �������� ��������
	 * @throws EngineException - ����������, ������� ����� ���������, � ������ �� ���������� �������� 
	 */
	protected void checkParameter(Document document, String path) throws EngineException{
		if(this.getStringFromDocumentByPath(document, path)==null){
			throw new EngineException(405, path);
		}
	}
	
	/** ��������� �� ������� ���� �� ������ ��������� �� ������  
	 * @param document - ��������, ������� �������� ��������� �������������
	 * @param path - ������ XPath ����� ��� �������������
	 * @throws EngineException - ����������, ������� ���� ��������� ��-�� �� ���������� �� ������ ����
	 * */
	protected void checkParameterOneOfAll(Document document, String ... path) throws EngineException{
		if((path==null)||(path.length==0)){
			throw new EngineException(405,"nothing for change");
		}
		for(int counter=0;counter<path.length;counter++){
			if(this.getStringFromDocumentByPath(document, path[counter])!=null){
				return;
			}
		}
		throw new EngineException(405,"nothing for change");
	}

	/**
	 * ���������, �������� �� �������� ������ ��������� ( �� ���� &ltname&gt&lt/name&gt ��� �� &ltname/&gt
	 * @param document - XML ��������
	 * @param path - XPath ����
	 * @return
	 */
	protected boolean isParameterEmpty(Document document, String path){
		String value=this.getStringFromDocumentByPath(document, path);
		if(value==null)return true;
		return value.trim().equals("");
	}
	
	
	/** ���������, �������� �� ��������� �������� �������  
	 * @param document - XML �������� 
	 * @param path - XPath ���� � �����  
	 * */
	protected boolean isParameterAsString(Document document, String path){
		return this.getParameterAsString(document, path)!=null;
	}
	
	/** �������� ��������� ��������   
	 * @param document - XML �������� 
	 * @param path - XPath ���� � �����
	 * @return 
	 * <ul>
	 * 	<li><b>String</b> - ��������� ��������</li>
	 * 	<li><b>null</b> - �� ������� ��������, ���� ������ </li>
	 * </ul>  
	 * */
	protected String getParameterAsString(Document document, String path){
		String value=this.getStringFromDocumentByPath(document, path);
		if((value!=null)&&(value.length()>0)){
			return value;
		}else{
			return null;
		}
	}
	
	/**
	 * ���������, �������� �� ��������� �������� Integer ���������
	 * @param document
	 * @param path
	 * @return
	 */
	protected boolean isParameterAsInteger(Document document, String path){
		return getParameterAsInteger(document, path)!=null;
	}

	/**
	 * �������� Integer ��������
	 * @param document
	 * @param path
	 * @return
	 */
	protected Integer getParameterAsInteger(Document document, String path){
		try{
			return Integer.parseInt(this.getStringFromDocumentByPath(document, path));
		}catch(Exception ex){
			return null;
		}
	}
	
	/**
	 * ���������, �������� �� ��������� �������� Float ���������
	 * @param document
	 * @param path
	 * @return
	 */
	protected boolean isParameterAsFloat(Document document, String path){
		return this.getParameterAsFloat(document, path)!=null;
	}

	/**
	 * �������� �������� � ���� Float 
	 * @param document
	 * @param path
	 * @return
	 * <ul>
	 * 	<li><b>Float</b> - �������� �� ���������� XML �����</li>
	 * 	<li><b>null</b> - �������� Float �� ������� </li>
	 * </ul>
	 */
	protected Float getParameterAsFloat(Document document, String path){
		try{
			String value=this.getStringFromDocumentByPath(document, path).replaceAll(",", ".");
			return Float.parseFloat(value);
		}catch(Exception ex){
			return null;
		}
	}
	
	private SimpleDateFormat sdfDate=new SimpleDateFormat("dd.MM.yyyy");
	/**
	 * �������� �� ��������� �������� ����� "dd.MM.yyyy"
	 * @param document - XML �������� 
	 * @param path - XPath ���� 
	 * @return 
	 */
	protected boolean isParameterAsDate(Document document, String path){
		return this.getParameterAsDate(document, path)!=null;
	}

	/**
	 * �������� ���� "dd.MM.yyyy"
	 * @param document - XML �������� 
	 * @param path - XPath ���� 
	 * @return 
	 * <ul>
	 * 	<li><b>true</b></li>
	 * 	<li><b>false</b></li>
	 * </ul>
	 */
	protected Date getParameterAsDate(Document document, String path){
		try{
			return sdfDate.parse(this.getStringFromDocumentByPath(document, path));
		}catch(Exception ex){
			return null;
		}
	}

	
	private SimpleDateFormat sdfTimeStamp=new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
	/**
	 * �������� �� ��������� �������� ����� "dd.MM.yyyy HH:mm:ss"
	 * @param document - XML �������� 
	 * @param path - XPath ���� 
	 * @return 
	 */
	protected boolean isParameterAsTimeStamp(Document document, String path){
		try{
			sdfTimeStamp.parse(this.getStringFromDocumentByPath(document, path));
			return true;
		}catch(Exception ex){
			return false;
		}
	}

	/**
	 * �������� �� ��������� �������� ����� "dd.MM.yyyy HH:mm:ss"
	 * @param document - XML �������� 
	 * @param path - XPath ���� 
	 * @return 
	 * 	<ul>
	 * 		<li><b>Date</b> - value from XML leaf</li>
	 * 		<li><b>null</b> - value does not found </li>
	 * 	</ul>
	 */
	protected Date getParameterAsTimeStamp(Document document, String path){
		try{
			return sdfTimeStamp.parse(this.getStringFromDocumentByPath(document, path));
		}catch(Exception ex){
			return null;
		}
	}

	/** ��������� ��� ���������, ����������� � XML ��������� 
	 * @param document - �������� 
	 * @param path - ������ XPath ����
	 * @param type - ��� ��� ��������� 
	 * @param mayBeEmpty - ����� �� �������� ���� ������
	 * @param enumValues - ���������� ��������� ��������, ������� ����� ��������� ��������� XML ����( ����, �������� )
	 * @return
	 * <ul>
	 * 	<li><b>true</b> - ��������� </li>
	 * 	<li><b>false</b>  - �� ��������� </li>
	 * </ul>
	 */
	protected boolean isTypeValid(Document document, String path, EngineParameterType type, boolean mayBeEmpty, String ... enumValues){
		String value=this.getStringFromDocumentByPath(document, path);
		if(value==null)return false;
		value=value.trim();
		
		if(mayBeEmpty==true){
			// ����� ���� ������
			if(value.equals("")){
				// ������
				return true;
			}else{
				// �� ������ 
			}
		}else{
			// �� ����� ���� ������
			if(value.equals("")){
				// ������
				return false;
			}else{
				// �� ������
			}
		}
		// �� ������
		switch(type){
			case INTEGER:{
				return this.isParameterAsInteger(document, path);
			}
			case FLOAT:{
				return this.isParameterAsFloat(document, path);
			}
			case VARCHAR:{
				return this.isParameterAsString(document, path);
			}
			case DATE:{
				return this.isParameterAsDate(document, path);
			}
			case TIMESTAMP:{
				return this.isParameterAsTimeStamp(document, path);
			}
			case ENUM:{
				return this.isParameterAsEnum(document, path, enumValues);
			}
			default: return false; // ��� �� ������� 
		}
	}
	
	/** 
	 * �������� �� �������� ����� �� ������������������ �������� 
	 * @param document - XML �������� 
	 * @param path - XPath ���� 
	 * @param enumValues - ���������� �������� ��� ������ ���������� 
	 * @return
	 * <ul>
	 * 	<li><b>true</b> - ��������� ���� �������� ���� �� ���������� �������� </li>
	 * 	<li><b>false</b> - ��������� ���� �� �������� ���������� �������� </li>
	 * </ul>
	 */
	protected boolean isParameterAsEnum(Document document, String path, String[] enumValues) {
		return this.getParameterAsEnum(document, path, enumValues)!=null;
	}

	/**
	 * �������� �������� �� ��������� �� ��������� ����������� ������������ 
	 * @param document - XML
	 * @param path - XPath
	 * @param enumValues - ���������� ��������
	 * @return
	 * <ul>
	 * 	<li><b>null</b> - �������� �� ������� </li>
	 * 	<li><b>String</b> - ���� �� ���������� �������� </li>
	 * </ul>
	 */
	protected String getParameterAsEnum(Document document, String path, String ... enumValues){
		String value=this.getStringFromDocumentByPath(document, path);
		if(value==null)return null;
		if((enumValues==null)||(enumValues.length==0)){
			return null;
		}
		String returnValue=null;
		for(int counter=0;counter<enumValues.length;counter++){
			if(enumValues[counter].equalsIgnoreCase(value)){
				returnValue=enumValues[counter];
				break;
			}
		}
		return returnValue;
	}

	/** ��������� ��� ��������� � ��������� ����������, ���� �������� �� ������������� ����������
	 * @param document - XML 
	 * @param path - XPath
	 * @param type - {@link EngineParameterType} ��� ������� ������ ���� ���������� �� ������� � ���������, �� ������� ��������� XPath 
	 * @param mayBeEmpty - ����� �� �������� ���� ������
	 * @throws EngineException - ������������� 407 ������ � ������ �� ������������� ��������� 
	 */
	protected void checkType(Document document, String path, EngineParameterType type, boolean mayBeEmpty, String ... enumValues) throws EngineException{
		if(this.isTypeValid(document, path, type, mayBeEmpty, enumValues)==false){
			throw new EngineException(407, path);
		}
	}

	/** ��������� ��� ��������� ( ���� ������� ���������� ) � ��������� ����������, ���� �������� �� ������������� ����������
	 * @param document - XML 
	 * @param path - XPath
	 * @param type - {@link EngineParameterType} ��� ������� ������ ���� ���������� �� ������� � ���������, �� ������� ��������� XPath 
	 * @param mayBeEmpty - ����� �� �������� ���� ������
	 * @throws EngineException - ������������� 407 ������ � ������ �� ������������� ��������� 
	 */
	protected void checkTypeIfExists(Document document, String path, EngineParameterType type, boolean mayBeEmpty, String ... enumValues) throws EngineException{
		if(this.getParameterAsString(document, path)!=null){
			if(this.isTypeValid(document, path, type, mayBeEmpty, enumValues)==false){
				throw new EngineException(407, path);
			}
		}
	}
	
	/** ������� XML �������� ���������������� ������  */
	public static Document getErrorXml(int kod, String text){
		Document destination_document=null;
		try{
			javax.xml.parsers.DocumentBuilderFactory document_builder_factory=javax.xml.parsers.DocumentBuilderFactory.newInstance();
			document_builder_factory.setValidating(false);
			document_builder_factory.setIgnoringComments(true);
			javax.xml.parsers.DocumentBuilder document_builder=document_builder_factory.newDocumentBuilder();
			destination_document=document_builder.newDocument();

			Element response=destination_document.createElement("RESPONSE");
			destination_document.appendChild(response);

			Element error=destination_document.createElement("ERROR");
			response.appendChild(error);

			Element kodElement=destination_document.createElement("KOD");
			error.appendChild(kodElement);
			kodElement.setTextContent(Integer.toString(kod));
			
			Element textElement=destination_document.createElement("TEXT");
			error.appendChild(textElement);
			textElement.setTextContent(text);
			
		}catch(Exception ex){
			System.err.println("#getErrorXml:"+ex.getMessage());
		}
		return destination_document;
	}

	
	/** ��������� ��� ������ �� ������� � ���� ������  */
	protected boolean isCityExists(ConnectWrap connector, Integer cityId){
		try{
			return connector.getConnection().createStatement().executeQuery("select * from a_city where id="+cityId).next();
		}catch(Exception ex){
			System.err.println("#isCityExists "+ex.getMessage());
			return false;
		}
	}
	
	/** ������� ������� � �������� � ������������� �������� 
	 * @param document - ��������, �� ��������� �������� ��������� �������
	 * @param elementName - ��� ��������
	 * @param parentElement - ������������ ������� 
	 * @return 
	 * */
	protected Element createElementAndAddToParent(Document document, String elementName, Node parentElement) {
		Element element=document.createElement(elementName);
		parentElement.appendChild(element);
		return element;
	}

	/** ������� ������� � �������� � ������������� ��������
	 * @param document - ��������, �� ��������� �������� ��������� �������
	 * @param elementName - ��� ��������
	 * @param elementText - ����� ������ ��������
	 * @param parentElement - ������������ ������� 
	 * @return 
	 */
	protected Element createElementWithTextAndAddToParent(Document document, String elementName, String elementText, Node parentElement) {
		Element element=document.createElement(elementName);
		parentElement.appendChild(element);
		return element;
	}
	
}
