package com.test.spring.context;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class ContextAware implements ApplicationContextAware{

	@Override
	public void setApplicationContext(ApplicationContext context) throws BeansException {
		ApplicationContextHolder.instance=context;
	}

}
