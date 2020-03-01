package xml_robot;

import httpClient.HttpClient;
import httpClient.HttpParameter;
import httpClient.HttpResult;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import xml_robot.engine.Engine;

public class TestRobot {
	
	public static void main(String[] args){
		System.out.println("begin");
		// сформировать запрос
		// Document request=createUser("user name","user surname", new Date(),1);
		//Document request=changeUser(83,"another name",null,null,null,null);
		Document request=getCityList();
		
		System.out.println("Request:\n"+Engine.getStringFromXmlDocument(request));
		// запрос на сервлет
		// создать класс для обмена данными  
		HttpClient client=new HttpClient("http://localhost:8080/AstronomyStub3/astro_robo",new HttpParameter("request",Engine.getStringFromXmlDocument(request)));
		// послать запрос на сервер
		HttpResult result=client.requestToServer("UTF-8");
		// вывод ответа от сервлета
		System.out.println("Server response: "+result.getResultText());
		System.out.println("-end-");
	}

	private static Document getCityList(){
		Document document=Engine.createDocument();
		
		Element REQUEST=createElementAndAppendToParent(document, "REQUEST", document, null);
			Element PARTNER=createElementAndAppendToParent(document, "PARTNER", REQUEST, null);
				createElementAndAppendToParent(document, "KOD", PARTNER, "111");
			createElementAndAppendToParent(document, "FUNCTION_NAME", REQUEST, "get_city_list");
			
		return document;
	}
	
	private static SimpleDateFormat sdf=new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
	
	private static Document changeUser(int userKod, String name, String surname, Date birthday, Integer city, String sex){
		Document document=Engine.createDocument();
		
		Element REQUEST=createElementAndAppendToParent(document, "REQUEST", document, null);
			Element PARTNER=createElementAndAppendToParent(document, "PARTNER", REQUEST, null);
				createElementAndAppendToParent(document, "KOD", PARTNER, "111");
			createElementAndAppendToParent(document, "FUNCTION_NAME", REQUEST, "change_user_settings");

			Element USER=createElementAndAppendToParent(document, "USER", REQUEST,null);
				createElementAndAppendToParent(document, "KOD", USER, Integer.toString(userKod));
			if(name!=null)
			createElementAndAppendToParent(document, "NAME", REQUEST, name);
			if(surname!=null)
			createElementAndAppendToParent(document, "SURNAME", REQUEST, surname);
			if(birthday!=null)
			createElementAndAppendToParent(document, "BIRTHDAY", REQUEST, sdf.format(birthday));
			if(city!=null){
				Element PLACE=createElementAndAppendToParent(document,"PLACE",REQUEST,null);
				createElementAndAppendToParent(document, "CITY_KOD", PLACE, Integer.toString(city));
			}
			if(sex!=null)
			createElementAndAppendToParent(document,"SEX",REQUEST,sex);
		return document;
	}
	
	
	private static Document createUser(String name, String surname, Date birthday, int city){
		Document document=Engine.createDocument();
		
		Element REQUEST=createElementAndAppendToParent(document, "REQUEST", document, null);
			Element PARTNER=createElementAndAppendToParent(document, "PARTNER", REQUEST, null);
				createElementAndAppendToParent(document, "KOD", PARTNER, "111");
			createElementAndAppendToParent(document, "FUNCTION_NAME", REQUEST, "registration");
			createElementAndAppendToParent(document, "NAME", REQUEST, name);
			createElementAndAppendToParent(document, "SURNAME", REQUEST, surname);
			createElementAndAppendToParent(document, "BIRTHDAY", REQUEST, sdf.format(birthday));
			Element PLACE=createElementAndAppendToParent(document,"PLACE",REQUEST,null);
				createElementAndAppendToParent(document, "CITY_KOD", PLACE, Integer.toString(city));
			createElementAndAppendToParent(document,"SEX",REQUEST,"male");
		return document;
	}
	
	/** создать элемент, добавить его в родителя, назначить ему текстовое содержимое  
	 * @param document - документ
	 * @param elementName - имя элемента
	 * @param parent - родительский элемент
	 * @param elementTextValue - текстовое значение элемента ( если null - не устанавливаем )
	 * @return
	 */
	private static Element createElementAndAppendToParent(Document document, String elementName, Element parent, String elementTextValue){
		Element element=document.createElement(elementName);
		parent.appendChild(element);
		if(elementTextValue!=null){
			element.setTextContent(elementTextValue);
		}
		return element;
	}

	/** создать элемент, добавить его в родителя, назначить ему текстовое содержимое  
	 * @param document - документ
	 * @param elementName - имя элемента
	 * @param parent - родительский элемент
	 * @param elementTextValue - текстовое значение элемента ( если null - не устанавливаем )
	 * @return
	 */
	private static Element createElementAndAppendToParent(Document document, String elementName, Document parent, String elementTextValue){
		Element element=document.createElement(elementName);
		parent.appendChild(element);
		if(elementTextValue!=null){
			element.setTextContent(elementTextValue);
		}
		return element;
	}
	
	
	/** получить текстовое содержимое указанного (XPath) Node */
	private static String getStringFromDocumentByPath(Document document, String path){
		Node node=getNodeFromDocument(document, path);
		if(node!=null){
			return node.getTextContent();
		}else{
			return null;
		}
	}
	
	/** получить Node по указанному пути */
	private static Node getNodeFromDocument(Document document, String path){
		XPath xpath = XPathFactory.newInstance().newXPath();
		try {
			return (Node)xpath.evaluate(path, document, XPathConstants.NODE);
		} catch (XPathExpressionException e) {
			return null;
		}
	}
	
	
}
