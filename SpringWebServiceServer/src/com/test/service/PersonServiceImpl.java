package com.test.service;

import javax.jws.WebService;

import com.test.common.Person;
import com.test.spring.ApplicationContextHolder;

@WebService( endpointInterface="com.test.service.PersonService" )
public class PersonServiceImpl implements PersonService{

	@Override
	public String createPerson(String name) {
		System.out.println("Context:"+ApplicationContextHolder.getInstance());
		return "Hello,"+name+", WebService !";
	}

	@Override
	public Person getPerson(String name) {
		return new Person(name);
	}

	@Override
	public String test() {
		return "";
	}

	
}
