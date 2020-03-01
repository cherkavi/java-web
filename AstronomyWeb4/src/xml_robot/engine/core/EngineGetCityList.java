package xml_robot.engine.core;

import java.sql.ResultSet;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import database.ConnectWrap;
import database.StaticConnector;

import xml_robot.engine.Engine;
import xml_robot.exception.EngineException;

public class EngineGetCityList extends Engine{
	private Logger logger=Logger.getLogger(this.getClass());
	
	@Override
	protected Document execute(int partnerKod, Document document) throws EngineException {
		Document returnValue=null;
		ConnectWrap connector=StaticConnector.getConnectWrap();
		try{
			returnValue=Engine.createDocument();
			Element CITY_LIST=returnValue.createElement("CITY_LIST");
			returnValue.appendChild(CITY_LIST);
			ResultSet rs=connector.getConnection().createStatement().executeQuery("select * from a_city order by id");
			while(rs.next()){
				Element element=createElementFromResultSet(returnValue,rs);
				if(element!=null){
					CITY_LIST.appendChild(element);
				}
			}
		}catch(Exception ex){
			logger.error("#execute Exception:"+ex.getMessage());
			throw new EngineException(408, this.getFunctionName());
		}finally{
			connector.close();
		}
		return returnValue;
	}
	
	private Element createElementFromResultSet(Document document, ResultSet rs){
		try{
			Element CITY=document.createElement("CITY");
				
				Element ID=document.createElement("ID");
				CITY.appendChild(ID);
				ID.setTextContent(rs.getString("ID"));
				
				Element NAME=document.createElement("NAME");
				CITY.appendChild(NAME);
				NAME.setTextContent(rs.getString("NAME"));
				logger.debug(rs.getString("NAME"));
			return CITY;
		}catch(Exception ex){
			logger.error("#createElementFromResultSet Exception:"+ex.getMessage());
			return null;
		}
	}
	
	@Override
	protected String getFunctionName() {
		return "get_city_list";
	}

	@Override
	protected void isAllParametersExists(Document document) throws EngineException {
		// have no parameters
	}

	@Override
	protected void isAllParametersValid(Document document) throws EngineException {
		// have no parameters
	}

}
