package exchange.objects.server;

import java.io.IOException;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import exchange.objects.shared.Command;
import exchange.objects.shared.ExchangeObject;

/**
 * Servlet implementation class ServerGate
 */
public class ServerGate extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try{
			ObjectInputStream is=new ObjectInputStream(request.getInputStream());
			Object object=is.readObject();
			is.close();
			
			ObjectOutputStream out=new ObjectOutputStream(response.getOutputStream());
			out.writeObject(processObject(object));
			out.flush();
			out.close();
			System.out.println("ServerGate#service OK");
		}catch(Exception ex){
			System.err.println("ServerGate#service Exception:"+ex.getMessage());
		}
	}
	
	/** обработка вход€щего объекта и выдача ответа этому объекту */
	private Object processObject(Object request){
		if(request==null){
			return null;
		}else{
			if(request instanceof ExchangeObject){
				ExchangeObject value=(ExchangeObject)request;
				System.out.println("ProcessObject: "+value.toString());
				fillObject(value);
				return value;
			} else if(request instanceof Command){
				Command value=(Command)request;
				System.out.println("ProcessObject: "+value.toString());
				return Command.COMMAND_START;
			}else{
				return null;
			}
		}
	}

	/** временный метод дл€ эмул€ции действий над объектом  */
	private void fillObject(ExchangeObject request){
		request.setIntValue(this.getRandomInt());
		request.setArray(this.getRandomArray());
		request.setStringValue(this.getRandomString());
	}
	
	private Random random=new Random();
	private int getRandomInt(){
		return random.nextInt();
	}
	
	private SimpleDateFormat sdf=new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
	private String getRandomString(){
		return sdf.format(new Date());
	}
	
	/** получить массив из переменных */
	private byte[] getRandomArray(){
		int length=random.nextInt(16);
		byte[] returnValue=new byte[length];
		random.nextBytes(returnValue);
		return returnValue;
	}
}
