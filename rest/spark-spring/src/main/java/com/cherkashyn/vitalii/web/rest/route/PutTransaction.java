package com.cherkashyn.vitalii.web.rest.route;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cherkashyn.vitalii.web.rest.Constants;
import com.cherkashyn.vitalii.web.rest.model.Transaction;
import com.cherkashyn.vitalii.web.rest.service.TransactionService;
import com.cherkashyn.vitalii.web.rest.transport.DomainSerializer;

import spark.Request;
import spark.Response;
import spark.Route;

@Component
public class PutTransaction implements Route, Constants{
	private final static Logger LOGGER=LoggerFactory.getLogger(PutTransaction.class);

	@Autowired
	private TransactionService service;

	@Override
	public Object handle(Request request, Response response) throws Exception {
		Transaction value=DomainSerializer.unrender(request.body(), Transaction.class);
		value.setId(Long.parseLong(request.params(":id")));
		return DomainSerializer.render(service.save(value));
	}

}
