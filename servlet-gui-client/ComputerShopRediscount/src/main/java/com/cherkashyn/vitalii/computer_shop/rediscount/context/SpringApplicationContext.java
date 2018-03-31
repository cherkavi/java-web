package com.cherkashyn.vitalii.computer_shop.rediscount.context;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

public class SpringApplicationContext implements ApplicationContextAware {

	private static ApplicationContext appContext;

	// Private constructor prevents instantiation from other classes
    private SpringApplicationContext() {}
    
	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		appContext = applicationContext;
		
		loadJdbcClassName(applicationContext);
	}

	private void loadJdbcClassName(ApplicationContext applicationContext) {
		DriverManagerDataSource dataSource=applicationContext.getBean(DriverManagerDataSource.class);
		try {
			Class.forName(dataSource.getDriverClassName());
		} catch (ClassNotFoundException e) {
		}
	}

	public static Object getBean(String beanName) {
		return appContext.getBean(beanName);
	}

}