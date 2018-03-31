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
public class ITest_WS extends HessianServlet implements ITest{
	private static final long serialVersionUID = 1L;
	private final ITest_Proxy proxy;
 	private Logger logger=Logger.getLogger(ITest_WS.class);
	
	public ITest_WS(){
		DataSource dataSource =null;
		try{
			Context initContext = new InitialContext();
			dataSource = (DataSource)initContext.lookup("java:/comp/env/jdbc/data_source");
		}catch(Exception ex){
			logger.error("DataSource Exception:",ex);
		}
		this.proxy=new ITest_Proxy(dataSource);
	}
	
	@Override
	public Date getDate() {
		return this.proxy.getDate();
	}
	
	@Override
	public String getDateString() {
		return this.proxy.getDateString();
	}
	@Override
	public SimpleBean getBean() {
		return this.proxy.getBean();
	}

	@Override
	public List<SimpleBean> getListOfBeans() {
		return this.proxy.getListOfBeans();
	}
	
	public SimpleBean[] getArrayOfBeans(){
		return this.proxy.getArrayOfBeans();
	}

	@Override
	public SimpleBean getBeanById(int id) {
		return this.proxy.getBeanById(id);
	}

	@Override
	public SimpleBean getBeanClone(SimpleBean bean) {
		return this.proxy.getBeanClone(bean);
	}
}
