package com.test;

import java.util.Date;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import org.apache.log4j.Logger;

import com.caucho.hessian.server.HessianServlet;
import com.test.client.SimpleBean;

/**
 * Servlet implementation class TestWS
 */
public class TestWS extends HessianServlet implements ITest{
	private static final long serialVersionUID = 1L;
	private final Proxy_TestWS proxy;
 	private Logger logger=Logger.getLogger(TestWS.class);
	
	public TestWS(){
		DataSource dataSource =null;
		try{
			Context initContext = new InitialContext();
			dataSource = (DataSource)initContext.lookup("java:/comp/env/jdbc/data_source");
		}catch(Exception ex){
			logger.error("DataSource Exception:",ex);
		}
		this.proxy=new Proxy_TestWS(dataSource);
	}
	
}
