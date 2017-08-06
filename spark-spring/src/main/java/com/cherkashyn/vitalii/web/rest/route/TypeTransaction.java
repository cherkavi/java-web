package com.cherkashyn.vitalii.web.rest.route;

import java.util.Collection;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cherkashyn.vitalii.web.rest.model.Transaction;
import com.cherkashyn.vitalii.web.rest.service.TransactionService;
import com.cherkashyn.vitalii.web.rest.transport.DomainSerializer;

import spark.Request;
import spark.Response;
import spark.Route;

@Component
public class TypeTransaction implements Route{
	private final static Logger LOGGER=LoggerFactory.getLogger(TypeTransaction.class);

	@Autowired
	private TransactionService service;

	@Override
	public Object handle(Request request, Response response) throws Exception {
		Collection<Transaction> list=service.getByType(request.params(":type"));
		return DomainSerializer.render(list.stream().map(eachTransaction->eachTransaction.getId()).collect(Collectors.toList()));
	}

}
