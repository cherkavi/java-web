package parking.heart_beat.server;


import java.io.IOException;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import parking.heart_beat.exchange.client.ClientRequest;
import parking.heart_beat.exchange.client.ConfirmTask;
import parking.heart_beat.exchange.client.Parameters;
import parking.heart_beat.exchange.common.Param;
import parking.heart_beat.exchange.server.ConfirmParameters;
import parking.heart_beat.exchange.server.ServerResponse;
import parking.heart_beat.exchange.server.Task;

import database.ConnectWrap;
import database.wrap.ModuleHeartBeat;
import database.wrap.ModuleParameter;
import database.wrap.ModuleTask;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

/**
 * Сервлет, который принимает "сердцебиение" от удаленных клиентов, анализирует полученные данные и отвечает на них
 */
public class Gate extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final static String parameterModuleNumber="number";
	private final static String parameterModuleValue="value";
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doService(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doService(request, response);
	}

	
	private void doService(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ClientHttpRequest clientRequest=new ClientHttpRequest(request);
		PrintWriter out=response.getWriter();
		this.processResponse(clientRequest, out);
		out.close();
		
	}

	/** обработать запрос и послать ответ */
	private boolean processResponse(ClientHttpRequest clientRequest, PrintWriter out){
		boolean returnValue=false;
		ClientHttpParameter moduleNumber=clientRequest.getParameterByName(parameterModuleNumber);
		ClientHttpParameter moduleValue=clientRequest.getParameterByName(parameterModuleValue);
		if((moduleNumber!=null)&&(moduleNumber.getParamValue()!=null)){
			ConnectWrap connector=database.ConnectWrapFactory.getConnectWrap();
			try{
				ServerResponse response=new ServerResponse();
				Integer moduleId=this.getModuleIdByName(connector.getConnection(), moduleNumber.getParamValue());
				// обработать функцию HeartBeat от удаленного модуля 
				this.processHeartBeat(connector, moduleId);
				// проверить наличие переданного параметра 
				if((moduleValue!=null)&&(moduleValue.getParamValue()!=null)){
					// обработать функцию переданную модулем
					processModuleParameter(connector, response, moduleId,moduleValue.getParamValue());
				};
				// проверить есть ли задачи с StateOfTask.id=1 ( передана клиенту, но не пришел ответ на их прием ) - преобразовать в новую задачу 
				this.refreshTaskByModule(connector, moduleId);
				// проверить есть ли для данного модуля какие-либо задачи-параметры для передачи  
				if(isTaskExistsForModule(connector, moduleId)){
					// есть задание для данного модуля
					// получить все задания для указанного модуля 
					List<ModuleTask> taskList=this.getTaskListByModule(connector,moduleId);
					// записать эти задания в объект ServerResponse
					response.setTask(new Task(this.getTaskFromModuleTask(taskList)));
					// установить для List<ModuleTask> значение - передано на клиента
					this.setModuleTaskState(connector, taskList, new Integer(1));
				}
				// записать ответ в виде XML клиенту 
				out.print(this.getStringFromObject(response));
			}finally{
				connector.close();
			}
		}else{
			// не найден основной параметр - номер модуля, неизвестный пакет - 
			this.writeEmptyTask(out);
		}
		return returnValue;
	}
	
	/** по указанному клиенту все задачи с StateOfTask=1 преобразовать в StateOfTask=0 */
	private void refreshTaskByModule(ConnectWrap connector, Integer moduleId){
		Connection connection=connector.getConnection();
		Statement statement=null;
		try{
			statement=connection.createStatement();
			statement.executeUpdate("update module_task set module_task.id_state=0 where id_module="+moduleId+" and module_task.id_state=1");
			connection.commit();
		}catch(Exception ex){
			try{
				connection.rollback();
			}catch(Exception ex2){};
		}finally{
			try{
				statement.close();
			}catch(Exception ex){};
		}
	}
	
	/** установить значение передано на клиента StateOfTask=1 */
	private void setModuleTaskState(ConnectWrap connector, List<ModuleTask> taskList, Integer stateOfTask){
		Session session=connector.getSession();
		session.beginTransaction();
		Date dateWrite=new Date();
		for(int counter=0;counter<taskList.size();counter++){
			taskList.get(counter).setDateWrite(dateWrite);
			taskList.get(counter).setIdState(stateOfTask);
			session.update(taskList.get(counter));
		}
		session.getTransaction().commit();
	}
	
	/** получить массив заданий для удаленного модуля в виде параметров */
	private Param[] getTaskFromModuleTask(List<ModuleTask> taskList){
		Param[] returnValue=new Param[taskList.size()];
		for(int counter=0;counter<taskList.size();counter++){
			returnValue[counter]=new Param(taskList.get(counter).getParamName(),taskList.get(counter).getParamValue());
		}
		return returnValue;
	}
	
	/** получить список задач для указанного модуля  
	 * @param connector - соединение с базой данных 
	 * @param moduleId - уникальный идентификатор модуля 
	 * @return список из задач для удаленного модуля 
	 */
	@SuppressWarnings("unchecked")
	private List<ModuleTask> getTaskListByModule(ConnectWrap connector, Integer moduleId){
		return connector.getSession().createCriteria(ModuleTask.class).add(Restrictions.or(Restrictions.isNull("idState"), Restrictions.eq("idState", new Integer(0)))).add(Restrictions.eq("idModule", moduleId)).list();
	}
	
	/** обработать ответ от модуля ( обработать переданный параметр от модуля ) 
	 * @param connector - соединение с базой данных
	 * @param serverResponse - ответ сервера на клиентский запрос
	 * @param moduleId - уникальный идентификатор модуля
	 * @param parameterValue - параметр, который модуль передал в HTTP запросе
	 */
	private void processModuleParameter(ConnectWrap connector, ServerResponse serverResponse, Integer moduleId, String parameterValue){
		try{
			ClientRequest clientRequest=(ClientRequest)this.getObjectFromString(parameterValue);
			// обработать подтверждения, полученные от модуля о получении задач
				// получить все задачи по данному модулю, которые были посланы на модуль, но подтверждение на них не было получено
			List<ModuleTask> moduleTask=this.getReceivedModuleTask(connector,moduleId);
				// получить все параметры от модуля, которые информируют об обработанных задачах
			ConfirmTask clientConfirmTask=clientRequest.getConfirmTask();
				// пройтись по всем Task и найти им соответствие в полученных от клиента параметрах ConfirmTask
			for(int counter=0;counter<clientConfirmTask.getSize();counter++){
				Param parameter=clientConfirmTask.getParameterByIndex(counter);
				if(parameter!=null){
					writeConfirmTaskFromClient(connector, parameter, moduleTask);
				}
			}
			// обработать переданные параметры - записать все в таблицу 
			Parameters parameters=clientRequest.getParameters();
				// записать все параметры в таблицу
			this.writeParameters(connector, parameters, moduleId);
				// записать в подтверждения прочтения параметров в ServerReponse
			serverResponse.setConfirmParameters(this.getConfirmParameters(parameters));
		}catch(Exception ex){
			System.err.println("Gate#processModuleParameter Exception: "+ex.getMessage());
		}
		// преобразовать строку в объект ClientRequest
		
		// обработать данные
	}
	
	private ConfirmParameters getConfirmParameters(Parameters parameters){
		Param[] params=new Param[parameters.getSize()];
		for(int counter=0;counter<parameters.getSize();counter++){
			params[counter]=new Param(parameters.getParameterByIndex(counter).getName(),"OK");
		}
		ConfirmParameters returnValue=new ConfirmParameters(params);
		return returnValue;
	}

	/** записать переданные параметры в базу данных */
	private void writeParameters(ConnectWrap connector, Parameters parameters, Integer moduleId){
		if((parameters!=null)&&(parameters.getSize()>0)){
			Session session=connector.getSession();
			session.beginTransaction();
			Date currentDate=new Date();
			for(int counter=0;counter<parameters.getSize();counter++){
				ModuleParameter record=new ModuleParameter();
				record.setIdModule(moduleId);
				record.setParamName(parameters.getParameterByIndex(counter).getName());
				record.setParamValue(parameters.getParameterByIndex(counter).getValue());
				record.setDateWrite(currentDate);
				session.save(record);
			}
			session.getTransaction().commit();
		}
	}
	
	/** параметр, который был получен от удаленного клиента "прогнать" по всем задачам, и в случае соответствия имени - записать ответ в базу */
	private void writeConfirmTaskFromClient(ConnectWrap connector, Param param, List<ModuleTask> moduleTask){
		for(int counter=0;counter<moduleTask.size();counter++){
			if(param.getName().equals(moduleTask.get(counter).getParamName())){
				moduleTask.get(counter).setIdState(new Integer(2));// получено подтверждение о получении 
				moduleTask.get(counter).setAnswer(param.getValue());
				moduleTask.get(counter).setDateWrite(new Date());
				Session session=connector.getSession();
				session.beginTransaction();
				session.update(moduleTask.get(counter));
				session.getTransaction().commit();
			}
		}
	}
	
	/** получить из базы данных посланные на модуль задачи (module_task.id_state=1 ("sended to client"))*/
	@SuppressWarnings("unchecked")
	private List<ModuleTask> getReceivedModuleTask(ConnectWrap connector, Integer moduleId){
		try{
			Session session=connector.getSession();
			return (List<ModuleTask>)session.createCriteria(ModuleTask.class).add(Restrictions.eq("idModule",moduleId)).add(Restrictions.eq("idState", new Integer(1))).list();
		}catch(NullPointerException ex){
			return new ArrayList<ModuleTask>();
		}
	}
	
	/** записать в таблицу базы данных "сердцебиение" удаленного модуля */
	private void processHeartBeat(ConnectWrap connector, Integer moduleId){
		// записать HeartBeat для данного модуля (записать в таблицу database.wrap.module_heart_beat) 
		Session session=connector.getSession();
		session.beginTransaction();
		ModuleHeartBeat record=new ModuleHeartBeat();
		record.setIdModule(moduleId);
		record.setDateWrite(new Date());
		session.save(record);
		session.getTransaction().commit();
	}
	
	/** получить код модуля по его имени-идентификатору */
	private int getModuleIdByName(Connection connection, String moduleName){
		int returnValue=0;
		PreparedStatement statement=null;
		try{
			statement=connection.prepareStatement("select * from module where module.name=?");
			statement.setString(1, moduleName);
			ResultSet rs=statement.executeQuery();
			rs.next();
			returnValue=rs.getInt("id");
		}catch(Exception ex){
			System.err.println("Gate#getModuleIdByName Exception: "+ex.getMessage());
		}finally{
			try{
				statement.close();
			}catch(Exception ex){};
		}
		return returnValue;
	}

	/** проверить, есть ли для данного модуля какая-либо задача
	 * @param moduleId - уникальный идентификатор модуля 
	 * @return 
	 * <li>true - есть по данному модулю данные </li>
	 * <li>false - нет по данному модулю данных </li>
	 */
	private boolean isTaskExistsForModule(ConnectWrap connector, Integer moduleId){
		// проверить, есть ли для данного модуля какая-либо задача
		boolean returnValue=false;
		Connection connection=connector.getConnection();
		ResultSet rs=null;
		try{
			
			rs=connection.createStatement().executeQuery("select count(*) from module_task where id_module="+moduleId+" and (module_task.id_state is null or module_task.id_state=0)");
			rs.next();
			returnValue=rs.getInt(1)>0;
		}catch(Exception ex){
			System.err.println("Gate#isTaskExistsForModule Exception:"+ex.getMessage());
		}finally{
			try{
				rs.getStatement().close();
			}catch(Exception ex){};
		}
		return returnValue;
	}
	
	
	private void writeEmptyTask(PrintWriter out){
		out.println("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><empty/>");
	}

	/** сериализация - получение строки из объекта */
	private String getStringFromObject(Object object){
		// Сериализация объекта в XML
		XStream xstream = new XStream();
		//xstream.alias("Complex", Complex.class);
		return xstream.toXML(object);
	}
	
	/** десериализация - получение объекта из строки */
	private Object getObjectFromString(String value){
		// Десириализация объекта из XML - DOM Driver
		XStream xstream = new XStream(new DomDriver()); // does not require XPP3 library
		return xstream.fromXML(value);
	}
	
}
