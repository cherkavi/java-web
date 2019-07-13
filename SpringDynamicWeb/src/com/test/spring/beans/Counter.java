package com.test.spring.beans;

import org.springframework.beans.factory.InitializingBean;

/**
 * ������ bean �������� ������ �� �������� �� ��������������:
 * 	 ��������� InitializingBean, ������� ���������� Spring ( ������ ��� init-method )
 * 	 init-method - �������������� ����� ������������� 
 * @author �������������
 */
public class Counter implements InitializingBean{
	private int counter;
	
	public Counter(){
		System.out.println("Create counter");
	}
	
	/** �������� ���������  */
	public int getResult(){
		System.out.println("Counter#getResult");
		return counter;
	}
	
	/** ��������� ��������� � ������� ��� */
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
