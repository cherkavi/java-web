package com.test.spring;

import org.springframework.context.ApplicationContext;

public class ApplicationContextHolder {
	static ApplicationContext context=null;
	
	/**
	 * @return ������� ��������
	 */
	public static ApplicationContext getInstance(){
		return context;
	}
}
