package test.jax.client;

import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;

import test.jax.IWebInterface;

public class Client {
	
	public static void main(String[] args) throws MalformedURLException{
		URL url = new URL("http://localhost:8888/ws?wsdl");
		// go to URL, and fill the QName parameter: 
		// <definitions targetNamespace="http://jax.test/" name="WebImplementationService">
        QName qname = new QName("http://jax.test/","WebImplementationService");
        // create service 
        Service service = Service.create(url, qname);
        // get functionality of the web service
        IWebInterface webInterface = service.getPort(IWebInterface.class);
	 
        System.out.println(webInterface.getLength("mkyong"));

	}
}
