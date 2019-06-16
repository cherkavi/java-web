package com.test.spring.context;

import org.springframework.context.ApplicationContext;

/** Holder for ApplicationContext */
public class ApplicationContextHolder {
	/** instance  */
	static ApplicationContext instance;
	
	
	/** get current ApplicationContext  */
	public static ApplicationContext getInstance(){
		return instance;
	}
}
