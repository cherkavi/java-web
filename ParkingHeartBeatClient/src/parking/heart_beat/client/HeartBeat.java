package parking.heart_beat.client;

import java.io.BufferedReader;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;

import parking.heart_beat.exchange.client.ClientRequest;
import parking.heart_beat.exchange.client.ConfirmTask;
import parking.heart_beat.exchange.client.Parameters;
import parking.heart_beat.exchange.common.Param;
import parking.heart_beat.exchange.server.ConfirmParameters;
import parking.heart_beat.exchange.server.ServerResponse;
import parking.heart_beat.exchange.server.Task;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

/** �����, ������� �������� ������ �� ��������� ������, �������� ������ �� ����� client_settings.xml*/
public class HeartBeat implements Runnable{
		private final static String parameterModuleNumber="number";
		private final static String parameterModuleValue="value";
		private final static SimpleDateFormat sdf=new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
	
		public static void main(String[] args){
			HeartBeat enterPoint=new HeartBeat();
			(new Thread(enterPoint)).start();
			enterPoint.addParameter(new Param("STARTUP",sdf.format(new java.util.Date())));
			for(int counter=0;counter<100;counter++){
				try{
					Thread.sleep(1000);
					enterPoint.addParameter(new Param("value_"+counter,sdf.format(new java.util.Date())));
				}catch(Exception ex){};
			}
		}
		/** ������ ���� � �����, ������� �������� ����������� ��������� */
		private final static String fileXmlName="client_settings.xml";
		/** ��������� ������*/
		private Settings settings;
		
		/** �����, ������� �������� ������ �� ��������� ������, �������� ������ �� ����� client_settings.xml*/
		public HeartBeat(){
			settings=new Settings(this.getFullPathToXml());
		}
		
		/** �������� �������� ��� �������� �� ������ ��� ��������� �������� � ��� ( �������� )*/
		public void addParameter(Param param){
			synchronized(this.parametersContainer){
				this.parametersContainer.addParameter(param);
			}
		}
		
		/** �������� ������ ���� � XML ����� */
		private String getFullPathToXml(){
			String currentDirectory=System.getProperty("user.dir");
			String fileSeparator=System.getProperty("file.separator");
			String path=null;
			if(currentDirectory.endsWith(fileSeparator)){
				path=currentDirectory+fileXmlName;
			}else{
				path=currentDirectory+fileSeparator+fileXmlName;
			}
			return path;
		}
		
		
		/** ����, ������� ������� � ���, ��� �������� ����� ���������� */
		private boolean isRun=true;
		
		/** ������ ��� ������ ������ � ����� */
		private Charset charset=Charset.forName("UTF-8");
		
		@Override
		public void run() {
			// �������� ���� ������ ��������� - ���� ������ 
			while( isRun==true){
				// ������� ��������� � ��������
				try{
					// ������������ ������ ClientRequest 
					ClientRequest request=new ClientRequest();
						// �������� ������������� ������������ Task, ������� ����� ���� ������� � ���������� ��������
					request.setConfirmTask(this.getConfirmTask());						
						// �������� ������� ���������
					request.setParameters(this.getParameters());
					// ��������� ������ �� ������ � ����� ����������� ModuleId � ClientRequest
					URL url=new URL(this.settings.getUrlPath());
					HttpURLConnection connection=(HttpURLConnection)url.openConnection();
					connection.setDoInput(true);
					connection.setDoOutput(true);
					connection.setRequestMethod("POST");
					connection.setUseCaches(false); // ��������, ������� � ��� ��� ��� ���������� �� ���������� ���� �������
					// ����������� � ��������
					connection.connect();
					// �������� ������ � ������ 
					PrintWriter printToServer=new PrintWriter(connection.getOutputStream());
					printToServer.print(this.getHttpParametersAsString(this.settings.getModuleId(), request));
					printToServer.flush();
					printToServer.close();
					// �������� ����� �� ���������� �������
					InputStream inputStream=connection.getInputStream();
					InputStreamReader isr=null;
					if(this.charset!=null){
						isr=new InputStreamReader(inputStream,charset);
					}else{
						isr=new InputStreamReader(inputStream);
					}
					BufferedReader br=new BufferedReader(isr);
					StringBuffer sb=new StringBuffer();
					String line=null;
					while( (line=br.readLine())!=null){
						sb.append(line);
					}
					try{
						inputStream.close();
					}catch(Exception ex){};
					try{
						isr.close();
					}catch(Exception ex){};
					ServerResponse response=(ServerResponse)this.getObjectFromString(sb.toString());
					// ���������� �����
						// ���������� ������������� ������������ ����������
					this.processConfirmParameters(response.getConfirmParameters());
						// ���������� ������������� ������
					this.processTask(response.getTask());

					// ��������� ��������� �������� 
					try{
						Thread.sleep(this.settings.getSendDelay()*1000);
					}catch(Exception ex2){};
				}catch(Exception ex){
					System.err.println("run Exception:"+ex.getMessage());
					// ��������� ������ �� ����� ������� �����
					try{
						Thread.sleep(this.settings.getSendErrorDelay()*1000);
					}catch(Exception ex2){};
				}
			}
		}

		private TaskContainer taskContainer=new TaskContainer();
		/** �������� ������������� � �����������/���������� Task*/
		private ConfirmTask getConfirmTask(){
			return taskContainer.getConfirmTaskForServer();
		}
		/** ���������� ������, ������� �������� �� ������� */
		private void processTask(Task serverTask){
			this.taskContainer.processServerTask(serverTask);
		}

		
		/** ���������, ������� �������� ���������� ��� ��������� ���������� */
		private ParametersContainer parametersContainer=new ParametersContainer();
		/** �������� ��������� ��� ������� �������� */
		private Parameters getParameters(){
			synchronized (this.parametersContainer) {
				return this.parametersContainer.getParametersForSend();
			}
		}
		/** ���������� �������������� ��������� */
		private void processConfirmParameters(ConfirmParameters confirmParameter){
			synchronized (this.parametersContainer) {
				this.parametersContainer.processConfirmParameter(confirmParameter);
			}
		}
		
		/** �������� ��� �������� �� HTTP ������ � ���� ������ */
		private String getHttpParametersAsString(String moduleId, ClientRequest request){
			StringBuffer result=new StringBuffer();
			try{
				result.append(URLEncoder.encode(parameterModuleNumber,"UTF-8")
					      +"="
					      +URLEncoder.encode(moduleId,"UTF-8"));
				result.append("&");
				result.append(URLEncoder.encode(parameterModuleValue,"UTF-8")
					      +"="
					      +URLEncoder.encode(this.getStringFromObject(request),"UTF-8"));
			}catch(Exception ex){
				System.out.println("EnterPoint#getHttpParametersAsString Exception:"+ex.getMessage());
			}
			return result.toString();
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
