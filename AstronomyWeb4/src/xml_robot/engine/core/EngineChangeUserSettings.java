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

/** изменение регистрационных данных пользователя  */
public class EngineChangeUserSettings extends Engine{
	private Logger logger=Logger.getLogger(this.getClass());
	@Override
	protected Document execute(int partnerKod, Document document) throws EngineException {
		Integer kod=this.getParameterAsInteger(document, "//REQUEST/USER/KOD");
		String name=this.getParameterAsString(document, "//REQUEST/NAME");
		String surname=this.getParameterAsString(document, "//REQUEST/SURNAME");
		Date birthday=this.getParameterAsTimeStamp(document, "//REQUEST/BIRTHDAY");
		Integer cityKod=this.getParameterAsInteger(document, "//REQUEST/PLACE/CITY_KOD");
		String sex=this.getParameterAsEnum(document, "//REQUEST/SEX", "male","female");
		
		Document returnValue=null;
		ConnectWrap connector=StaticConnector.getConnectWrap();
		if(cityKod!=null){
			if(this.isCityExists(connector, cityKod)==false){
				throw new EngineException(409, "//REQUEST/PLACE/CITY_KOD");
			}
		}
		try{
			A_USER_ID user=(A_USER_ID)connector.getSession().get(A_USER_ID.class, kod);
			if(user==null){
				throw new EngineException(408, "the user with kod="+kod+" does not found");
			}
			// является ли данный клиент собственностью текущего партнера 
			if(user.getIdPartner().intValue()!=partnerKod){
				throw new EngineException(410, "you are not have this client");
			}
			connector.getSession().beginTransaction();
			
			if(name!=null){
				user.setName(name);
			}
			if(surname!=null){
				user.setSurname(surname);
			}
			if(birthday!=null){
				user.setBirthday(birthday);
			}
			if(cityKod!=null){
				user.setBirthdayIdCity(cityKod);
			}
			if(sex!=null){
				user.setSex(sex.equals("male")?1:0);
			}
			connector.getSession().update(user);
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
	protected String getFunctionName() {
		return "change_user_settings";
	}

	@Override
	protected void isAllParametersExists(Document document) throws EngineException {
		this.checkParameter(document, "//REQUEST/USER/KOD");
		this.checkParameterOneOfAll(document, "//REQUEST/NAME",
											  "//REQUEST/SURNAME",
											  "//REQUEST/BIRTHDAY",
											  "//REQUEST/PLACE/CITY_KOD",
											  "//REQUEST/SEX"
											  );
	}

	@Override
	protected void isAllParametersValid(Document document) throws EngineException {
		this.checkType(document, "//REQUEST/USER/KOD", EngineParameterType.INTEGER, false);
		this.checkTypeIfExists(document, "//REQUEST/NAME", EngineParameterType.VARCHAR, false);
		this.checkTypeIfExists(document, "//REQUEST/SURNAME", EngineParameterType.VARCHAR, false);
		this.checkTypeIfExists(document, "//REQUEST/BIRTHDAY", EngineParameterType.TIMESTAMP, false);
		this.checkTypeIfExists(document, "//REQUEST/PLACE/CITY_KOD", EngineParameterType.INTEGER, false);
		this.checkTypeIfExists(document, "//REQUEST/NAME", EngineParameterType.VARCHAR, false);
		this.checkTypeIfExists(document, "//REQUEST/SEX", EngineParameterType.ENUM, false, "male","female");
	}

}
