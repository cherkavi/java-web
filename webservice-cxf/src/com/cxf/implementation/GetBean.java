package com.cxf.implementation;

import java.text.SimpleDateFormat;

import javax.jws.WebService;

import com.cxf.commons.TestBean;
import com.cxf.interf.IGetBean;

@WebService(endpointInterface="com.cxf.interf.IGetBean", serviceName="getBeanService")
public class GetBean implements IGetBean{
	private SimpleDateFormat sdf=new SimpleDateFormat("HH:mm:ss");

	@Override
	public TestBean getBeanById(int id) {
		return new TestBean(id, sdf.format(new java.util.Date()));
	}

}
