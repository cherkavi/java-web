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

/** объект по обработке входящих XML запросов */
public abstract class Engine {
	
	/** получить имя функции */
	protected abstract String getFunctionName(); 
	
	/** является ли данный документ предназначенным для обработки именно текущим обработчиком */
	protected boolean isCurrentDocument(String functionName, Document document){
		if(functionName==null){
			return false;
		}
		if(this.getFunctionName()==null){
			return false;
		}
		return functionName.trim().equalsIgnoreCase(this.getFunctionName().trim());
	}
	
	/** проверка на наличие всех параметров 
	 * @param document - входящий XML документ
	 * @throws EngineException - в случае, если необходимый (required) параметр не найден  
	 * */
	abstract protected void isAllParametersExists(Document document) throws EngineException;

	/** проверка на валидность всех параметров 
	 * @param document - входящий XML документ 
	 * */
	abstract protected void isAllParametersValid(Document document) throws EngineException;
	
	/** выполнить алгоритм 
	 * @param partnerKod - уникальный код партнера из базы данных 
	 * @param document - входящий XML документ
	 * @return исходящий XML документ 
	 * */
	protected abstract Document execute(int partnerKod, Document document) throws EngineException;
	
	/** обработать полученный XML 
	 * @param partnerKod - код партнера 
	 * @param functionName - имя запрашиваемой функции
	 * @param document - полученный документ от пользователя
	 * @return 
	 * <ul>
	 * 	<li><b>String</b> - текст, который следует передать партнеру, запросившему выполнение </li>
	 * 	<li><b>null</b></li>
	 * </ul>
	 * @throws EngineException - ошибка обработки запроса ( то есть запрос именно для этого Engine, но не может быть обработан из-за ошибки )
	 */
	public Document process(int partnerKod, String functionName, Document document) throws EngineException{
		if(this.isCurrentDocument(functionName, document)){
			/** проверка на наличие всех параметров  */
			isAllParametersExists(document);
			/** проверка на валидность всех параметров  */
			isAllParametersValid(document);
			/** выполнить алгоритм  */
			return execute(partnerKod, document);
		}else{
			return null;
		}
	}
	
	/** 
	 * получить из String, содержащий вид XML файла объект Document
	 * @param value строка, содержащая XML текст
	 * @return null, если произошла ошибка парсинга, либо же сам XML в виде org.w3c.dom.Document
	 */
	public static Document getXmlFromString(String value){
		Document return_value=null;
		javax.xml.parsers.DocumentBuilderFactory document_builder_factory=javax.xml.parsers.DocumentBuilderFactory.newInstance();
        // установить непроверяемое порождение Parser-ов
        document_builder_factory.setValidating(false);
        try {
            // получение анализатора
            javax.xml.parsers.DocumentBuilder parser=document_builder_factory.newDocumentBuilder();
            // Parse источник
            return_value=parser.parse(new ByteArrayInputStream(value.getBytes("UTF-8")));
        }catch(Exception ex){
        	System.err.println("getXmlFromString from String exception: "+ex.getMessage());
        }
		return return_value;
	}
	
	/** преобразовать XML в строковое представление  */
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
	
	/** создать XML документ */
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
	
	
	/** получить текстовое содержимое указанного (XPath) Node */
	protected String getStringFromDocumentByPath(Document document, String path){
		Node node=this.getNodeFromDocument(document, path);
		if(node!=null){
			return node.getTextContent();
		}else{
			return null;
		}
	}
	
	private XPath xpath = XPathFactory.newInstance().newXPath();
	/** получить Node по указанному пути */
	protected Node getNodeFromDocument(Document document, String path){
		try {
			return (Node)xpath.evaluate(path, document, XPathConstants.NODE);
		} catch (XPathExpressionException e) {
			return null;
		}
	}

	/** получить NodeList по указанному Node */
	protected NodeList getNodeSetFromDocument(Document document, String path){
		try {
			return (NodeList)xpath.evaluate(path, document, XPathConstants.NODESET);
		} catch (XPathExpressionException e) {
			return null;
		}
	}

	/** проверить документ на наличие в нем переменной по указанному пути  
	 * @param document - входящий документ 
	 * @param path - полный XPath путь к искомому элементу
	 * @throws EngineException - исключение, которое будет выброшено, в случае не нахождения элемента 
	 */
	protected void checkParameter(Document document, String path) throws EngineException{
		if(this.getStringFromDocumentByPath(document, path)==null){
			throw new EngineException(405, path);
		}
	}
	
	/** проверить на наличие хотя бы одного параметра из списка  
	 * @param document - документ, который запрошен удаленных пользователем
	 * @param path - список XPath путей для идентификации
	 * @throws EngineException - исключение, которое было выброшено из-за не найденного ни одного пути
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
	 * проверить, является ли параметр пустым элементом ( то есть &ltname&gt&lt/name&gt или же &ltname/&gt
	 * @param document - XML документ
	 * @param path - XPath путь
	 * @return
	 */
	protected boolean isParameterEmpty(Document document, String path){
		String value=this.getStringFromDocumentByPath(document, path);
		if(value==null)return true;
		return value.trim().equals("");
	}
	
	
	/** проверить, является ли указанный параметр строкой  
	 * @param document - XML документ 
	 * @param path - XPath путь к файлу  
	 * */
	protected boolean isParameterAsString(Document document, String path){
		return this.getParameterAsString(document, path)!=null;
	}
	
	/** получить строковый параметр   
	 * @param document - XML документ 
	 * @param path - XPath путь к файлу
	 * @return 
	 * <ul>
	 * 	<li><b>String</b> - строковое значение</li>
	 * 	<li><b>null</b> - не найдено значение, либо пустое </li>
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
	 * проверить, является ли указанный параметр Integer значением
	 * @param document
	 * @param path
	 * @return
	 */
	protected boolean isParameterAsInteger(Document document, String path){
		return getParameterAsInteger(document, path)!=null;
	}

	/**
	 * получить Integer параметр
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
	 * проверить, является ли указанный параметр Float значением
	 * @param document
	 * @param path
	 * @return
	 */
	protected boolean isParameterAsFloat(Document document, String path){
		return this.getParameterAsFloat(document, path)!=null;
	}

	/**
	 * получить параметр в виде Float 
	 * @param document
	 * @param path
	 * @return
	 * <ul>
	 * 	<li><b>Float</b> - значение из указанного XML листа</li>
	 * 	<li><b>null</b> - значение Float не найдено </li>
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
	 * является ли указанный параметр датой "dd.MM.yyyy"
	 * @param document - XML документ 
	 * @param path - XPath путь 
	 * @return 
	 */
	protected boolean isParameterAsDate(Document document, String path){
		return this.getParameterAsDate(document, path)!=null;
	}

	/**
	 * получить дату "dd.MM.yyyy"
	 * @param document - XML документ 
	 * @param path - XPath путь 
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
	 * является ли указанный параметр датой "dd.MM.yyyy HH:mm:ss"
	 * @param document - XML документ 
	 * @param path - XPath путь 
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
	 * является ли указанный параметр датой "dd.MM.yyyy HH:mm:ss"
	 * @param document - XML документ 
	 * @param path - XPath путь 
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

	/** проверить тип параметра, переданного в XML документе 
	 * @param document - документ 
	 * @param path - полный XPath путь
	 * @param type - тип для сравнения 
	 * @param mayBeEmpty - может ли параметр быть пустым
	 * @param enumValues - допустимые строковые значения, которые может содержить указанный XML узел( лист, параметр )
	 * @return
	 * <ul>
	 * 	<li><b>true</b> - совпадает </li>
	 * 	<li><b>false</b>  - не совпадает </li>
	 * </ul>
	 */
	protected boolean isTypeValid(Document document, String path, EngineParameterType type, boolean mayBeEmpty, String ... enumValues){
		String value=this.getStringFromDocumentByPath(document, path);
		if(value==null)return false;
		value=value.trim();
		
		if(mayBeEmpty==true){
			// может быть пустым
			if(value.equals("")){
				// пустое
				return true;
			}else{
				// не пустое 
			}
		}else{
			// не может быть пустым
			if(value.equals("")){
				// пустое
				return false;
			}else{
				// не пустое
			}
		}
		// не пустое
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
			default: return false; // тип не опознан 
		}
	}
	
	/** 
	 * является ли параметр одним из зарегестрированных значений 
	 * @param document - XML документ 
	 * @param path - XPath путь 
	 * @param enumValues - допустимые значения для данной переменной 
	 * @return
	 * <ul>
	 * 	<li><b>true</b> - указанный путь содержит одно из допустимых значений </li>
	 * 	<li><b>false</b> - указанный путь не содержит допустимое значение </li>
	 * </ul>
	 */
	protected boolean isParameterAsEnum(Document document, String path, String[] enumValues) {
		return this.getParameterAsEnum(document, path, enumValues)!=null;
	}

	/**
	 * получить значение из документа на основании допустимого перечисления 
	 * @param document - XML
	 * @param path - XPath
	 * @param enumValues - допустимые значения
	 * @return
	 * <ul>
	 * 	<li><b>null</b> - значение не найдено </li>
	 * 	<li><b>String</b> - одно из допустимых значений </li>
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

	/** проверить тип параметра и выбросить исключение, если параметр не соответствует найденному
	 * @param document - XML 
	 * @param path - XPath
	 * @param type - {@link EngineParameterType} тип который должен быть сопоставим со строкой в параметре, на который указывает XPath 
	 * @param mayBeEmpty - может ли значение быть пустым
	 * @throws EngineException - выбрасывается 407 ошибка в случае не соотвтетствия параметра 
	 */
	protected void checkType(Document document, String path, EngineParameterType type, boolean mayBeEmpty, String ... enumValues) throws EngineException{
		if(this.isTypeValid(document, path, type, mayBeEmpty, enumValues)==false){
			throw new EngineException(407, path);
		}
	}

	/** проверить тип параметра ( если таковой существует ) и выбросить исключение, если параметр не соответствует найденному
	 * @param document - XML 
	 * @param path - XPath
	 * @param type - {@link EngineParameterType} тип который должен быть сопоставим со строкой в параметре, на который указывает XPath 
	 * @param mayBeEmpty - может ли значение быть пустым
	 * @throws EngineException - выбрасывается 407 ошибка в случае не соотвтетствия параметра 
	 */
	protected void checkTypeIfExists(Document document, String path, EngineParameterType type, boolean mayBeEmpty, String ... enumValues) throws EngineException{
		if(this.getParameterAsString(document, path)!=null){
			if(this.isTypeValid(document, path, type, mayBeEmpty, enumValues)==false){
				throw new EngineException(407, path);
			}
		}
	}
	
	/** создать XML документ идентифицирующий ошибку  */
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

	
	/** проверить код города на наличие в базе данных  */
	protected boolean isCityExists(ConnectWrap connector, Integer cityId){
		try{
			return connector.getConnection().createStatement().executeQuery("select * from a_city where id="+cityId).next();
		}catch(Exception ex){
			System.err.println("#isCityExists "+ex.getMessage());
			return false;
		}
	}
	
	/** создать элемент и добавить к родительскому элементу 
	 * @param document - документ, на основании которого создается элемент
	 * @param elementName - имя элемента
	 * @param parentElement - родительский элемент 
	 * @return 
	 * */
	protected Element createElementAndAddToParent(Document document, String elementName, Node parentElement) {
		Element element=document.createElement(elementName);
		parentElement.appendChild(element);
		return element;
	}

	/** создать элемент и добавить к родительскому элементу
	 * @param document - документ, на основании которого создается элемент
	 * @param elementName - имя элемента
	 * @param elementText - текст внутри элемента
	 * @param parentElement - родительский элемент 
	 * @return 
	 */
	protected Element createElementWithTextAndAddToParent(Document document, String elementName, String elementText, Node parentElement) {
		Element element=document.createElement(elementName);
		parentElement.appendChild(element);
		return element;
	}
	
}
