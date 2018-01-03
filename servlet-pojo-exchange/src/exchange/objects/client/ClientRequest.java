package exchange.objects.client;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.net.URLConnection;

import exchange.objects.shared.*;

/** объект, который осуществляет информационный обмен между сервером и клиентом */
public class ClientRequest {
	/** полный путь к сервлету, который обеспечивает обмен данными */
	private String pathToServer;
	 
	public ClientRequest(String pathToServer){
		this.pathToServer=pathToServer;
	}

	
	/** послать на сервер и получить ответ  
	 * @param request - объект, который следует послать на сервер 
	 * @return объект, который следует получить от сервера 
	 * @throws - если произошла ошибка во время работы 
	 * */
	public Object sendToServer(Object request) throws Exception{
		URLConnection connection=null;
		Object returnValue=null;
		try{
			URL url=new URL(this.pathToServer);
			connection=url.openConnection();
			connection.setDoInput(true);
			connection.setDoOutput(true);
			ObjectOutputStream out=new ObjectOutputStream(connection.getOutputStream());
			out.writeObject(request);
			out.flush();
			out.close();
			
			ObjectInputStream is=new ObjectInputStream(connection.getInputStream());
			returnValue=is.readObject();
			is.close();
		}finally{
			// actions 
		}
		return returnValue;
	}
	
	public static void main(String[] args){
		System.out.println();
		ClientRequest client=new ClientRequest("http://localhost:8080/ServletObjectExchange/ServerGate");
		/*ExchangeObject value=new ExchangeObject(5, "hello");
		try{
			ExchangeObject returnValue=(ExchangeObject)client.sendToServer(value);
			System.out.println("CLIENT OK : "+returnValue.toString());
		}catch(Exception ex){
			System.err.println("Exception: "+ex.getMessage());
		}*/

		Command value=Command.COMMAND_STOP;
		try{
			Command returnValue=(Command)client.sendToServer(value);
			System.out.println("CLIENT OK : "+returnValue.toString());
		}catch(Exception ex){
			System.err.println("Exception: "+ex.getMessage());
		}
	}
	
	
}
