package com.test.spring;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;


/**
 * 
 * this is spring beans, wich implemented interface for listen ApplicationContextAware
 * @author Администратор
 *
 */
public class ApplicationContextProvider implements ApplicationContextAware{

	@Override
	public void setApplicationContext(ApplicationContext currentContext) throws BeansException {
		ApplicationContextHolder.context=currentContext;
	}

}
