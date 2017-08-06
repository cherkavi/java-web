package com.cherkashyn.vitalii.web.rest.route;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cherkashyn.vitalii.web.rest.Constants;
import com.cherkashyn.vitalii.web.rest.exception.SerializeException;
import com.cherkashyn.vitalii.web.rest.exception.ServiceException;
import com.cherkashyn.vitalii.web.rest.service.TransactionService;
import com.cherkashyn.vitalii.web.rest.transport.DomainSerializer;

import spark.Request;
import spark.Response;
import spark.Route;

@Component
public class GetTransaction implements Route, Constants{
	private final static Logger LOGGER=LoggerFactory.getLogger(GetTransaction.class);
	
	@Autowired
	private TransactionService service;
	
	@Override
	public Object handle(Request request, Response response) throws NumberFormatException, SerializeException, ServiceException{
		return DomainSerializer.render(service.get(Long.parseLong(request.params(":id"))));
	}

}
