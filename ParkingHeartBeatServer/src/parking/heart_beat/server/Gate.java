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
 * �������, ������� ��������� "������������" �� ��������� ��������, ����������� ���������� ������ � �������� �� ���
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

	/** ���������� ������ � ������� ����� */
	private boolean processResponse(ClientHttpRequest clientRequest, PrintWriter out){
		boolean returnValue=false;
		ClientHttpParameter moduleNumber=clientRequest.getParameterByName(parameterModuleNumber);
		ClientHttpParameter moduleValue=clientRequest.getParameterByName(parameterModuleValue);
		if((moduleNumber!=null)&&(moduleNumber.getParamValue()!=null)){
			ConnectWrap connector=database.ConnectWrapFactory.getConnectWrap();
			try{
				ServerResponse response=new ServerResponse();
				Integer moduleId=this.getModuleIdByName(connector.getConnection(), moduleNumber.getParamValue());
				// ���������� ������� HeartBeat �� ���������� ������ 
				this.processHeartBeat(connector, moduleId);
				// ��������� ������� ����������� ��������� 
				if((moduleValue!=null)&&(moduleValue.getParamValue()!=null)){
					// ���������� ������� ���������� �������
					processModuleParameter(connector, response, moduleId,moduleValue.getParamValue());
				};
				// ��������� ���� �� ������ � StateOfTask.id=1 ( �������� �������, �� �� ������ ����� �� �� ����� ) - ������������� � ����� ������ 
				this.refreshTaskByModule(connector, moduleId);
				// ��������� ���� �� ��� ������� ������ �����-���� ������-��������� ��� ��������  
				if(isTaskExistsForModule(connector, moduleId)){
					// ���� ������� ��� ������� ������
					// �������� ��� ������� ��� ���������� ������ 
					List<ModuleTask> taskList=this.getTaskListByModule(connector,moduleId);
					// �������� ��� ������� � ������ ServerResponse
					response.setTask(new Task(this.getTaskFromModuleTask(taskList)));
					// ���������� ��� List<ModuleTask> �������� - �������� �� �������
					this.setModuleTaskState(connector, taskList, new Integer(1));
				}
				// �������� ����� � ���� XML ������� 
				out.print(this.getStringFromObject(response));
			}finally{
				connector.close();
			}
		}else{
			// �� ������ �������� �������� - ����� ������, ����������� ����� - 
			this.writeEmptyTask(out);
		}
		return returnValue;
	}
	
	/** �� ���������� ������� ��� ������ � StateOfTask=1 ������������� � StateOfTask=0 */
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
	
	/** ���������� �������� �������� �� ������� StateOfTask=1 */
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
	
	/** �������� ������ ������� ��� ���������� ������ � ���� ���������� */
	private Param[] getTaskFromModuleTask(List<ModuleTask> taskList){
		Param[] returnValue=new Param[taskList.size()];
		for(int counter=0;counter<taskList.size();counter++){
			returnValue[counter]=new Param(taskList.get(counter).getParamName(),taskList.get(counter).getParamValue());
		}
		return returnValue;
	}
	
	/** �������� ������ ����� ��� ���������� ������  
	 * @param connector - ���������� � ����� ������ 
	 * @param moduleId - ���������� ������������� ������ 
	 * @return ������ �� ����� ��� ���������� ������ 
	 */
	@SuppressWarnings("unchecked")
	private List<ModuleTask> getTaskListByModule(ConnectWrap connector, Integer moduleId){
		return connector.getSession().createCriteria(ModuleTask.class).add(Restrictions.or(Restrictions.isNull("idState"), Restrictions.eq("idState", new Integer(0)))).add(Restrictions.eq("idModule", moduleId)).list();
	}
	
	/** ���������� ����� �� ������ ( ���������� ���������� �������� �� ������ ) 
	 * @param connector - ���������� � ����� ������
	 * @param serverResponse - ����� ������� �� ���������� ������
	 * @param moduleId - ���������� ������������� ������
	 * @param parameterValue - ��������, ������� ������ ������� � HTTP �������
	 */
	private void processModuleParameter(ConnectWrap connector, ServerResponse serverResponse, Integer moduleId, String parameterValue){
		try{
			ClientRequest clientRequest=(ClientRequest)this.getObjectFromString(parameterValue);
			// ���������� �������������, ���������� �� ������ � ��������� �����
				// �������� ��� ������ �� ������� ������, ������� ���� ������� �� ������, �� ������������� �� ��� �� ���� ��������
			List<ModuleTask> moduleTask=this.getReceivedModuleTask(connector,moduleId);
				// �������� ��� ��������� �� ������, ������� ����������� �� ������������ �������
			ConfirmTask clientConfirmTask=clientRequest.getConfirmTask();
				// �������� �� ���� Task � ����� �� ������������ � ���������� �� ������� ���������� ConfirmTask
			for(int counter=0;counter<clientConfirmTask.getSize();counter++){
				Param parameter=clientConfirmTask.getParameterByIndex(counter);
				if(parameter!=null){
					writeConfirmTaskFromClient(connector, parameter, moduleTask);
				}
			}
			// ���������� ���������� ��������� - �������� ��� � ������� 
			Parameters parameters=clientRequest.getParameters();
				// �������� ��� ��������� � �������
			this.writeParameters(connector, parameters, moduleId);
				// �������� � ������������� ��������� ���������� � ServerReponse
			serverResponse.setConfirmParameters(this.getConfirmParameters(parameters));
		}catch(Exception ex){
			System.err.println("Gate#processModuleParameter Exception: "+ex.getMessage());
		}
		// ������������� ������ � ������ ClientRequest
		
		// ���������� ������
	}
	
	private ConfirmParameters getConfirmParameters(Parameters parameters){
		Param[] params=new Param[parameters.getSize()];
		for(int counter=0;counter<parameters.getSize();counter++){
			params[counter]=new Param(parameters.getParameterByIndex(counter).getName(),"OK");
		}
		ConfirmParameters returnValue=new ConfirmParameters(params);
		return returnValue;
	}

	/** �������� ���������� ��������� � ���� ������ */
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
	
	/** ��������, ������� ��� ������� �� ���������� ������� "��������" �� ���� �������, � � ������ ������������ ����� - �������� ����� � ���� */
	private void writeConfirmTaskFromClient(ConnectWrap connector, Param param, List<ModuleTask> moduleTask){
		for(int counter=0;counter<moduleTask.size();counter++){
			if(param.getName().equals(moduleTask.get(counter).getParamName())){
				moduleTask.get(counter).setIdState(new Integer(2));// �������� ������������� � ��������� 
				moduleTask.get(counter).setAnswer(param.getValue());
				moduleTask.get(counter).setDateWrite(new Date());
				Session session=connector.getSession();
				session.beginTransaction();
				session.update(moduleTask.get(counter));
				session.getTransaction().commit();
			}
		}
	}
	
	/** �������� �� ���� ������ ��������� �� ������ ������ (module_task.id_state=1 ("sended to client"))*/
	@SuppressWarnings("unchecked")
	private List<ModuleTask> getReceivedModuleTask(ConnectWrap connector, Integer moduleId){
		try{
			Session session=connector.getSession();
			return (List<ModuleTask>)session.createCriteria(ModuleTask.class).add(Restrictions.eq("idModule",moduleId)).add(Restrictions.eq("idState", new Integer(1))).list();
		}catch(NullPointerException ex){
			return new ArrayList<ModuleTask>();
		}
	}
	
	/** �������� � ������� ���� ������ "������������" ���������� ������ */
	private void processHeartBeat(ConnectWrap connector, Integer moduleId){
		// �������� HeartBeat ��� ������� ������ (�������� � ������� database.wrap.module_heart_beat) 
		Session session=connector.getSession();
		session.beginTransaction();
		ModuleHeartBeat record=new ModuleHeartBeat();
		record.setIdModule(moduleId);
		record.setDateWrite(new Date());
		session.save(record);
		session.getTransaction().commit();
	}
	
	/** �������� ��� ������ �� ��� �����-�������������� */
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

	/** ���������, ���� �� ��� ������� ������ �����-���� ������
	 * @param moduleId - ���������� ������������� ������ 
	 * @return 
	 * <li>true - ���� �� ������� ������ ������ </li>
	 * <li>false - ��� �� ������� ������ ������ </li>
	 */
	private boolean isTaskExistsForModule(ConnectWrap connector, Integer moduleId){
		// ���������, ���� �� ��� ������� ������ �����-���� ������
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

	/** ������������ - ��������� ������ �� ������� */
	private String getStringFromObject(Object object){
		// ������������ ������� � XML
		XStream xstream = new XStream();
		//xstream.alias("Complex", Complex.class);
		return xstream.toXML(object);
	}
	
	/** �������������� - ��������� ������� �� ������ */
	private Object getObjectFromString(String value){
		// �������������� ������� �� XML - DOM Driver
		XStream xstream = new XStream(new DomDriver()); // does not require XPP3 library
		return xstream.fromXML(value);
	}
	
}
