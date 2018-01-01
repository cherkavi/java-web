package com.vitalii.cherkashyn.collaboration.rest;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.Application;

import com.vitalii.cherkashyn.collaboration.rest.services.TestService;

public class RestApplication extends Application{
	private Set<Object> services=new HashSet<Object>();
	
	public RestApplication(){
		this.services.add(new TestService());
	}
	
	@Override
	public Set<Object> getSingletons() {
		return services;
	}
}
