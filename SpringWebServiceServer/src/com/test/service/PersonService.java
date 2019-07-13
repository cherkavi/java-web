package com.test.service;

import javax.jws.WebService;

import com.test.common.Person;

@WebService
public interface PersonService {
	public String createPerson(String name);
	
	public Person getPerson(String name);
	
	public String test();
}
