package parking.heart_beat.client;

import java.io.File;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

/** является настройкой для работы програмы */
public class Settings {
	/** задержка между посылками сигнала HeartBeat на сервер */
	private int sendDelay=10;
	/** задержка в виде ожидания соединения, в случае невозможности соединения с сервером */
	private int sendErrorDelay=30;
	/** полный путь к сервлету, с которым необходимо связываться */
	private String urlPath="http://localhost:8080/ParkingHeartBeatServer/Gate";
	/** уникальный идентификатор модуля */
	private String moduleId="0000";
	
	/** является настройкой для работы програмы */
	public Settings(){
	}
	
	/** является настройкой для работы програмы, прочесть из файла  */
	public Settings(String pathToFile){
		this();
		this.readFromFile(pathToFile);
	}

	public void readFromFile(String path){
        Document document=null;
		javax.xml.parsers.DocumentBuilderFactory document_builder_factory=javax.xml.parsers.DocumentBuilderFactory.newInstance();
        // установить непроверяемое порождение Parser-ов
        document_builder_factory.setValidating(false);
        try {
            // получение анализатора
            javax.xml.parsers.DocumentBuilder parser=document_builder_factory.newDocumentBuilder();
            document=parser.parse(new File(path));
            // read parameters 
            this.setDocument(document);
        }catch(Exception ex){
        	System.err.println("Settings#constructor read Exception:"+ex.getMessage());
        }
	}
	
	/** установить параметры на основании документа XML */
	public boolean setDocument(Document document){
		boolean returnValue=false;
		try{
			XPath xpath = XPathFactory.newInstance().newXPath();			
			try{
				this.sendDelay=Integer.parseInt(xpath.evaluate("//settings/delay/text()", document));
			}catch(Exception ex){}
			try{
				this.sendErrorDelay=Integer.parseInt(xpath.evaluate("//settings/delay_error/text()", document));
			}catch(Exception ex){}
			try{
				this.urlPath=xpath.evaluate("//settings/url_path/text()", document).trim();
			}catch(Exception ex){}

			try{
				this.moduleId=xpath.evaluate("//settings/module_id/text()", document).trim();
			}catch(Exception ex){}
		}catch(Exception ex){
			System.err.println("Settings#setDocument Exception: "+ex.getMessage());
		}
		return returnValue;
	}
	
	
	/** получить на основании данного объекта XML документ */
	public Document getDocument(){
		Document document=null;
		try{
			javax.xml.parsers.DocumentBuilderFactory document_builder_factory=javax.xml.parsers.DocumentBuilderFactory.newInstance();
			document_builder_factory.setValidating(false);
			//document_builder_factory.setIgnoringComments(true);
			document=document_builder_factory.newDocumentBuilder().newDocument();
			
			Element settings=document.createElement("settings");
			document.appendChild(settings);
			
			Element delay=document.createElement("delay");
			delay.setTextContent(Integer.toString(this.sendDelay));
			settings.appendChild(delay);
			
			Element delayError=document.createElement("delay_error");
			delayError.setTextContent(Integer.toString(this.sendErrorDelay));
			settings.appendChild(delayError);
			
			Element urlPath=document.createElement("url_path");
			urlPath.setTextContent(this.urlPath);
			settings.appendChild(urlPath);
			
			Element moduleId=document.createElement("module_id");
			moduleId.setTextContent(this.moduleId);
			settings.appendChild(moduleId);
			
		}catch(Exception ex){
			System.err.println("Settings#getDocument Exception: "+ex.getMessage());
		}
		
		return document;
	}
	
	/** сохранить файл на диске */
	public boolean saveToFile(String path){
		boolean returnValue=false;
		try{
			Document document=this.getDocument();
			javax.xml.transform.TransformerFactory transformer_factory = javax.xml.transform.TransformerFactory.newInstance();  
			javax.xml.transform.Transformer transformer = transformer_factory.newTransformer();  
			javax.xml.transform.dom.DOMSource dom_source = new javax.xml.transform.dom.DOMSource(document); // Pass in your document object here  
			java.io.FileWriter out=new java.io.FileWriter(path);
			//string_writer = new Packages.java.io.StringWriter();  
			javax.xml.transform.stream.StreamResult stream_result = new javax.xml.transform.stream.StreamResult(out);  
			transformer.transform(dom_source, stream_result);
			returnValue=true;
		}catch(Exception ex){
			System.err.println("Settings#saveToFile Exception: "+ex.getMessage());
			returnValue=false;
		}
		return returnValue;
	}
	
	public int getSendDelay() {
		return sendDelay;
	}

	public void setSendDelay(int sendDelay) {
		this.sendDelay = sendDelay;
	}

	public int getSendErrorDelay() {
		return sendErrorDelay;
	}

	public void setSendErrorDelay(int sendErrorDelay) {
		this.sendErrorDelay = sendErrorDelay;
	}

	public String getUrlPath() {
		return urlPath;
	}

	public void setUrlPath(String urlPath) {
		this.urlPath = urlPath;
	}

	public String getModuleId() {
		return moduleId;
	}

	public void setModuleId(String moduleId) {
		this.moduleId = moduleId;
	}
	
}
