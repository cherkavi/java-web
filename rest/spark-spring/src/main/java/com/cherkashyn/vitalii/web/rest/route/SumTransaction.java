package com.cherkashyn.vitalii.web.rest.route;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cherkashyn.vitalii.web.rest.model.Sum;
import com.cherkashyn.vitalii.web.rest.service.TransactionService;
import com.cherkashyn.vitalii.web.rest.transport.DomainSerializer;

import spark.Request;
import spark.Response;
import spark.Route;

@Component
public class SumTransaction implements Route{
	private final static Logger LOGGER=LoggerFactory.getLogger(SumTransaction.class);

	@Autowired
	private TransactionService service;

	@Override
	public Object handle(Request request, Response response) throws Exception {
		return DomainSerializer.render(new Sum(service.sumByParent(Long.parseLong(request.params(":id"))))) ;
	}

}
