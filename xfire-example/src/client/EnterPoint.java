package client;

import java.io.IOException;
import java.lang.reflect.Proxy;
import java.net.MalformedURLException;

import org.codehaus.xfire.XFire;
import org.codehaus.xfire.XFireFactory;
import org.codehaus.xfire.client.XFireProxyFactory;
import org.codehaus.xfire.service.Service;
import org.codehaus.xfire.service.binding.ObjectServiceFactory;

import server.Account;
import server.IBankingService;

public class EnterPoint {
	Log log=new Log();
	
	public static void main(String[] args){
		EnterPoint object=new EnterPoint();
		
		try{
			System.out.println("\n\n"+object.callWebService("Account Source", "Account destination", 50, "EUR"));
			System.out.println("\n\nWebService: get Complex object: "+object.callWebServiceGetAccount(101));
		}catch(MalformedURLException me){
			System.err.println("Exception:"+me.getMessage());
		}catch(NumberFormatException ne){
			System.err.println("Exception:"+ne.getMessage());
		}catch(IOException io){
			System.err.println("Exception:"+io.getMessage());
		}catch(Exception ex){
			System.err.println("Exception:"+ex.getMessage());
		}
		
	}
	
	
	public String callWebService(
	        String fromAccount, String toAccount, double amount, String currency) 
	        throws MalformedURLException, Exception {
	        
	        //Create a metadata of the service      ( new JaxbServiceFactory())
	        Service serviceModel = new ObjectServiceFactory().create(IBankingService.class);        
	        log.debug("callSoapServiceLocal(): got service model." );
	   
	        //Create a proxy for the deployed service
	        XFire xfire = XFireFactory.newInstance().getXFire();
	        XFireProxyFactory factory = new XFireProxyFactory(xfire);      
	    
	        String serviceUrl = "http://localhost:8100/XFireExample/services/Banking";
	        
	        IBankingService client = null;
	        try {
	            client = (IBankingService) factory.create(serviceModel, serviceUrl);
	        } catch (MalformedURLException e) {
	            log.error("WsClient.callWebService(): EXCEPTION: " + e.toString());
	        }    
	               
	        //Invoke the service
	        String serviceResponse = "";
	        try { 
	            serviceResponse = client.transferFunds(fromAccount, toAccount, amount, currency);
	       } catch (Exception e){
	            log.error("WsClient.callWebService(): EXCEPTION: " + e.toString());                 
	            serviceResponse = e.toString();
	        }        
	        log.debug("WsClient.callWebService(): status=" + serviceResponse);            
	        //Return the response
	        return serviceResponse;
	    }
	
	public Account callWebServiceGetAccount(Integer value)throws MalformedURLException, Exception { 
	        //Create a metadata of the service      
	        Service serviceModel = new ObjectServiceFactory().create(IBankingService.class);        
	        log.debug("callSoapServiceLocal(): got service model." );
	   
	        //Create a proxy for the deployed service
	        XFire xfire = XFireFactory.newInstance().getXFire();
	        XFireProxyFactory factory = new XFireProxyFactory(xfire);      
	    
	        String serviceUrl = "http://localhost:8100/XFireExample/services/Banking";
	        
	        IBankingService client = null;
	        try {
	        	Object object=factory.create(serviceModel, serviceUrl);
	        	Proxy proxy=(Proxy)object;
	        	System.out.println("Class:"+object.getClass()+"   toString:"+object.toString());
	            client = (IBankingService)object; 
	        } catch (MalformedURLException e) {
	            log.error("WsClient.callWebService(): EXCEPTION: " + e.toString());
	        }    
	               
	        //Invoke the service
	        Account serviceResponse = null;
	        try { 
	            serviceResponse = client.getAccountByKod(value);
	       } catch (Exception e){
	            log.error("WsClient.callWebService(): EXCEPTION: " + e.toString());                 
	        }        
	        log.debug("WsClient.callWebService(): status=" + serviceResponse);            
	        //Return the response
	        return serviceResponse;
	    } 	
	
}


class Log {
	public void debug(Object information){
		System.out.println(information);
	}
	
	public void error(Object information){
		System.err.println(information);
	}
}