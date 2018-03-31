package com.test;

import java.util.Date;
import java.util.List;

import ua.com.nmtg.private_office.common_elements.annotation.DataBase;
import ua.com.nmtg.private_office.common_elements.annotation.DataBaseElement;

import com.test.client.SimpleBean;

@DataBase(
		listOfElements = { 
				@DataBaseElement(ddl = "? create procedure test(?,?,?)"),
				@DataBaseElement(ddl = "? create procedure test(?,?,?)"),
				@DataBaseElement(ddl = "? create procedure test(?,?,?)"),
				}
		)
public interface ITest {
	public String getDateString();
	public Date getDate();
	public SimpleBean getBean();
	public List<SimpleBean> getListOfBeans();
	public SimpleBean[] getArrayOfBeans();
	public SimpleBean getBeanById(int id);
	public SimpleBean getBeanClone(SimpleBean bean);
}
