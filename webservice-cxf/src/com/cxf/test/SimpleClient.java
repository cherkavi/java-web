package com.cxf.test;

import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;

import com.cxf.commons.TestBean;
import com.cxf.interf.IGetBean;


public class SimpleClient {
	public static void main(String[] args){
		System.out.println("begin");
		JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();

    	// factory.getInInterceptors().add(new LoggingInInterceptor());
    	// factory.getOutInterceptors().add(new LoggingOutInterceptor());
		
    	factory.setServiceClass(IGetBean.class);
    	factory.setAddress("http://localhost:8080/WebServiceCXF/services/service_get_bean");
    	IGetBean client = (IGetBean) factory.create();

    	TestBean employee = client.getBeanById(10);
    	System.out.println("Server said: " + employee);
		
		System.out.println("-end-");
	}
}
