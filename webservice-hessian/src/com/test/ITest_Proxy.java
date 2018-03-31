package com.test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import com.test.client.SimpleBean;

public class ITest_Proxy implements ITest{
	private DataSource dataSource;
	
	public ITest_Proxy(DataSource dataSource){
		this.dataSource=dataSource;
	}
	
	@Override
	public Date getDate() {
		return new Date();
	}

	
	private final static SimpleDateFormat sdf=new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
	@Override
	public String getDateString() {
		return sdf.format(this.getDate());
	}
	@Override
	public SimpleBean getBean() {
		return createBean(1, "temp");
	}

	private SimpleBean createBean(int id, String name){
		SimpleBean bean=new SimpleBean();
		bean.setId(id);
		bean.setName(name);
		bean.setDate(new Date());
		return bean;
	}

	@Override
	public List<SimpleBean> getListOfBeans() {
		List<SimpleBean> returnValue=new ArrayList<SimpleBean>();
		Connection connection=null;
		try{
			connection=this.dataSource.getConnection();
			ResultSet rs=connection.createStatement().executeQuery("select id, name from a_city");
			while(rs.next()){
				SimpleBean beanForAdd=createBean(rs.getInt(1),rs.getString(2));
				System.out.println("Bean:"+beanForAdd);
				returnValue.add(beanForAdd);
			}
		}catch(Exception ex){
			
		}finally{
			try{
				connection.close();
			}catch(Exception ex){};
		}
		return returnValue;
	}
	
	public SimpleBean[] getArrayOfBeans(){
		return getListOfBeans().toArray(new SimpleBean[]{});
	}

	@Override
	public SimpleBean getBeanById(int id) {
		return this.createBean(9, "test");
	}

	@Override
	public SimpleBean getBeanClone(SimpleBean bean) {
		return this.createBean(12, "clone");
	}
}
