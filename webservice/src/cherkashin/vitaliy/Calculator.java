package cherkashin.vitaliy;
import javax.jws.WebMethod;

import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.WebParam.Mode;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;
import javax.xml.ws.Endpoint;

import commons.UserObject;

//@WebService(name="Calculator", targetNamespace="http://cherkashin.vitaliy.com/", serviceName="Calculator")
@WebService
@SOAPBinding(style=Style.RPC) // style=Style.DOCUMENT 
public class Calculator {
	@WebMethod
	//@WebMethod(operationName="calc", action="url:calc")	
	//public @WebResult(partName="result") int addValue(@WebParam(partName="first_value",mode=Mode.IN) int a, @WebParam(partName="second_value",mode=Mode.INOUT)Integer b){
	public int addValue(int a, int b){
		b=b*10+a;
		return a+b;
	}
	
	@WebMethod
	public UserObject getUserObject(int intValue, String stringValue){
		UserObject returnValue=new UserObject(intValue, stringValue);
		return returnValue;
	}
	
	public static int get(int a, int b){
		b=b*10+a;
		return a+b;
	}
	
	public static void main(String[] args){
		//int a=10;
		//int b=20;
		//System.out.println("Get: "+get(a,b));
		//System.out.println("B: "+b);
		
		// публикация
		Endpoint.publish("http://192.168.15.119:8080/WS/calc",new Calculator());

		
		// генерация кода:
		// wsimport -keep http://192.168.15.119:8080/WS/calc?wsdl // генерировать только для внешнего адреса
		// на выходе - сгенерированные классы, которые позволяют создать клиента, - переписать сгенерированные классы на клиента
	}
}
