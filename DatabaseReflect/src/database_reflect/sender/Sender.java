package database_reflect.sender;

import java.net.MalformedURLException;


import org.codehaus.xfire.XFire;
import org.codehaus.xfire.XFireFactory;
import org.codehaus.xfire.client.XFireProxyFactory;
import org.codehaus.xfire.service.Service;
import org.codehaus.xfire.service.binding.ObjectServiceFactory;

import database_reflect.marshalling.MarshalObject;
import database_reflect.server_listener.ITableRecordListener;

/** класс, который отправляет данные на сервер */
public class Sender {
	private XFireProxyFactory proxyFactory=null;
	private Service serviceModel=null;
	private String serviceUrl = null;
	private MarshalObject marshalObject=new MarshalObject();
	
	/** объект, который посылает данные на сервер 
	 * @param pathToServer - полный путь к приложению, например <i>"http://localhost:8080/DataBaseReflectServer"</i>
	 * */
	public Sender(String pathToServer){
        XFire xfire = XFireFactory.newInstance().getXFire();
        proxyFactory = new XFireProxyFactory(xfire);
        serviceModel = new ObjectServiceFactory().create(ITableRecordListener.class);
        this.serviceUrl=pathToServer+"/services/RecordListener";
	}
	
	public String sendData(Object tableRecord){
		String returnValue=null;
        ITableRecordListener client = null;
        try {
            client = (ITableRecordListener) proxyFactory.create(serviceModel, serviceUrl);
            returnValue=this.invoke(client,tableRecord);
            client=null;
        } catch (MalformedURLException e) {
        	returnValue=null;
            System.err.println("Sender#sendData: EXCEPTION: " + e.toString());
        }    
        return returnValue;
	}
	
	private String invoke(ITableRecordListener client, Object tableRecord){
		try{
			//return client.getRecord(tableRecord);
			String stringForSend=this.marshalObject.getStringFromObject(tableRecord);
			//System.out.println("For Send: "+stringForSend);
			return client.getRecord(stringForSend);// (Serializable)tableRecord);
		}catch(Exception ex){
			System.err.println("invoke Exception: "+ex.getMessage());
			return null;
		}
		
	}
};
