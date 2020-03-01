package xml_robot.engine.core;

import java.util.Date;


import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import database.ConnectWrap;
import database.StaticConnector;
import database.wrap_mysql.A_USER_ID;

import xml_robot.engine.Engine;
import xml_robot.engine.EngineParameterType;
import xml_robot.exception.EngineException;

/** регистрация нового пользователя  */
public class EngineRegistration extends Engine{
	private Logger logger=Logger.getLogger(this.getClass());
	
	@Override
	protected Document execute(int partnerKod, Document document) throws EngineException {
		String name=this.getParameterAsString(document, "//REQUEST/NAME");
		String surname=this.getParameterAsString(document, "//REQUEST/SURNAME");
		Date birthday=this.getParameterAsTimeStamp(document, "//REQUEST/BIRTHDAY");
		Integer cityKod=this.getParameterAsInteger(document, "//REQUEST/PLACE/CITY_KOD");
		String sex=this.getParameterAsEnum(document, "//REQUEST/SEX", "male","female");
		
		Document returnValue=null;
		ConnectWrap connector=StaticConnector.getConnectWrap();
		if(this.isCityExists(connector, cityKod)==false){
			throw new EngineException(409, "//REQUEST/PLACE/CITY_KOD");
		}
		try{
			A_USER_ID user=new A_USER_ID();
			connector.getSession().beginTransaction();
			user.setName(name);
			user.setSurname(surname);
			user.setBirthday(birthday);
			user.setBirthdayIdCity(cityKod);
			user.setSex(sex.equals("male")?1:0);
			user.setIdPartner(partnerKod);
			connector.getSession().save(user);
			connector.getSession().getTransaction().commit();
			returnValue=createAnswer(user.getId());
		}catch(Exception ex){
			try{
				connector.getSession().getTransaction().rollback();
			}catch(Exception exInner){};
			logger.error("#execute Exception: "+ex.getMessage());
			throw new EngineException(408, this.getFunctionName());
		}finally{
			connector.close();
		}
		return returnValue;
	}
	
	private Document createAnswer(int userKod){
		Document returnValue=Engine.createDocument();
		Element RESPONSE=returnValue.createElement("RESPONSE");
		returnValue.appendChild(RESPONSE);
		Element USER=returnValue.createElement("USER");
		RESPONSE.appendChild(USER);
		Element KOD=returnValue.createElement("KOD");
		USER.appendChild(KOD);
		KOD.setTextContent(Integer.toString(userKod));
		return returnValue;
	}

	@Override
	protected void isAllParametersExists(Document document)
			throws EngineException {
		this.checkParameter(document, "//REQUEST/NAME");
		this.checkParameter(document, "//REQUEST/SURNAME");
		this.checkParameter(document, "//REQUEST/BIRTHDAY");
		this.checkParameter(document, "//REQUEST/PLACE/CITY_KOD");
		this.checkParameter(document, "//REQUEST/SEX");
	}

	@Override
	protected void isAllParametersValid(Document document) throws EngineException {
		this.checkType(document, "//REQUEST/NAME", EngineParameterType.VARCHAR, false);
		this.checkType(document, "//REQUEST/SURNAME", EngineParameterType.VARCHAR, false);
		this.checkType(document, "//REQUEST/BIRTHDAY", EngineParameterType.TIMESTAMP, false);
		this.checkType(document, "//REQUEST/PLACE/CITY_KOD", EngineParameterType.INTEGER, false);
		this.checkType(document, "//REQUEST/SEX", EngineParameterType.ENUM, false,"male", "female");
	}

	@Override
	protected String getFunctionName() {
		return "registration";
	}

}
