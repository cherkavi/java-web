package com.test.spring;

import org.springframework.context.ApplicationContext;

public class ApplicationContextHolder {
	static ApplicationContext context=null;
	
	/**
	 * @return текущий контекст
	 */
	public static ApplicationContext getInstance(){
		return context;
	}
}
