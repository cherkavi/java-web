package utility;
import java.io.*;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import javax.xml.xpath.XPathExpression;
import org.w3c.dom.Document;

/** файл, который загружает переменные из указанных путей вида XPath из прочитанного XML файла*/
public class Loader {
	/** вывод ошибочной информации */
	private static void error(String information){
		System.out.print("Loader");
		System.out.print(" ERROR ");
		System.out.println(information);
	}
	
	private Document field_document;
	private XPath field_xpath;
	
	/** файл, который загружает переменные из указанных путей вида XPath из прочитанного XML файла*/
	public Loader(String path_to_xml) throws Exception {
		// чтение файла 
		File file=new File(path_to_xml);
		if(!file.exists()){
			throw new Exception();
		}
		// создание документа XML 
		this.field_document=getXmlByPath(path_to_xml);
		// создание объекта XPath
		XPathFactory factory=XPathFactory.newInstance();
		this.field_xpath=factory.newXPath();
	}
	
	/** получить XML файл из указанного абсолютного пути */
	private Document getXmlByPath(String path_to_xml) throws Exception{
        Document return_value=null;
		javax.xml.parsers.DocumentBuilderFactory document_builder_factory=javax.xml.parsers.DocumentBuilderFactory.newInstance();
        // установить непроверяемое порождение Parser-ов
        document_builder_factory.setValidating(false);
        try {
            // получение анализатора
            javax.xml.parsers.DocumentBuilder parser=document_builder_factory.newDocumentBuilder();
            return_value=parser.parse(new File(path_to_xml));
        }catch(Exception ex){
        	error("XmlExchange.java ERROR");
        	throw ex;
        }
		return return_value;
	}
	
	/** получить значение из XML файла согласно заданному XPath пути <br>
	 * @return возвращает либо пустую строку, либо значение 
	 * */
	public String getString(String path){
		String return_value="";
		try{
			XPathExpression expression=this.field_xpath.compile(path);
			return_value=(String) expression.evaluate(this.field_document,XPathConstants.STRING);
		}catch(Exception ex){
			error("getValue: Path:"+path+"   Exception:"+ex.getMessage());
		}
		return return_value;
	}

	/** получить значение из XML файла согласно заданному XPath пути <br>
	 * @return возвращает либо пустую строку, либо значение 
	 * */
	public String getString(String path, String default_value){
		String return_value=default_value;
		try{
			XPathExpression expression=this.field_xpath.compile(path);
			return_value=(String) expression.evaluate(this.field_document,XPathConstants.STRING);
		}catch(Exception ex){
			error("getValue: Path:"+path+"   Exception:"+ex.getMessage());
		}
		return return_value;
	}

	/** получить значение из XML файла согласно заданному XPath пути <br>
	 * @return возвращает либо default_value 
	 * */
	public int getInteger(String path,int default_value){
		int return_value=default_value;
		try{
			return_value=Integer.parseInt(getString(path));
		}catch(Exception ex){
			error("getInteger: Path:"+path+"   Exception:"+ex.getMessage());
		}
		return return_value;
	}
	
	public double getDouble(String path, double default_value){
		double return_value=default_value;
		try{
			return_value=Double.parseDouble(getString(path));
		}catch(Exception ex){
			error("getInteger: Path:"+path+"   Exception:"+ex.getMessage());
		}
		return return_value;
	}
}
