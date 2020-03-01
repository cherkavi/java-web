package xml_robot;

import java.io.IOException;


import java.sql.ResultSet;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.apache.log4j.BasicConfigurator;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import database.ConnectWrap;
import database.StaticConnector;

import xml_robot.engine.Engine;
import xml_robot.engine.core.*;
import xml_robot.exception.EngineException;

/**
 * Servlet implementation class AstroRobo
 */
public class AstroRobo extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private ArrayList<Engine> listOfEngine=new ArrayList<Engine>();
    
    static{
    	BasicConfigurator.configure();
    }
    
	public AstroRobo(){
		// FIXME
		// INFO Engine list создать двигатели обработчиков входящих запросов
		this.listOfEngine.add(new EngineChangeUserSettings());
		this.listOfEngine.add(new EngineExecuteAlgorithm());
		this.listOfEngine.add(new EngineGetCityList());
		this.listOfEngine.add(new EngineGetReportInfo());
		this.listOfEngine.add(new EngineGetReportList());
		this.listOfEngine.add(new EngineRegistration());
	}
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doAction(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doAction(request, response);
	}

	protected void doAction(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("UTF-8");
		String requestParameter=request.getParameter("request");
		if(requestParameter==null){
			debug("ошибка получения параметра \"request\"");
			response.getWriter().write(Engine.getStringFromXmlDocument(Engine.getErrorXml(400, "'request' parameter does not recognized")));
			return;
		}
		debug("распарсить параметр в XML"); 
		Document requestXml=Engine.getXmlFromString(requestParameter);
		if(requestXml==null){
			debug("ошибка парсинга XML");
			response.getWriter().write(Engine.getStringFromXmlDocument(Engine.getErrorXml(401, "the parameter 'request' consists error XML ")));
			return;
		}
		/** код партнера  */
		int partnerKod=0;
		try{
			partnerKod=this.getPartnerKod(requestXml);
		}catch(EngineException ex){
			debug("код партнера не найден"); 
			response.getWriter().write(Engine.getStringFromXmlDocument(Engine.getErrorXml(ex.getErrroKod(), ex.getErrorText())));
			return;
		}
		
		/** полное имя функции */
		String functionName=this.getStringFromDocumentByPath(requestXml, "//REQUEST/FUNCTION_NAME");
		if(functionName==null){
			debug("не найдена запрашиваемая функция ");
			response.getWriter().write(Engine.getStringFromXmlDocument(Engine.getErrorXml(406, "request function does not found")));
		}
		
		if(partnerKod==0){
			debug("не опознан код партнера");
			response.getWriter().write(Engine.getStringFromXmlDocument(Engine.getErrorXml(403, "the partners kod does not recognized")));
			return;
		}
		
		debug("пробежаться по всем доступным обработчикам, и на первый кто ответил выдать ответ"); 
		for(int counter=0;counter<this.listOfEngine.size();counter++){
			try{
				Document returnValue=this.listOfEngine.get(counter).process(partnerKod, functionName, requestXml);
				if(returnValue!=null){
					debug("обработчик положительно обработал сообщение");
					response.getWriter().write(Engine.getStringFromXmlDocument(returnValue));
					return ;
				}else{
					debug("обращение к следующему обработчику");
				}
			}catch(EngineException ex){
				debug("ошибка во время работы обработчика");
				response.getWriter().write(Engine.getStringFromXmlDocument(Engine.getErrorXml(ex.getErrroKod(), ex.getErrorText())));
				return;
			}
		}
		debug("обработчик не обнаружен"); 
		response.getWriter().write(Engine.getStringFromXmlDocument(Engine.getErrorXml(402, "the parameter 'request' consists error XML ")));
		return;
	}
	
	
	
	/** проверить партнера из зарегестрированных  */
	private int getPartnerKod(Document document) throws EngineException{
		int returnValue=0;
		// запросить базу на наличие кода в документе 	REQUEST.PARTNER.KOD
		String secretKod=this.getStringFromDocumentByPath(document, "//REQUEST/PARTNER/KOD");
		if(secretKod==null){
			throw new EngineException(403, "partner does not found");
		}
		ConnectWrap connector=StaticConnector.getConnectWrap();
		try{
			ResultSet rs=connector.getConnection().createStatement().executeQuery("select * from a_partner where secret_kod="+secretKod);
			while(rs.next()){
				returnValue=rs.getInt("kod");
			}
		}catch(Exception ex){
			error("#getPartnerKod Exception:"+ex.getMessage());
		}finally{
			try{
				connector.close();
			}catch(Exception ex){};
		}
		return returnValue;
	}
	
	
	
	
	
	private void error(String information){
		System.err.print("AstroRobo#");
		System.err.println(information);
	}

	private void debug(String information){
		System.out.print("AstroRobo#");
		System.out.println(information);
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
	
}
