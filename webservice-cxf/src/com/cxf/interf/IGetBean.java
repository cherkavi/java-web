package com.cxf.interf;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

import com.cxf.commons.TestBean;

@WebService
public interface IGetBean {
	
	@WebMethod
	public TestBean getBeanById(@WebParam(name="id")
								int id);
}
