package com.test.client;

import java.net.MalformedURLException;

import com.caucho.hessian.client.HessianProxyFactory;
import com.test.ITest;

public class Client {
	public static void main(String[] args) throws MalformedURLException{
		String url = "http://localhost:8080/WebServiceHessian/TestWS";

		HessianProxyFactory factory = new HessianProxyFactory();
		ITest proxy = (ITest) factory.create(ITest.class, url);

		System.out.println("Date: " + proxy.getDate());		
		System.out.println("String: " + proxy.getDateString());
	}
}
