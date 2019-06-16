package com.test.spring.beans;

import org.springframework.beans.factory.InitializingBean;

/**
 * данный bean содержит логику по контролю за инициализацией:
 * 	 интерфейс InitializingBean, который вызывается Spring ( раньше чем init-method )
 * 	 init-method - первоначальный метод инициализации 
 * @author Администратор
 */
public class Counter implements InitializingBean{
	private int counter;
	
	public Counter(){
		System.out.println("Create counter");
	}
	
	/** получить результат  */
	public int getResult(){
		System.out.println("Counter#getResult");
		return counter;
	}
	
	/** увеличить результат и вернуть его */
	public int increaseCounter(){
		System.out.println("Counter#increaseCounter");
		return ++counter;
	}


	@Override
	public void afterPropertiesSet() throws Exception {
		System.out.println("Call interface InitializingBean#afterPropertiesSet");
	}
	
	public void callInitMethod(){
		System.out.println("Call init-method ");
	}
}
